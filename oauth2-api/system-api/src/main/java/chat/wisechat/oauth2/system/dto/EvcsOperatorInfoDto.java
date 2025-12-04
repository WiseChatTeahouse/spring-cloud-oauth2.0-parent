package chat.wisechat.oauth2.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Siberia.Hu
 * @Date 2025/12/1  21:25
 */
@Data
public class EvcsOperatorInfoDto implements Serializable {
    private String operatorId;
    private String operatorName;
    private String selfOperatorId;
    private String operatorSecret;
    private String dataSecret;
    private String dataSecretIv;
    private String sigSecret;
    private String url;
}
