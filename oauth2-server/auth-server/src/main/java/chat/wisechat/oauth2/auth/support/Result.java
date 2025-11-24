package chat.wisechat.oauth2.auth.support;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/24  21:55
 */
@Data
public class Result<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    public static <T> Result<T> restResult(T data, int code, String msg) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
