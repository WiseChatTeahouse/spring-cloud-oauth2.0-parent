package chat.wisechat.oauth2.open.dto;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用DTO  方便验签 方便后面转换成对应的DTO
 *
 * @Author Siberia.Hu
 * @Date 2025/11/28  20:16
 */
@Data
public class CommonReq {

    Map<String, Object> commonParams = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getCommonParams() {
        return this.commonParams;
    }

    @JsonAnySetter
    public void setCommonParams(String key, Object value) {
        this.commonParams.put(key, value);
    }
}

