package chat.wisechat.oauth2.system.service.impl;

import chat.wisechat.common.core.utlis.JsonUtil;
import chat.wisechat.oauth2.system.dto.EvcsOperatorInfoDto;
import chat.wisechat.oauth2.system.dto.OauthClientInfoDto;
import chat.wisechat.oauth2.system.entity.EvcsOperatorInfo;
import chat.wisechat.oauth2.system.mapper.EvcsOperatorInfoMapper;
import chat.wisechat.oauth2.system.service.EvcsOperatorInfoService;
import chat.wisechat.oauth2.system.service.OauthClientInfoService;
import chat.wisechat.oauth2.system.vo.EvcsOperatorInfoVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Siberia.Hu
 * @Date 2025/12/1  21:29
 */
@Slf4j
@Service
public class EvcsOperatorInfoServiceImpl extends ServiceImpl<EvcsOperatorInfoMapper, EvcsOperatorInfo> implements EvcsOperatorInfoService {

    @Resource
    private OauthClientInfoService oauthClientInfoService;

    @Override
    public EvcsOperatorInfoVo findOperatorInfoByOperatorId(String operatorId, String selfOperatorId) {
        EvcsOperatorInfo evcsOperatorInfo = lambdaQuery().eq(EvcsOperatorInfo::getOperatorId, operatorId)
                .eq(EvcsOperatorInfo::getSelfOperatorId, selfOperatorId).one();
        EvcsOperatorInfoVo evcsOperatorInfoVo = new EvcsOperatorInfoVo();
        BeanUtils.copyProperties(evcsOperatorInfo, evcsOperatorInfoVo);
        return evcsOperatorInfoVo;
    }

    @Override
    @Transactional
    public void addOperatorInfo(EvcsOperatorInfoDto dto) {
        EvcsOperatorInfo evcsOperatorInfo = new EvcsOperatorInfo();
        BeanUtils.copyProperties(dto, evcsOperatorInfo);

        evcsOperatorInfo.setSelfSigSecret(RandomStringUtils.secure().nextAlphanumeric(16));
        evcsOperatorInfo.setSelfDataSecret(RandomStringUtils.secure().nextAlphanumeric(16));
        evcsOperatorInfo.setSelfDataSecretIv(RandomStringUtils.secure().nextAlphanumeric(16));

        save(evcsOperatorInfo);
        log.info(JsonUtil.toJson(evcsOperatorInfo));
        OauthClientInfoDto oauthClientInfo = new OauthClientInfoDto();
        oauthClientInfo.setClientId(evcsOperatorInfo.getOperatorId());
        oauthClientInfo.setClientName(evcsOperatorInfo.getOperatorName());
        oauthClientInfo.setScope("open-server");
        oauthClientInfo.setAuthorizedGrantTypes("client_credentials");
        oauthClientInfo.setPlatformId(evcsOperatorInfo.getId());
        oauthClientInfoService.addClientInfo(oauthClientInfo);
    }
}
