package chat.wisechat.oauth2.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/20 13:49
 */
@Data
public class OauthClientInfoVo implements Serializable {
    private Long id;
    private String clientId;
    private String clientSecret;
    private String clientName;
    private String scope;
    private String authorizedGrantTypes;
    private String redirectUris;
    private Long accessTokenValidity;
    private Long refreshTokenValidity;
}
