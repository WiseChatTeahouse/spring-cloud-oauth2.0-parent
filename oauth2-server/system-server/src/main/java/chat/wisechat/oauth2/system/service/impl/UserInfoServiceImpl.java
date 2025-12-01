package chat.wisechat.oauth2.system.service.impl;

import chat.wisechat.oauth2.system.entity.UserInfo;
import chat.wisechat.oauth2.system.dto.UserInfoDto;
import chat.wisechat.oauth2.system.mapper.UserInfoMapper;
import chat.wisechat.oauth2.system.service.UserInfoService;
import chat.wisechat.oauth2.system.vo.UserInfoVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/21 14:36
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Override
    public UserInfoVo loadUserByUsername(String username) {
        UserInfo userInfo = lambdaQuery().eq(UserInfo::getUsername, username).one();
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo;
    }

    @Override
    public void addUserInfo(UserInfoDto dto) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(dto, userInfo);
        save(userInfo);
    }
}
