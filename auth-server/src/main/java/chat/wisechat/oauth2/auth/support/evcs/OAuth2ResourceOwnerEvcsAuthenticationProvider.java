package chat.wisechat.oauth2.auth.support.evcs;

import chat.wisechat.oauth2.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/14  21:45
 */
public class OAuth2ResourceOwnerEvcsAuthenticationProvider
        extends OAuth2ResourceOwnerBaseAuthenticationProvider<OAuth2ResourceOwnerEvcsAuthenticationToken> {

    public OAuth2ResourceOwnerEvcsAuthenticationProvider(OAuth2AuthorizationService authorizationService,
                                                         OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                                         AuthenticationManager authenticationManager) {
        super(authorizationService, tokenGenerator, authenticationManager);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2ResourceOwnerEvcsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
