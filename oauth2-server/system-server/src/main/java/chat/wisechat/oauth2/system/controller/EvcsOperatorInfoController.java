package chat.wisechat.oauth2.system.controller;

import chat.wisechat.oauth2.system.dto.EvcsOperatorInfoDto;
import chat.wisechat.oauth2.system.service.EvcsOperatorInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Siberia.Hu
 * @Date 2025/12/4  20:20
 */
@RestController
@RequestMapping("/operator")
public class EvcsOperatorInfoController {

    @Resource
    private EvcsOperatorInfoService evcsOperatorInfoService;

    @PostMapping("/addUserInfo")
    public void addOperatorInfo(@RequestBody EvcsOperatorInfoDto dto) {
        evcsOperatorInfoService.addOperatorInfo(dto);
    }


}
