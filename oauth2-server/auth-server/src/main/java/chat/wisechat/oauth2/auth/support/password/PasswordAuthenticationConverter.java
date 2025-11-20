package chat.wisechat.oauth2.auth.support.password;

import chat.wisechat.oauth2.auth.support.base.BaseAuthenticationConverter;
import org.springframework.security.core.Authentication;

import java.util.Map;
import java.util.Set;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/20  22:02
 */
public class PasswordAuthenticationConverter extends BaseAuthenticationConverter {

    @Override
    public boolean support(String grantType) {
        return "password".equals(grantType);
    }

    @Override
    public Authentication buildAuthentication(Authentication clientPrincipal, Set<String> requestedScopes, Map<String, Object> additionalParameters) {
        return new PasswordAuthenticationToken(clientPrincipal, requestedScopes, additionalParameters);
    }
}
