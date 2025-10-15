package chat.wisechat.oauth2.auth.support.sms;

import chat.wisechat.oauth2.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationConverter;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/14  21:18
 */
public class OAuth2ResourceOwnerSmsAuthenticationConverter extends OAuth2ResourceOwnerBaseAuthenticationConverter {
    private static final String SMS = "sms";

    @Override
    public boolean support(String grantType) {
        return SMS.equals(grantType);
    }
}
