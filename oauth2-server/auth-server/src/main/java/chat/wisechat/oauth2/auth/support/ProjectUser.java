package chat.wisechat.oauth2.auth.support;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/21 16:14
 */
public class ProjectUser extends User {

    private final Map<String, Object> attributes = new HashMap<>();
    public ProjectUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }


}
