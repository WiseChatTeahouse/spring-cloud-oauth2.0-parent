package chat.wisechat.oauth2.auth.config;

import chat.wisechat.oauth2.auth.handler.ProjectAuthenticationFailureHandler;
import chat.wisechat.oauth2.auth.handler.ProjectAuthenticationSuccessHandler;
import chat.wisechat.oauth2.auth.support.JwtOAuth2AccessTokenGenerator;
import chat.wisechat.oauth2.auth.support.core.UserDetailsAuthenticationProvider;
import chat.wisechat.oauth2.auth.support.password.PasswordAuthenticationConverter;
import chat.wisechat.oauth2.auth.support.password.PasswordAuthenticationProvider;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

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
                new DelegatingOAuth2TokenGenerator(new JwtOAuth2AccessTokenGenerator(http.getSharedObject(JwtEncoder.class)), new OAuth2RefreshTokenGenerator())));
        return build;
    }

}
