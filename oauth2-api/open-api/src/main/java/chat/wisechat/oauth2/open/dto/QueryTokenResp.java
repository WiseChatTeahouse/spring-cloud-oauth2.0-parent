package chat.wisechat.oauth2.open.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

/**
 * @Author Siberia.Hu
 * @Date 2025/12/3  20:43
 */
@Data
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class QueryTokenResp {
    private String operatorID;
    private Integer succStat;
    private String accessToken;
    private Long tokenAvailableTime;
    private Integer failReason;
}
