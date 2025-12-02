package chat.wisechat.oauth2.system.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Siberia.Hu
 * @Date 2025/12/1  21:46
 */
@Data
public class EvcsOperatorInfoVo implements Serializable {
    private Long id;
    private String operatorId;
    private String selfOperatorId;
    private String operatorSecret;
    private String dataSecret;
    private String dataSecretIv;
    private String sigSecret;
    private String url;
    private String selfDataSecret;
    private String selfDataSecretIv;
    private String selfSigSecret;
    private Integer status;
}
