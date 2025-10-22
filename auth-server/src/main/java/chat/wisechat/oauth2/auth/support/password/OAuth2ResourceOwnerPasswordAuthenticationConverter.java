package chat.wisechat.oauth2.auth.support.password;

import chat.wisechat.oauth2.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationConverter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/14  21:18
 */
@Slf4j
public class OAuth2ResourceOwnerPasswordAuthenticationConverter extends OAuth2ResourceOwnerBaseAuthenticationConverter {

    private static final String PASSWORD = "password";

    @Override
    public boolean support(String grantType) {
        log.debug("AuthenticationConverter = {} grantType = {}", PASSWORD, grantType);
        return PASSWORD.equals(grantType);
    }
}
