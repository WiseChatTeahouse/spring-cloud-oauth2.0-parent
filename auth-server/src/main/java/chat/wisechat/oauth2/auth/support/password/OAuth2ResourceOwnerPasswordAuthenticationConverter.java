package chat.wisechat.oauth2.auth.support.password;

import chat.wisechat.oauth2.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationConverter;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/14  21:18
 */
public class OAuth2ResourceOwnerPasswordAuthenticationConverter extends OAuth2ResourceOwnerBaseAuthenticationConverter {

    private static final String PASSWORD = "password";

    @Override
    public boolean support(String grantType) {
        return PASSWORD.equals(grantType);
    }
}
