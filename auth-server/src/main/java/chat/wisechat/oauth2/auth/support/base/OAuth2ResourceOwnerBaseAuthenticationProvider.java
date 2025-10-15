package chat.wisechat.oauth2.auth.support.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * 一个用于认证 OAuth2AuthorizationGrantAuthenticationToken 的主处理器
 *
 * @Author Siberia.Hu
 * @Date 2025/10/13  22:45
 */
@Slf4j
public abstract class OAuth2ResourceOwnerBaseAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Base主处理器");
        return null;
    }

    @Override
    public abstract boolean supports(Class<?> authentication);
}
