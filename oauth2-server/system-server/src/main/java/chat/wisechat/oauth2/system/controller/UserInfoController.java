package chat.wisechat.oauth2.system.controller;

import chat.wisechat.oauth2.system.entity.UserInfoDto;
import chat.wisechat.oauth2.system.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/21 14:40
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/addUserInfo")
    public void addUserInfo(@RequestBody UserInfoDto dto) {
        userInfoService.addUserInfo(dto);
    }
}
