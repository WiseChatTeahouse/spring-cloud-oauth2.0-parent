package chat.wisechat.oauth2.system.service;

import chat.wisechat.oauth2.system.dto.EvcsOperatorInfoDto;
import chat.wisechat.oauth2.system.entity.EvcsOperatorInfo;
import chat.wisechat.oauth2.system.vo.EvcsOperatorInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Author Siberia.Hu
 * @Date 2025/12/1  21:28
 */
public interface EvcsOperatorInfoService extends IService<EvcsOperatorInfo> {
    EvcsOperatorInfoVo findOperatorInfoByOperatorId(String operatorId, String selfOperatorId);

    void addOperatorInfo(EvcsOperatorInfoDto dto);
}
