package chat.wisechat.oauth2.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author Siberia.Hu
 * @Date 2025/12/1  21:23
 */
@Data
@TableName(value = "e_operator_info")
public class EvcsOperatorInfo implements Serializable {

    private Long id;
    private String operatorId;
    private String selfOperatorId;
    private String operatorSecret;
    private String dataSecret;
    private String dataSecretIv;
    private String sigSecret;
    private String url;
    private Integer status;
    private Integer isDelete;
    private Long createUserId;
    private String createUserName;
    private LocalDateTime createTime;
    private Long updateUserId;
    private String updateUserName;
    private LocalDateTime updateTime;

}
