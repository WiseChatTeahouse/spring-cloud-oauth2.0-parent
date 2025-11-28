package chat.wisechat.oauth2.open.service.impl;

import chat.wisechat.common.core.utlis.JsonUtil;
import chat.wisechat.oauth2.open.dto.CommonReq;
import chat.wisechat.oauth2.open.entity.ParkingCouponResult;
import chat.wisechat.oauth2.open.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/28  20:52
 */
@Slf4j
@Service
public class ApiServiceImpl implements ApiService {

    @Override
    public void parkingCouponResult(CommonReq req) {
        log.info("req:{}", JsonUtil.toJson(req));
        ParkingCouponResult parkingCouponResult = JsonUtil.parseObject(req, ParkingCouponResult.class);
        log.info("parkingCouponResult= {}", parkingCouponResult.toString());
    }
}
