package chat.wisechat.oauth2.auth.service;

import chat.wisechat.oauth2.system.feign.RemoteOauthClientInfoFeign;
import chat.wisechat.oauth2.system.vo.OauthClientInfoVo;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeRequestAuthenticationException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/18  21:23
 */
@Component
public class RemoteRegisteredClientRepository implements RegisteredClientRepository {

    @Resource
    private RemoteOauthClientInfoFeign remoteOauthClientInfoFeign;

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {

        // 查询相应的客户端信息
        OauthClientInfoVo oauthClientInfo = Optional.of(remoteOauthClientInfoFeign.findClientInfoByClientId(clientId))
                .orElseThrow(() -> new OAuth2AuthorizationCodeRequestAuthenticationException(
                        new OAuth2Error("客户端信息查询失败，请检查配置"), null));
        // TODO: oauthClientInfo为null时需要怎么处理
        // 构建客户端对象
        RegisteredClient.Builder builder = RegisteredClient.withId(oauthClientInfo.getClientId())
                .clientId(oauthClientInfo.getClientId())
                .clientSecret("{noop}" + oauthClientInfo.getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);

        // 验证方式
        String authorizedGrantTypeStr = oauthClientInfo.getAuthorizedGrantTypes();
        if (StringUtils.isBlank(authorizedGrantTypeStr)) {
            return null;
        }
        String[] authorizedGrantTypes = authorizedGrantTypeStr.split(",");
        Set<AuthorizationGrantType> grantTypeSets = Arrays.stream(authorizedGrantTypes)
                .map(AuthorizationGrantType::new).collect(Collectors.toSet());
        builder.authorizationGrantTypes(authorizedGrantType -> authorizedGrantType.addAll(grantTypeSets));

        // 回调地址
        Optional.ofNullable(oauthClientInfo.getRedirectUris())
                .ifPresent(redirectUri -> Arrays.stream(redirectUri.split(","))
                        .filter(StringUtils::isNotBlank)
                        .forEach(builder::redirectUri));

        // scope
        Optional.ofNullable(oauthClientInfo.getScope())
                .ifPresent(scope -> Arrays.stream(scope.split(","))
                        .filter(StringUtils::isNotBlank)
                        .forEach(builder::scope));

        return builder
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)// 只是引用 非实际令牌存储
                        .accessTokenTimeToLive(Duration.ofSeconds(oauthClientInfo.getAccessTokenValidity()))
                        .refreshTokenTimeToLive(Duration.ofSeconds(oauthClientInfo.getRefreshTokenValidity())).build())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
    }
}
