package chat.wisechat.oauth2.auth.support.evcs;

import chat.wisechat.oauth2.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationConverter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/14  21:18
 */
@Slf4j
public class OAuth2ResourceOwnerEvcsAuthenticationConverter extends OAuth2ResourceOwnerBaseAuthenticationConverter {

    private static final String EVCS = "evcs";

    @Override
    public boolean support(String grantType) {
        log.debug("AuthenticationConverter = {} grantType = {}", EVCS, grantType);
        return EVCS.equals(grantType);
    }
}
