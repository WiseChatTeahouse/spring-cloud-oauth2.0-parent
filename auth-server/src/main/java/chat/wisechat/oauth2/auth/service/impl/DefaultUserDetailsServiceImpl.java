package chat.wisechat.oauth2.auth.service.impl;

import chat.wisechat.oauth2.auth.service.ChargeUserDetailsService;
import chat.wisechat.oauth2.auth.service.DefaultUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/15 19:55
 */
@Component
public class DefaultUserDetailsServiceImpl implements ChargeUserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new DefaultUser("", "", Collections.emptyList());
    }
}
