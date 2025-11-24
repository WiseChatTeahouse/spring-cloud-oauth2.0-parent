package chat.wisechat.oauth2.auth.service;

import chat.wisechat.oauth2.auth.support.ProjectUser;
import chat.wisechat.oauth2.system.feign.RemoteUserInfoFeign;
import chat.wisechat.oauth2.system.vo.UserInfoVo;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/21 14:21
 */
@Primary
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private RemoteUserInfoFeign remoteUserInfoFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoVo userInfoVo = remoteUserInfoFeign.loadUserByUsername(username);
        Set<String> dbAuthsSet = new HashSet<>();
        dbAuthsSet.add("ROLE_" + "ADMIN");
        Collection<GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(dbAuthsSet.toArray(new String[0]));
        return new ProjectUser(userInfoVo.getUsername(), userInfoVo.getPassword(), authorities);
    }
}
