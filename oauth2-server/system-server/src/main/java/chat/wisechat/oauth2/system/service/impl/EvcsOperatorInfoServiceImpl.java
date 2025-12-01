package chat.wisechat.oauth2.system.service.impl;

import chat.wisechat.oauth2.system.entity.EvcsOperatorInfo;
import chat.wisechat.oauth2.system.mapper.EvcsOperatorInfoMapper;
import chat.wisechat.oauth2.system.service.EvcsOperatorInfoService;
import chat.wisechat.oauth2.system.vo.EvcsOperatorInfoVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @Author Siberia.Hu
 * @Date 2025/12/1  21:29
 */
@Service
public class EvcsOperatorInfoServiceImpl extends ServiceImpl<EvcsOperatorInfoMapper, EvcsOperatorInfo> implements EvcsOperatorInfoService {
    @Override
    public EvcsOperatorInfoVo findOperatorInfoByOperatorId(String operatorId, String selfOperatorId) {
        EvcsOperatorInfo evcsOperatorInfo = lambdaQuery().eq(EvcsOperatorInfo::getOperatorId, operatorId)
                .eq(EvcsOperatorInfo::getSelfOperatorId, selfOperatorId).one();
        EvcsOperatorInfoVo evcsOperatorInfoVo = new EvcsOperatorInfoVo();
        BeanUtils.copyProperties(evcsOperatorInfo, evcsOperatorInfoVo);
        return evcsOperatorInfoVo;
    }
}
