package chat.wisechat.oauth2.auth.support.password;

import chat.wisechat.oauth2.auth.support.base.BaseAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/21 10:10
 */
public class PasswordAuthenticationProvider extends BaseAuthenticationProvider {

    public PasswordAuthenticationProvider(OAuth2AuthorizationService authorizationService, AuthenticationManager authenticationManager, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        super(authorizationService, authenticationManager, tokenGenerator);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
