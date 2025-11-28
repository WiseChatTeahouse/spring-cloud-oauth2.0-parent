package chat.wisechat.common.core.utlis;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/28  20:27
 */
public class JsonUtil {

    private static final ObjectMapper jsonMapper;

    public static ObjectMapper getMapper() {
        return jsonMapper;
    }

    public static <T> T parseObject(Object obj, Class<T> clazz) {
        try {
            return getMapper().convertValue(obj, clazz);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJson(Object obj) {
        try {
            return getMapper().writeValueAsString(obj);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static {
        jsonMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
