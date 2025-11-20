package chat.wisechat.oauth2.system.feign;

import chat.wisechat.oauth2.system.vo.OauthClientInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/18  21:48
 */
@FeignClient(contextId = "remoteOauthClientInfoFeign", value = "system-server")
public interface RemoteOauthClientInfoFeign {
    @GetMapping("/api/client/findClientInfoByClientId/{clientId}")
    OauthClientInfoVo findClientInfoByClientId(@PathVariable("clientId") String clientId);
}
