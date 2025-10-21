package chat.wisechat.oauth2.auth.support.base;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.util.Assert;

import java.io.Serial;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/13  23:00
 */
@Getter
public abstract class OAuth2ResourceOwnerBaseAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 1L;

    private final AuthorizationGrantType authorizationGrantType;

    private final Authentication principal;

    private final Set<String> scopes;

    private final Map<String, Object> additionalParameters;

    public OAuth2ResourceOwnerBaseAuthenticationToken(AuthorizationGrantType authorizationGrantType, Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(Collections.emptyList());
        Assert.notNull(authorizationGrantType, "authorizationGrantType cannot be null");
        Assert.notNull(clientPrincipal, "clientPrincipal cannot be null");
        this.authorizationGrantType = authorizationGrantType;
        this.principal = clientPrincipal;
        this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
        this.additionalParameters = Collections.unmodifiableMap(
                additionalParameters != null ? new HashMap<>(additionalParameters) : Collections.emptyMap());
    }

    /**
     * Always returns an empty String
     *
     * @return an empty String
     */
    @Override
    public Object getCredentials() {
        return "";
    }

    /**
     * Returns the additional parameters.
     *
     * @return the additional parameters, or an empty Map if not available
     */
    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
