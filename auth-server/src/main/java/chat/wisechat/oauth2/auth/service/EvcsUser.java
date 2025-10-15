package chat.wisechat.oauth2.auth.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/15 20:19
 */
public class EvcsUser extends User implements OAuth2AuthenticatedPrincipal {
    public EvcsUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public List<Object> evcsOperators;

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
