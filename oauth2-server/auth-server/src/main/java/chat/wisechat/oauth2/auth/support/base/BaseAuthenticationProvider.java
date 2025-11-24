package chat.wisechat.oauth2.auth.support.base;

import chat.wisechat.oauth2.auth.support.password.PasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClaimAccessor;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/21 10:09
 */
public abstract class BaseAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationManager authenticationManager;
    private final OAuth2AuthorizationService authorizationService;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;
    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";

    public BaseAuthenticationProvider(OAuth2AuthorizationService authorizationService, AuthenticationManager authenticationManager, OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator) {
        this.authenticationManager = authenticationManager;
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PasswordAuthenticationToken clientCredentialsAuthentication = (PasswordAuthenticationToken) authentication;

        OAuth2ClientAuthenticationToken clientPrincipal = getAuthenticatedClientElseThrowInvalidClient(clientCredentialsAuthentication);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        // 看是否走错了
        if (!registeredClient.getAuthorizationGrantTypes().contains(new AuthorizationGrantType("password"))) {
            throw new OAuth2AuthenticationException(OAuth2ErrorCodes.UNAUTHORIZED_CLIENT);
        }

        Set<String> authorizedScopes = new LinkedHashSet<>(clientCredentialsAuthentication.getScopes());

        try {
            Map<String, Object> additionalParameters = clientCredentialsAuthentication.getAdditionalParameters();

            String username = (String) additionalParameters.get(OAuth2ParameterNames.USERNAME);
            String password = (String) additionalParameters.get(OAuth2ParameterNames.PASSWORD);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            // @formatter:off
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(authenticate)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(authorizedScopes)
                .tokenType(OAuth2TokenType.ACCESS_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizationGrant(clientCredentialsAuthentication);
        // @formatter:on

            OAuth2TokenContext tokenContext = tokenContextBuilder.build();

            OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);


            // @formatter:off
        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .principalName(clientPrincipal.getName())
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .authorizedScopes(authorizedScopes);
        // @formatter:on

            OAuth2AccessToken accessToken = accessToken(authorizationBuilder, generatedAccessToken, tokenContext);

            // ----- Refresh token -----
            OAuth2RefreshToken refreshToken = null;
            // Do not issue refresh token to public client
            if (registeredClient.getAuthorizationGrantTypes().contains(new AuthorizationGrantType("password"))) {
                tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
                OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
                if (generatedRefreshToken != null) {
                    if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                        OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                                "The token generator failed to generate a valid refresh token.", ERROR_URI);
                        throw new OAuth2AuthenticationException(error);
                    }
                    refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
                    authorizationBuilder.refreshToken(refreshToken);
                }
            }

            OAuth2Authorization authorization = authorizationBuilder.build();

            this.authorizationService.save(authorization);


            return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken);
        } catch (AuthenticationException e) {
            if (e instanceof BadCredentialsException) {
                throw new OAuth2AuthenticationException(
                        new OAuth2Error("bad_credentials",
                                "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
            throw new OAuth2AuthenticationException("bad_credentials");
        }
    }

    private static OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) {
        OAuth2ClientAuthenticationToken clientPrincipal = null;
        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
            clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
        }
        if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
            return clientPrincipal;
        }
        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }

    private <T extends OAuth2Token> OAuth2AccessToken accessToken(OAuth2Authorization.Builder builder, T token, OAuth2TokenContext accessTokenContext) {

        OAuth2AccessToken.TokenType tokenType = OAuth2AccessToken.TokenType.BEARER;
        if (token instanceof ClaimAccessor claimAccessor) {
            Map<String, Object> cnfClaims = claimAccessor.getClaimAsMap("cnf");
            if (!CollectionUtils.isEmpty(cnfClaims) && cnfClaims.containsKey("jkt")) {
                tokenType = OAuth2AccessToken.TokenType.DPOP;
            }
        }
        OAuth2AccessToken accessToken = new OAuth2AccessToken(tokenType, token.getTokenValue(), token.getIssuedAt(),
                token.getExpiresAt(), accessTokenContext.getAuthorizedScopes());
        OAuth2TokenFormat accessTokenFormat = accessTokenContext.getRegisteredClient()
                .getTokenSettings()
                .getAccessTokenFormat();
        builder.token(accessToken, (metadata) -> {
            if (token instanceof ClaimAccessor claimAccessor) {
                metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, claimAccessor.getClaims());
            }
            metadata.put(OAuth2Authorization.Token.INVALIDATED_METADATA_NAME, false);
            metadata.put(OAuth2TokenFormat.class.getName(), accessTokenFormat.getValue());
        });

        return accessToken;
    }

}
