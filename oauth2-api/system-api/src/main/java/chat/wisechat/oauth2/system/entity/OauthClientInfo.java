package chat.wisechat.oauth2.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/18  21:47
 */
@Data
@TableName(value = "sys_oauth_client_info")
public class OauthClientInfo implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
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
    private Integer isDelete;
    private Long createUserId;
    private String createUserName;
    private LocalDateTime createTime;
    private Long updateUserId;
    private String updateUserName;
    private LocalDateTime updateTime;
}
