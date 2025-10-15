package chat.wisechat.oauth2.auth.support.base;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/13  23:00
 */
public abstract class OAuth2ResourceOwnerBaseAuthenticationToken extends AbstractAuthenticationToken {
    public OAuth2ResourceOwnerBaseAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }
}
