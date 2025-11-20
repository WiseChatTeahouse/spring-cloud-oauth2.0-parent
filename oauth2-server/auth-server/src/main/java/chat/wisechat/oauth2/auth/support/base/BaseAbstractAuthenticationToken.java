package chat.wisechat.oauth2.auth.support.base;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.Collections;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/20  22:20
 */
public abstract class BaseAbstractAuthenticationToken extends AbstractAuthenticationToken {

    private final AuthorizationGrantType authorizationGrantType;

    private final Authentication clientPrincipal;

    public BaseAbstractAuthenticationToken(AuthorizationGrantType authorizationGrantType,
                                           Authentication clientPrincipal) {
        super(Collections.emptyList());
        this.authorizationGrantType = authorizationGrantType;
        this.clientPrincipal = clientPrincipal;
    }

    public AuthorizationGrantType getGrantType() {
        return this.authorizationGrantType;
    }

    @Override
    public Object getPrincipal() {
        return this.clientPrincipal;
    }

    @Override
    public Object getCredentials() {
        return "";
    }
}
