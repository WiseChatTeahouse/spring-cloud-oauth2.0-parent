package chat.wisechat.oauth2.auth.filter;

import chat.wisechat.common.core.utlis.AESUtil;
import chat.wisechat.common.core.utlis.HmacMD5Util;
import chat.wisechat.common.core.utlis.JsonUtil;
import chat.wisechat.oauth2.open.dto.EvcsReq;
import chat.wisechat.oauth2.open.dto.EvcsResp;
import chat.wisechat.oauth2.open.dto.QueryTokenReq;
import chat.wisechat.oauth2.open.dto.QueryTokenResp;
import chat.wisechat.oauth2.system.feign.RemoteEvcsOperatorInfoFeign;
import chat.wisechat.oauth2.system.vo.EvcsOperatorInfoVo;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
        EvcsReq evcsReq = JsonUtil.parseObject(payload, EvcsReq.class);
        // 根据 Data中的 operatorID 和 selfOperatorID 去获取运营商的配置信息
        String operatorID = evcsReq.getOperatorID();
        EvcsOperatorInfoVo evcsOperatorInfoVo = remoteEvcsOperatorInfoFeign.findOperatorInfoByOperatorId(operatorID, selfOperatorID);
        String dataSecret = evcsOperatorInfoVo.getDataSecret();
        String dataSecretIv = evcsOperatorInfoVo.getDataSecretIv();
        // 解密改造请求头 添加 Authorization   采用基础的 Basic Auth 加密 用户名和密码
        String data = evcsReq.getData();
        try {
            data = AESUtil.decrypt(data, dataSecret, dataSecretIv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (StringUtils.isBlank(data)) {
            return;
        }
        QueryTokenReq queryTokenReq = JsonUtil.parseObject(data, QueryTokenReq.class);
        operatorID = queryTokenReq.getOperatorID();
        String operatorSecret = queryTokenReq.getOperatorSecret();

        String authorization = Base64.getEncoder().encodeToString((operatorID + ":" + operatorSecret).getBytes(StandardCharsets.UTF_8));

        String newRequestBody = "grant_type=client_credentials&scope=server";
        // 创建带新请求体的包装器
        HttpRequestWrapper newRequestWrapper = new HttpRequestWrapper(requestWrapper, newRequestBody);
        // 添加必要的请求头
        newRequestWrapper.addHeader(HttpHeaders.AUTHORIZATION, "Basic " + authorization);
        newRequestWrapper.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        HttpResponseWrapper httpResponseWrapper = new HttpResponseWrapper(response);
        filterChain.doFilter(newRequestWrapper, httpResponseWrapper);

        // 获取原始响应体内容并修改
        byte[] responseData = httpResponseWrapper.getCaptureAsBytes();
        // 这里可以根据业务需求修改响应体内容
        String originalResponseBody = new String(responseData, StandardCharsets.UTF_8);
        Map<String, String> originalResp = JsonUtil.parseStrMap(originalResponseBody);
        String token = originalResp.get("access_token");
        String expiresAt = originalResp.get("expires_in");

        QueryTokenResp queryTokenResp = new QueryTokenResp();
        queryTokenResp.setOperatorID(operatorID);
        queryTokenResp.setSuccStat(1);
        queryTokenResp.setAccessToken(token);
        queryTokenResp.setTokenAvailableTime(Long.valueOf(expiresAt));
        queryTokenResp.setFailReason(0);
        String encrypt = "";
        try {
            encrypt = AESUtil.encrypt(JsonUtil.toJson(queryTokenResp), dataSecret, dataSecretIv);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 构建响应体
        EvcsResp evcsResp = new EvcsResp();
        evcsResp.setRet(1);
        evcsResp.setMsg("请求成功");
        evcsResp.setData(encrypt);
        String signBeforeStr = String.format("%s%s%s", evcsResp.getRet(), evcsResp.getMsg(), evcsResp.getData());
        try {
            String sign = HmacMD5Util.sign(signBeforeStr, evcsOperatorInfoVo.getSelfSigSecret());
            evcsResp.setSig(sign);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String evcsRespJsonStr = JsonUtil.toJson(evcsResp);
        // 设置新的响应体返回给前端
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(evcsRespJsonStr);
    }
}
