package chat.wisechat.oauth2.auth.support.email;

import chat.wisechat.oauth2.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Map;
import java.util.Set;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/14  21:19
 */
@Slf4j
public class OAuth2ResourceOwnerEmailAuthenticationConverter extends OAuth2ResourceOwnerBaseAuthenticationConverter<OAuth2ResourceOwnerEmailAuthenticationToken> {

    private static final String EMAIL = "email";

    @Override
    public boolean support(String grantType) {
        log.debug("AuthenticationConverter = {} grantType = {}", EMAIL, grantType);
        return EMAIL.equals(grantType);
    }

    @Override
    public OAuth2ResourceOwnerEmailAuthenticationToken buildAuthenticationToken(Authentication clientPrincipal, Set<String> requestedScopes, Map<String, Object> additionalParameters) {
        return new OAuth2ResourceOwnerEmailAuthenticationToken(new AuthorizationGrantType(EMAIL), clientPrincipal, requestedScopes, additionalParameters);
    }
}
