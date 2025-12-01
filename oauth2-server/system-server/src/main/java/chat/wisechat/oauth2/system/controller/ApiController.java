package chat.wisechat.oauth2.system.controller;

import chat.wisechat.oauth2.system.service.EvcsOperatorInfoService;
import chat.wisechat.oauth2.system.service.OauthClientInfoService;
import chat.wisechat.oauth2.system.service.UserInfoService;
import chat.wisechat.oauth2.system.vo.EvcsOperatorInfoVo;
import chat.wisechat.oauth2.system.vo.OauthClientInfoVo;
import chat.wisechat.oauth2.system.vo.UserInfoVo;
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
    private UserInfoService userInfoService;
    @Resource
    private OauthClientInfoService oauthClientService;
    @Resource
    private EvcsOperatorInfoService evcsOperatorInfoService;

    @GetMapping("/client/findClientInfoByClientId/{clientId}")
    public OauthClientInfoVo findClientInfoByClientId(@PathVariable("clientId") String clientId) {
        return oauthClientService.findClientInfoByClientId(clientId);
    }

    @GetMapping("/user/loadUserByUsername/{username}")
    public UserInfoVo loadUserByUsername(@PathVariable("username") String username) {
        return userInfoService.loadUserByUsername(username);
    }

    @GetMapping("/evcs/findByOperatorId/{operatorId}/{selfOperatorId}")
    public EvcsOperatorInfoVo findOperatorInfoByOperatorId(@PathVariable("operatorId") String operatorId,
                                                           @PathVariable("selfOperatorId") String selfOperatorId) {
        return evcsOperatorInfoService.findOperatorInfoByOperatorId(operatorId, selfOperatorId);
    }

}
