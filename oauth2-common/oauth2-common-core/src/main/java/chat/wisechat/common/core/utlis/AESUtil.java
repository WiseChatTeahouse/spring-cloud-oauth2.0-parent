package chat.wisechat.common.core.utlis;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Author Siberia.Hu
 * @Date 2025/12/2 15:08
 */
public class AESUtil {
    private static final String KEY_ALGORITHM = "AES";
    private static final String CBC_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";


    /**
     * 加密方法
     *
     * @param data 要加密的数据
     * @param key  密钥（长度必须为16字节）
     * @param iv   初始化向量（长度必须为16字节）
     * @return 加密后的Base64编码字符串
     * @throws Exception 加密过程中可能出现的异常
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的Base64编码数据
     * @param key           密钥（长度必须为16字节）
     * @param iv            初始化向量（长度必须为16字节）
     * @return 解密后的原始字符串
     * @throws Exception 解密过程中可能出现的异常
     */
    public static String decrypt(String encryptedData, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
