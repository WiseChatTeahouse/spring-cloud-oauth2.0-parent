package chat.wisechat.oauth2.auth.filter;

import chat.wisechat.common.core.utlis.AESUtil;
import chat.wisechat.common.core.utlis.JsonUtil;
import chat.wisechat.oauth2.open.dto.EvcsReq;
import chat.wisechat.oauth2.open.dto.EvcsResp;
import chat.wisechat.oauth2.system.feign.RemoteEvcsOperatorInfoFeign;
import chat.wisechat.oauth2.system.vo.EvcsOperatorInfoVo;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

/**
 * @Author Siberia.Hu
 * @Date 2025/12/1 16:54
 */
@Slf4j
@Component
public class EvcsFilter extends OncePerRequestFilter {

    @Resource
    private RemoteEvcsOperatorInfoFeign remoteEvcsOperatorInfoFeign;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String selfOperatorID = request.getHeader("evcs-operator");
        if (StringUtils.isBlank(selfOperatorID)) {
            filterChain.doFilter(request, response);
            return;
        }

        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(request);
        String payload = requestWrapper.getPayload();
        log.info("payload = {}", payload);
        // TODO:从Data中获取加密的 operatorID 和 operatorSec  并按照base auth 模式写入请求头中
        // 根据 Data中的 operatorID 和 selfOperatorID 去获取运营商的配置信息
        String operatorID = "MA0MX0001";
        EvcsOperatorInfoVo evcsOperatorInfoVo = remoteEvcsOperatorInfoFeign.findOperatorInfoByOperatorId(operatorID, selfOperatorID);
        String dataSecret = evcsOperatorInfoVo.getDataSecret();
        String dataSecretIv = evcsOperatorInfoVo.getDataSecretIv();
        // 解密改造请求头 添加 Authorization   采用基础的 Basic Auth 加密 用户名和密码
        EvcsReq evcsReq = JsonUtil.parseObject(payload, EvcsReq.class);
        String data = evcsReq.getData();
        try {
            data = AESUtil.decrypt(data, dataSecret, dataSecretIv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (StringUtils.isBlank(data)) {
            return;
        }
        Map<String, String> dataMap = JsonUtil.parseStrMap(data);
        operatorID = dataMap.get("OperatorID");
        String operatorSecret = dataMap.get("OperatorSecret");

        String authorization = Base64.getEncoder().encodeToString((operatorID + ":" + operatorSecret).getBytes(StandardCharsets.UTF_8));

        String newRequestBody = "grant_type=client_credentials&scope=server";
        // 创建带新请求体的包装器
        HttpRequestWrapper newRequestWrapper = new HttpRequestWrapper(requestWrapper, newRequestBody);
        // 添加必要的请求头
        newRequestWrapper.addHeader("Authorization", "Basic " + authorization);
        newRequestWrapper.addHeader("Content-Type", "application/x-www-form-urlencoded");

        filterChain.doFilter(newRequestWrapper, response);

        // TODO:待改造响应体
        EvcsResp.SUCCESS("", "");

    }
}
