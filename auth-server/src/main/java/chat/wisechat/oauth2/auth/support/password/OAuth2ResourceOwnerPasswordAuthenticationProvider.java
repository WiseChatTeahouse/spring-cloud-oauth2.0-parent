package chat.wisechat.oauth2.auth.support.password;

import chat.wisechat.oauth2.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/14  21:45
 */
public class OAuth2ResourceOwnerPasswordAuthenticationProvider
        extends OAuth2ResourceOwnerBaseAuthenticationProvider<OAuth2ResourceOwnerPasswordAuthenticationToken> {
    public OAuth2ResourceOwnerPasswordAuthenticationProvider(OAuth2AuthorizationService authorizationService,
                                                                OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                                                AuthenticationManager authenticationManager) {
        super(authorizationService, tokenGenerator, authenticationManager);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2ResourceOwnerPasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
