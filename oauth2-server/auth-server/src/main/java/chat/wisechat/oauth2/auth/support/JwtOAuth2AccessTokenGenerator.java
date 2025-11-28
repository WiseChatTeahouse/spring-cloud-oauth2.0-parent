package chat.wisechat.oauth2.auth.support;

import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/28 10:31
 */
public class JwtOAuth2AccessTokenGenerator implements OAuth2TokenGenerator<Jwt> {

    private final JwtEncoder jwtEncoder;
    // TODO: init
    private OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer;

    public JwtOAuth2AccessTokenGenerator(JwtEncoder jwtEncoder) {
        Assert.notNull(jwtEncoder, "jwtEncoder cannot be null");
        this.jwtEncoder = jwtEncoder;
    }

    @Nullable
    @Override
    public Jwt generate(OAuth2TokenContext context) {
        // @formatter:off
        if (context.getTokenType() == null || !OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            return null;
        }
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType()) &&
                !OAuth2TokenFormat.SELF_CONTAINED.equals(context.getRegisteredClient().getTokenSettings().getAccessTokenFormat())) {
            return null;
        }
        // @formatter:on

        String issuer = "fast-charge";
        RegisteredClient registeredClient = context.getRegisteredClient();

        Instant issuedAt = Instant.now();
        Instant expiresAt = issuedAt.plus(registeredClient.getTokenSettings().getAccessTokenTimeToLive());
        JwsAlgorithm jwsAlgorithm = SignatureAlgorithm.RS256;

        // @formatter:off
        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder();

        claimsBuilder
                .issuer(issuer)
                .subject(context.getPrincipal().getName())// 用户名
                .audience(Collections.singletonList(registeredClient.getClientId()))
                .issuedAt(issuedAt)
                .expiresAt(expiresAt)
                .id(UUID.randomUUID().toString().replace("-",""))
                .claim("userID",123);// 在这里负上用户信息
        if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
            claimsBuilder.notBefore(issuedAt);
        }
        // @formatter:on

        JwsHeader.Builder jwsHeaderBuilder = JwsHeader.with(jwsAlgorithm);

        JwsHeader jwsHeader = jwsHeaderBuilder.build();
        JwtClaimsSet claims = claimsBuilder.build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims));
    }
}
