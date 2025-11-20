package chat.wisechat.oauth2.system.controller;

import chat.wisechat.oauth2.system.service.OauthClientInfoService;
import chat.wisechat.oauth2.system.vo.OauthClientInfoVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/20 13:53
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Resource
    private OauthClientInfoService oauthClientService;

    @GetMapping("/client/findClientInfoByClientId/{clientId}")
    public OauthClientInfoVo findClientInfoByClientId(@PathVariable("clientId") String clientId) {
        return oauthClientService.findClientInfoByClientId(clientId);
    }

}
