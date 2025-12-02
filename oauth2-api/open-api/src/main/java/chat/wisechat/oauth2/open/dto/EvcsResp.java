package chat.wisechat.oauth2.open.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author Siberia.Hu
 * @Date 2025/12/2 17:05
 */
@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class EvcsResp {
    private int ret;

    private String msg;

    private String data;

    private String sig;

    public static EvcsResp SUCCESS(String data, String sign) {
        EvcsResp evcsResp = new EvcsResp();
        evcsResp.setRet(1);
        evcsResp.setData(data);
        evcsResp.setSig(sign);
        return evcsResp;
    }

}
