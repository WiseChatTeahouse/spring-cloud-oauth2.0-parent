package chat.wisechat.oauth2.auth.config;

import chat.wisechat.oauth2.auth.handler.ProjectAuthenticationFailureHandler;
import chat.wisechat.oauth2.auth.handler.ProjectAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/18  20:45
 */
@Configuration
public class AuthorizationServerConfiguration {

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
                                                .accessTokenRequestConverters(authenticationConverters -> authenticationConverters.addAll(List.of()))
                                                .authenticationProviders(authenticationProviders -> authenticationProviders.addAll(List.of()))
                                                .accessTokenResponseHandler(new ProjectAuthenticationSuccessHandler())
                                                .errorResponseHandler(new ProjectAuthenticationFailureHandler()))
                                .clientAuthentication(clientAuthentication -> {
                                })
                                .authorizationEndpoint(authorizationEndpoint -> {
                                })
                );

        return http.build();
    }

}
