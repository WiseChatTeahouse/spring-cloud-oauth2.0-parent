package chat.wisechat.oauth2.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/13 16:04
 */
@SpringBootApplication
@MapperScan("chat.wisechat.**.mapper")
public class SystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
