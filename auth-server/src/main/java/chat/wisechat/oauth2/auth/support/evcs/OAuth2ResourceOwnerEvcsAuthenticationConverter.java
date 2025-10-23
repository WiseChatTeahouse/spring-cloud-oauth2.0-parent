package chat.wisechat.oauth2.auth.support.evcs;

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
public class OAuth2ResourceOwnerEvcsAuthenticationConverter extends OAuth2ResourceOwnerBaseAuthenticationConverter<OAuth2ResourceOwnerEvcsAuthenticationToken> {

    private static final String EVCS = "evcs";

    @Override
    public boolean support(String grantType) {
        log.debug("AuthenticationConverter = {} grantType = {}", EVCS, grantType);
        return EVCS.equals(grantType);
    }

    @Override
    public OAuth2ResourceOwnerEvcsAuthenticationToken buildAuthenticationToken(Authentication clientPrincipal, Set<String> requestedScopes, Map<String, Object> additionalParameters) {
        return new OAuth2ResourceOwnerEvcsAuthenticationToken(new AuthorizationGrantType(EVCS), clientPrincipal, requestedScopes, additionalParameters);
    }
}
