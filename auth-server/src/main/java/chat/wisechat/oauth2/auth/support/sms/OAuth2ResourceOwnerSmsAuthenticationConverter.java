package chat.wisechat.oauth2.auth.support.sms;

import chat.wisechat.oauth2.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/14  21:18
 */
@Slf4j
public class OAuth2ResourceOwnerSmsAuthenticationConverter extends OAuth2ResourceOwnerBaseAuthenticationConverter<OAuth2ResourceOwnerSmsAuthenticationToken> {
    private static final String SMS = "sms";

    @Override
    public boolean support(String grantType) {
        log.debug("AuthenticationConverter = {} grantType = {}", SMS, grantType);
        return SMS.equals(grantType);
    }

    @Override
    public OAuth2ResourceOwnerSmsAuthenticationToken buildAuthenticationToken(Authentication clientPrincipal, Set<String> requestedScopes, Map<String, Object> additionalParameters) {
        return new OAuth2ResourceOwnerSmsAuthenticationToken(new AuthorizationGrantType(SMS), clientPrincipal, requestedScopes, additionalParameters);
    }
}
