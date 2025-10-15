package chat.wisechat.oauth2.auth.service;

import org.springframework.core.Ordered;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/15 19:50
 */
public interface ChargeUserDetailsService extends UserDetailsService, Ordered {

    default boolean support(String clientId, String grantType) {
        return false;
    }

    default boolean evcsSupport(String type, String selfOperator) {
        return false;
    }

    @Override
    default int getOrder() {
        return 0;
    }

    @Override
    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
