package chat.wisechat.oauth2.auth.service;

import chat.wisechat.oauth2.system.feign.RemoteUserInfoFeign;
import chat.wisechat.oauth2.system.vo.UserInfoVo;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/21 14:21
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private RemoteUserInfoFeign remoteUserInfoFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoVo userInfoVo = remoteUserInfoFeign.loadUserByUsername(username);
        System.out.println("获取到用户信息");
        return null;
    }
}
