package chat.wisechat.oauth2.auth.support.password;

import chat.wisechat.oauth2.auth.support.base.BaseAbstractAuthenticationToken;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.*;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/20  22:29
 */
@Getter
public class PasswordAuthenticationToken extends BaseAbstractAuthenticationToken {

    private final Set<String> scopes;

    private final Map<String, Object> additionalParameters;

    public PasswordAuthenticationToken(Authentication clientPrincipal, Set<String> scopes,
                                       Map<String, Object> additionalParameters) {
        super(new AuthorizationGrantType("password"), clientPrincipal);
        this.scopes = Collections.unmodifiableSet((scopes != null) ? new HashSet<>(scopes) : Collections.emptySet());
        this.additionalParameters = Collections.unmodifiableMap(
                (additionalParameters != null) ? new HashMap<>(additionalParameters) : Collections.emptyMap());
    }

}
