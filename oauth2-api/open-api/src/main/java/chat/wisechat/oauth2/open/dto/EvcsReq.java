package chat.wisechat.oauth2.open.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * @Author Siberia.Hu
 * @Date 2025/12/2 17:05
 */
@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class EvcsReq {
    private String operatorID;

    private String data;

    private String timeStamp;

    private String seq;

    private String sig;
}
