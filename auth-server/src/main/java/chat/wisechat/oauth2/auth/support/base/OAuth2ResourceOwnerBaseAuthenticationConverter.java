package chat.wisechat.oauth2.auth.support.base;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/13  23:03
 */
public abstract class OAuth2ResourceOwnerBaseAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        return null;
    }
}
