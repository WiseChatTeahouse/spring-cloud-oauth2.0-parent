package chat.wisechat.oauth2.auth.support;

import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/25  20:57
 */
public class ProjectOAuth2TokenCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {
    @Override
    public void customize(OAuth2TokenClaimsContext context) {
        OAuth2TokenClaimsSet.Builder claims = context.getClaims();
        claims.claim("license", "https://wisechat.chat");
        String clientId = context.getAuthorizationGrant().getName();
        claims.claim("clientId", clientId);
        // 写入用户信息
        ProjectUser pigUser = (ProjectUser) context.getPrincipal().getPrincipal();
        claims.claim("user_info", pigUser);

    }
}
