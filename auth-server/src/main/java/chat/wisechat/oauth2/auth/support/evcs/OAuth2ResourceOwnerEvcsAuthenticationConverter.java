package chat.wisechat.oauth2.auth.support.evcs;

import chat.wisechat.oauth2.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationConverter;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/14  21:18
 */
public class OAuth2ResourceOwnerEvcsAuthenticationConverter extends OAuth2ResourceOwnerBaseAuthenticationConverter {

    private static final String EVCS = "evcs";

    @Override
    public boolean support(String grantType) {
        return EVCS.equals(grantType);
    }
}
