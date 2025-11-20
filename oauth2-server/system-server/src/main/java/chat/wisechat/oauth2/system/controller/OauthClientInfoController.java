package chat.wisechat.oauth2.system.controller;

import chat.wisechat.oauth2.system.service.OauthClientInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/20 13:52
 */
@RestController
@RequestMapping("/oauthClient")
public class OauthClientInfoController {

    @Resource
    private OauthClientInfoService oauthClientService;

}
