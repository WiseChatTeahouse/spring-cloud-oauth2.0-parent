package chat.wisechat.oauth2.auth.config;

import chat.wisechat.oauth2.auth.handler.ProjectAuthenticationFailureHandler;
import chat.wisechat.oauth2.auth.handler.ProjectAuthenticationSuccessHandler;
import chat.wisechat.oauth2.auth.support.ProjectUser;
import chat.wisechat.oauth2.auth.support.core.UserDetailsAuthenticationProvider;
import chat.wisechat.oauth2.auth.support.password.PasswordAuthenticationConverter;
import chat.wisechat.oauth2.auth.support.password.PasswordAuthenticationProvider;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.JwtGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/18  20:45
 */
@Configuration
public class AuthorizationServerConfiguration {

    @Resource
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) ->
                        authorizationServer
                                .tokenEndpoint(tokenEndpoint ->
                                        tokenEndpoint
                                                .accessTokenRequestConverters(authenticationConverters ->
                                                        authenticationConverters.addAll(List.of(
                                                                new PasswordAuthenticationConverter())))
                                                .accessTokenResponseHandler(new ProjectAuthenticationSuccessHandler())
                                                .errorResponseHandler(new ProjectAuthenticationFailureHandler()))
                                .clientAuthentication(clientAuthentication -> {
                                })
                                .authorizationEndpoint(authorizationEndpoint -> {
                                })
                ).authorizeHttpRequests(authorizeRequest -> authorizeRequest.anyRequest().permitAll());

        DefaultSecurityFilterChain build = http.build();

        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
        OAuth2AuthorizationService authorizationService = http.getSharedObject(OAuth2AuthorizationService.class);
        http.authenticationProvider(new UserDetailsAuthenticationProvider(userDetailsService));
        http.authenticationProvider(new PasswordAuthenticationProvider(authorizationService, authenticationManager,
                new DelegatingOAuth2TokenGenerator(tokenGenerator(), new OAuth2RefreshTokenGenerator())));
        return build;
    }

    public OAuth2TokenGenerator<OAuth2Token> tokenGenerator() {
        JwtGenerator jwtGenerator = new JwtGenerator(parameters -> {
            JwtClaimsSet claims = parameters.getClaims();
            JwsHeader jwsHeader = parameters.getJwsHeader();
            Instant issuedAt = Instant.now();
            Instant expiresAt = issuedAt.plusMillis(60L);
            String token = UUID.randomUUID().toString().replace("-", "");
            return new Jwt(token, issuedAt, expiresAt, jwsHeader.getHeaders(), claims.getClaims());
        });
        jwtGenerator.setJwtCustomizer(context -> {
            JwtClaimsSet.Builder claims = context.getClaims();
            claims.claim("license", "https://wisechat.chat");
            String clientId = context.getAuthorizationGrant().getName();
            claims.claim("clientId", clientId);
            // 写入用户信息
            ProjectUser pigUser = (ProjectUser) context.getPrincipal().getPrincipal();
            claims.claim("user_info", pigUser);
        });
        return new DelegatingOAuth2TokenGenerator(jwtGenerator, new OAuth2RefreshTokenGenerator());
    }

}
