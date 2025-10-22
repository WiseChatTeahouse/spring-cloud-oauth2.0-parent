package chat.wisechat.oauth2.auth.support.sms;

import chat.wisechat.oauth2.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationConverter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/14  21:18
 */
@Slf4j
public class OAuth2ResourceOwnerSmsAuthenticationConverter extends OAuth2ResourceOwnerBaseAuthenticationConverter {
    private static final String SMS = "sms";

    @Override
    public boolean support(String grantType) {
        log.debug("AuthenticationConverter = {} grantType = {}", SMS, grantType);
        return SMS.equals(grantType);
    }
}
