package chat.wisechat.oauth2.auth.support.sms;

import chat.wisechat.oauth2.auth.support.base.OAuth2ResourceOwnerBaseAuthenticationProvider;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/14  21:45
 */
public class OAuth2ResourceOwnerSmsAuthenticationProvider extends OAuth2ResourceOwnerBaseAuthenticationProvider {
    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2ResourceOwnerSmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
