package chat.wisechat.oauth2.system.service;

import chat.wisechat.oauth2.system.entity.OauthClientInfo;
import chat.wisechat.oauth2.system.vo.OauthClientInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/20 13:55
 */
public interface OauthClientInfoService extends IService<OauthClientInfo> {
    OauthClientInfoVo findClientInfoByClientId(String clientId);
}
