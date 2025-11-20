package chat.wisechat.oauth2.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/12  22:37
 */
@SpringBootApplication
@EnableFeignClients("chat.wisechat.**.feign")
public class AuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }
}
