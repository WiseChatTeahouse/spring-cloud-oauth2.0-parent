package chat.wisechat.oauth2.auth.support.base;


import chat.wisechat.oauth2.auth.support.utils.OAuth2EndpointUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 试图从 HttpServletRequest 中提取 OAuth2 access token 请求 到 OAuth2AuthorizationGrantAuthenticationToken 的实例时使用。
 *
 * @Author Siberia.Hu
 * @Date 2025/10/13  23:03
 */
@Slf4j
public abstract class OAuth2ResourceOwnerBaseAuthenticationConverter implements AuthenticationConverter {

    /**
     * 用于判断当前登录方式是否支持 这个授权类型
     *
     * @param grantType 授权类型
     * @return true:支持 false:不支持
     */
    public abstract boolean support(String grantType);

    @Override
    public Authentication convert(HttpServletRequest request) {
        log.info("Base预处理器");

        MultiValueMap<String, String> parameters = OAuth2EndpointUtils.getFormParameters(request);

        // grant_type (REQUIRED)
        String grantType = parameters.getFirst(OAuth2ParameterNames.GRANT_TYPE);
        if (!support(grantType)) {
            return null;
        }

        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();

        // code (REQUIRED)
        String code = parameters.getFirst(OAuth2ParameterNames.CODE);
        if (!StringUtils.hasText(code) || parameters.get(OAuth2ParameterNames.CODE).size() != 1) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.CODE,
                    OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        // redirect_uri (REQUIRED)
        // Required only if the "redirect_uri" parameter was included in the authorization
        // request
        String redirectUri = parameters.getFirst(OAuth2ParameterNames.REDIRECT_URI);
        if (StringUtils.hasText(redirectUri) && parameters.get(OAuth2ParameterNames.REDIRECT_URI).size() != 1) {
            OAuth2EndpointUtils.throwError(OAuth2ErrorCodes.INVALID_REQUEST, OAuth2ParameterNames.REDIRECT_URI,
                    OAuth2EndpointUtils.ACCESS_TOKEN_REQUEST_ERROR_URI);
        }

        Map<String, Object> additionalParameters = new HashMap<>();
        parameters.forEach((key, value) -> {
            if (!key.equals(OAuth2ParameterNames.GRANT_TYPE) && !key.equals(OAuth2ParameterNames.CLIENT_ID)
                    && !key.equals(OAuth2ParameterNames.CODE) && !key.equals(OAuth2ParameterNames.REDIRECT_URI)) {
                additionalParameters.put(key, (value.size() == 1) ? value.get(0) : value.toArray(new String[0]));
            }
        });

        return new OAuth2AuthorizationCodeAuthenticationToken(code, clientPrincipal, redirectUri, additionalParameters);
    }


}
