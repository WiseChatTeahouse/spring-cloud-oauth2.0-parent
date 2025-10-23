package chat.wisechat.oauth2.auth.support.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

/**
 * 一个用于认证 OAuth2AuthorizationGrantAuthenticationToken 的主处理器
 *
 * @Author Siberia.Hu
 * @Date 2025/10/13  22:45
 */
@Slf4j
public abstract class OAuth2ResourceOwnerBaseAuthenticationProvider<T extends OAuth2ResourceOwnerBaseAuthenticationToken> implements AuthenticationProvider {

    private final OAuth2AuthorizationService authorizationService;

    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    private final AuthenticationManager authenticationManager;

    protected OAuth2ResourceOwnerBaseAuthenticationProvider(OAuth2AuthorizationService authorizationService,
                                                            OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                                            AuthenticationManager authenticationManager) {
        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "tokenGenerator cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("Base主处理器");
        return null;
    }

    @Override
    public abstract boolean supports(Class<?> authentication);
}
