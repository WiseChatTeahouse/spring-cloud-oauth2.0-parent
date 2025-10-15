package chat.wisechat.oauth2.auth.service.impl;

import chat.wisechat.oauth2.auth.service.ChargeUserDetailsService;
import chat.wisechat.oauth2.auth.service.EvcsUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/15 19:59
 */
@Component
public class EvcsUserDetailsServiceImpl implements ChargeUserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO:需要自行构建
        return new EvcsUser("", "", Collections.emptyList());
    }
}
