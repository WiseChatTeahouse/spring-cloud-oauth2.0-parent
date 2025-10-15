package chat.wisechat.oauth2.auth.config;

import chat.wisechat.oauth2.auth.support.core.DatabaseUserDetailsAuthenticationProvider;
import chat.wisechat.oauth2.auth.support.email.OAuth2ResourceOwnerEmailAuthenticationConverter;
import chat.wisechat.oauth2.auth.support.email.OAuth2ResourceOwnerEmailAuthenticationProvider;
import chat.wisechat.oauth2.auth.support.evcs.OAuth2ResourceOwnerEvcsAuthenticationConverter;
import chat.wisechat.oauth2.auth.support.evcs.OAuth2ResourceOwnerEvcsAuthenticationProvider;
import chat.wisechat.oauth2.auth.support.filter.ValidateCodeFilter;
import chat.wisechat.oauth2.auth.support.password.OAuth2ResourceOwnerPasswordAuthenticationConverter;
import chat.wisechat.oauth2.auth.support.password.OAuth2ResourceOwnerPasswordAuthenticationProvider;
import chat.wisechat.oauth2.auth.support.sms.OAuth2ResourceOwnerSmsAuthenticationConverter;
import chat.wisechat.oauth2.auth.support.sms.OAuth2ResourceOwnerSmsAuthenticationProvider;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2AuthorizationCodeRequestAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2ClientCredentialsAuthenticationConverter;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2RefreshTokenAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.DelegatingAuthenticationConverter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

/**
 * @author Siberia.Hu
 * @date 2025/10/12  22:48
 */
@Configuration
public class AuthorizationServerConfiguration {

    @Resource
    private OAuth2AuthorizationService authorizationService;
    @Resource
    private ValidateCodeFilter validateCodeFilter;

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // OAuth2 认证相关配置
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();
        // 处理OAuth2的相关端点 只有/oauth2/** 的几个端点会走这里
        http.securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                // 添加前置过滤器 校验验证码信息 在校验 用户名和密码之前校验
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 个性化OAuth2 令牌端点
                .with(authorizationServerConfigurer, (authorizationServer) ->
                        authorizationServer
                                .tokenEndpoint(tokenEndpoint -> tokenEndpoint
                                        // 自定义请求转换器
                                        .accessTokenRequestConverter(accessTokenRequestConverter())
                                        // 用户名密码处理
                                        .authenticationProvider(new DatabaseUserDetailsAuthenticationProvider())
                                        // 用户名密码处理
                                        .authenticationProvider(new OAuth2ResourceOwnerPasswordAuthenticationProvider())
                                        // 短信处理
                                        .authenticationProvider(new OAuth2ResourceOwnerSmsAuthenticationProvider())
                                        // 邮箱处理
                                        .authenticationProvider(new OAuth2ResourceOwnerEmailAuthenticationProvider())
                                        // 互联互通处理
                                        .authenticationProvider(new OAuth2ResourceOwnerEvcsAuthenticationProvider())))
                .with(authorizationServerConfigurer.authorizationService(authorizationService)
                                //设置iss 的签发信息
                                .authorizationServerSettings(AuthorizationServerSettings.builder().issuer("https://wisechat.chat").build())
                        , Customizer.withDefaults())
                //所有请求
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated());
        return http.build();
    }


    /**
     * 请求转换器  注入到OAuth2TokenEndpointFilter中
     *
     * @return DelegatingAuthenticationConverter
     */
    @Bean
    public AuthenticationConverter accessTokenRequestConverter() {
        return new DelegatingAuthenticationConverter(Arrays.asList(
                // 自定义请求转换器
                new OAuth2ResourceOwnerPasswordAuthenticationConverter(),
                new OAuth2ResourceOwnerSmsAuthenticationConverter(),
                new OAuth2ResourceOwnerEvcsAuthenticationConverter(),
                new OAuth2ResourceOwnerEmailAuthenticationConverter(),

                new OAuth2RefreshTokenAuthenticationConverter(),
                new OAuth2ClientCredentialsAuthenticationConverter(),
                new OAuth2AuthorizationCodeAuthenticationConverter(),
                new OAuth2AuthorizationCodeRequestAuthenticationConverter()));
    }
}
