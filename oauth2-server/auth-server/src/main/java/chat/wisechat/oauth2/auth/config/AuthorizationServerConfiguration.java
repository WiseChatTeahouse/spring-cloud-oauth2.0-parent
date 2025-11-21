package chat.wisechat.oauth2.auth.config;

import chat.wisechat.oauth2.auth.handler.ProjectAuthenticationFailureHandler;
import chat.wisechat.oauth2.auth.support.password.PasswordAuthenticationConverter;
import chat.wisechat.oauth2.auth.support.password.PasswordAuthenticationProvider;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.token.DelegatingOAuth2TokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2AccessTokenGenerator;
import org.springframework.security.oauth2.server.authorization.token.OAuth2RefreshTokenGenerator;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AccessTokenResponseAuthenticationSuccessHandler;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/18  20:45
 */
@Configuration
public class AuthorizationServerConfiguration {

    @Resource
    private OAuth2AuthorizationService authorizationService;

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();

        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .with(authorizationServerConfigurer, (authorizationServer) ->
                        authorizationServer
                                .tokenEndpoint(tokenEndpoint ->
                                        tokenEndpoint
                                                .accessTokenRequestConverters(authenticationConverters ->
                                                        authenticationConverters.addAll(List.of(
                                                                new PasswordAuthenticationConverter())))
                                                .authenticationProviders(authenticationProviders ->
                                                        authenticationProviders.addAll(List.of(
                                                                new PasswordAuthenticationProvider(authorizationService, new DelegatingOAuth2TokenGenerator(new OAuth2AccessTokenGenerator(), new OAuth2RefreshTokenGenerator())))))
                                                .accessTokenResponseHandler(new OAuth2AccessTokenResponseAuthenticationSuccessHandler())
                                                .errorResponseHandler(new ProjectAuthenticationFailureHandler()))
                                .clientAuthentication(clientAuthentication -> {
                                })
                                .authorizationEndpoint(authorizationEndpoint -> {
                                })
                );

        return http.build();
    }

}
