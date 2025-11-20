package chat.wisechat.oauth2.system.service.impl;

import chat.wisechat.oauth2.system.dto.OauthClientInfoDto;
import chat.wisechat.oauth2.system.entity.OauthClientInfo;
import chat.wisechat.oauth2.system.mapper.OauthClientInfoMapper;
import chat.wisechat.oauth2.system.service.OauthClientInfoService;
import chat.wisechat.oauth2.system.vo.OauthClientInfoVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/20 13:55
 */
@Service
public class OauthClientInfoServiceImpl extends ServiceImpl<OauthClientInfoMapper, OauthClientInfo> implements OauthClientInfoService {


    @Override
    public void addClientInfo(OauthClientInfoDto dto) {
        OauthClientInfo oauthClientInfo = new OauthClientInfo();
        BeanUtils.copyProperties(dto, oauthClientInfo);
        //String randomStr = RandomStringUtils.secure().nextAlphanumeric(16);  随机字符串生成
        save(oauthClientInfo);
    }

    @Override
    public OauthClientInfoVo findClientInfoByClientId(String clientId) {
        OauthClientInfo oauthClientInfo = lambdaQuery().eq(OauthClientInfo::getClientId, clientId).one();
        OauthClientInfoVo oauthClientInfoVo = new OauthClientInfoVo();
        BeanUtils.copyProperties(oauthClientInfo, oauthClientInfoVo);
        return oauthClientInfoVo;
    }


}
