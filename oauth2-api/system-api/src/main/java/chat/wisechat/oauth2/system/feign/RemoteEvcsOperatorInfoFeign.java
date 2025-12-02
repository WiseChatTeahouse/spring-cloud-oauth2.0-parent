package chat.wisechat.oauth2.system.feign;


import chat.wisechat.oauth2.system.vo.EvcsOperatorInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Siberia.Hu
 * @Date 2025/12/1  21:17
 */
@FeignClient(name = "remoteEvcsOperatorInfoFeign", url = "http://localhost:7911")
public interface RemoteEvcsOperatorInfoFeign {

    @GetMapping("/api/evcs/findByOperatorId/{operatorId}/{selfOperatorId}")
    EvcsOperatorInfoVo findOperatorInfoByOperatorId(@PathVariable("operatorId") String operatorId,
                                                    @PathVariable("selfOperatorId") String selfOperatorId);


}
