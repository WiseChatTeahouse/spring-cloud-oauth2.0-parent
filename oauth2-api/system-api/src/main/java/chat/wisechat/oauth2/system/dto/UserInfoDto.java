package chat.wisechat.oauth2.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/21 14:27
 */
@Data
public class UserInfoDto implements Serializable {
    private Long id;
    private String username;
    private String password;
}
