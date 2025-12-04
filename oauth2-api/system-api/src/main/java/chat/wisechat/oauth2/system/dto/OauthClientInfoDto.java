package chat.wisechat.oauth2.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/20 14:28
 */
@Data
public class OauthClientInfoDto implements Serializable {
    private Long id;
    private String clientId;
    private String clientSecret;
    private String clientName;
    private String scope;
    private String authorizedGrantTypes;
    private String redirectUris;
    private Long accessTokenValidity;
    private Long refreshTokenValidity;
    private Long platformId;
}
