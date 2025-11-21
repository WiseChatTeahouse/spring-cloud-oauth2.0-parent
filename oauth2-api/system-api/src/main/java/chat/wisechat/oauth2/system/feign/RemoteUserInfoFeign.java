package chat.wisechat.oauth2.system.feign;

import chat.wisechat.oauth2.system.vo.UserInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/21 14:25
 */
@FeignClient(name = "remoteUserInfoFeign", url = "http://localhost:7911")
public interface RemoteUserInfoFeign {

    @GetMapping("/api/user/loadUserByUsername/{username}")
    UserInfoVo loadUserByUsername(@PathVariable("username") String username);
}
