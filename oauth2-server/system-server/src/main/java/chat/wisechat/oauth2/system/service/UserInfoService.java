package chat.wisechat.oauth2.system.service;

import chat.wisechat.oauth2.system.entity.UserInfo;
import chat.wisechat.oauth2.system.entity.UserInfoDto;
import chat.wisechat.oauth2.system.vo.UserInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/21 14:35
 */
public interface UserInfoService extends IService<UserInfo> {
    UserInfoVo loadUserByUsername(String username);

    void addUserInfo(UserInfoDto dto);
}
