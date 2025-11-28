package chat.wisechat.oauth2.auth.support;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/28 14:04
 */
public class JwtOAuth2TokenCustomizer implements OAuth2TokenCustomizer<JwtEncodingContext> {
    @Override
    public void customize(JwtEncodingContext context) {
        JwtClaimsSet.Builder claims = context.getClaims();
        claims.claim("license", "https://wisechat.chat");
        String clientId = context.getAuthorizationGrant().getName();
        claims.claim("clientId", clientId);
        // 写入用户信息
        ProjectUser pigUser = (ProjectUser) context.getPrincipal().getPrincipal();
        claims.claim("user_info", pigUser);
    }
}
