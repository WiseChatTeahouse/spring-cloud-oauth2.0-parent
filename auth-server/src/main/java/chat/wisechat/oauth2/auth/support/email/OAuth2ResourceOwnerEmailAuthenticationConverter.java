package chat.wisechat.oauth2.auth.support.email;

import chat.wisechat.oauth2.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationConverter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/14  21:19
 */
@Slf4j
public class OAuth2ResourceOwnerEmailAuthenticationConverter extends OAuth2ResourceOwnerBaseAuthenticationConverter {

    private static final String EMAIL = "email";

    @Override
    public boolean support(String grantType) {
        log.debug("AuthenticationConverter = {} grantType = {}", EMAIL, grantType);
        return EMAIL.equals(grantType);
    }
}
