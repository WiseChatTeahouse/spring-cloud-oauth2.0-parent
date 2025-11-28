package chat.wisechat.oauth2.open.controller;

import chat.wisechat.common.core.utlis.JsonUtil;
import chat.wisechat.oauth2.open.dto.CommonReq;
import chat.wisechat.oauth2.open.service.ApiService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/28  20:13
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {

    @Resource
    private ApiService apiService;

    @RequestMapping("/parking/coupon/result")
    public void parkingCouponResult(@RequestBody CommonReq req) {
        apiService.parkingCouponResult(req);
    }

    @RequestMapping("/*")
    public void common(HttpServletRequest request, @RequestBody CommonReq req) {
        log.warn("uri: {} req:{}", request.getRequestURI(), JsonUtil.toJson(req));
    }

}
