package chat.wisechat.common.core.utlis;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @Author Siberia.Hu
 * @Date 2025/12/2 15:16
 */
public class HmacMD5Util {
    private static final String KEY_ALGORITHM = "HmacMD5";

    /**
     * 生成HmacMD5签名并返回Hex编码结果
     *
     * @param data 要签名的数据
     * @param key  密钥
     * @return Hex编码的HmacMD5签名
     * @throws Exception 签名过程中可能出现的异常
     */
    public static String sign(String data, String key) throws Exception {
        Mac mac = Mac.getInstance(KEY_ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), KEY_ALGORITHM);
        mac.init(secretKeySpec);
        byte[] signatureBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(signatureBytes);
    }

    /**
     * 验证HmacMD5签名
     *
     * @param data      原始数据
     * @param key       密钥
     * @param signature 待验证的Hex编码签名
     * @return 签名是否有效
     * @throws Exception 验证过程中可能出现的异常
     */
    public static boolean verify(String data, String key, String signature) throws Exception {
        String computedSignature = sign(data, key);
        return computedSignature.equalsIgnoreCase(signature);
    }

    /**
     * 将字节数组转换为Hex编码字符串
     *
     * @param bytes 字节数组
     * @return Hex编码字符串
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
