package chat.wisechat.oauth2.auth.support.evcs;

import chat.wisechat.oauth2.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationProvider;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/14  21:45
 */
public class OAuth2ResourceOwnerEvcsAuthenticationProvider extends OAuth2ResourceOwnerBaseAuthenticationProvider {
    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
