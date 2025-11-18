package chat.wisechat.oauth2.auth.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/18  21:26
 */
@FeignClient(contextId = "remoteClientFeign", value = "client-one-server")
public interface RemoteClientFeign {

    @GetMapping("/client/findClientDetailsById/{clientId}")
    Object findClientDetailsById(@PathVariable("clientId") String clientId);
}
