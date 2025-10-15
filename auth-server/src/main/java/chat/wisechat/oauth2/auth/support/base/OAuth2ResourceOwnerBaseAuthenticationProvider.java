package chat.wisechat.oauth2.auth.support.base;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/13  22:45
 */
public abstract class OAuth2ResourceOwnerBaseAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public abstract boolean supports(Class<?> authentication);
}
