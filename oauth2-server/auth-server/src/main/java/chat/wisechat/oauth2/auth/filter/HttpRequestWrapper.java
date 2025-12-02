package chat.wisechat.oauth2.auth.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class HttpRequestWrapper extends HttpServletRequestWrapper {
    // 新增方法，允许修改请求体
    // TODO:先这样后面看看需不需要调整一下
    private String payload;
    private final Map<String, String> customHeaders;
    private Map<String, String[]> parameterMap;
    private boolean parametersParsed = false;

    public HttpRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.payload = readBody(request);
        this.customHeaders = new HashMap<>();
    }

    // 新增构造函数，允许指定请求体内容
    public HttpRequestWrapper(HttpServletRequest request, String newPayload) throws IOException {
        super(request);
        this.payload = newPayload;
        this.customHeaders = new HashMap<>();
    }

    private String readBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(payload.getBytes(StandardCharsets.UTF_8));
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                // 不需要实现
            }

            @Override
            public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
    }

    public void addHeader(String name, String value) {
        this.customHeaders.put(name, value);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        Set<String> names = new HashSet<>();

        // 添加原始请求头名称
        Enumeration<String> originalNames = ((HttpServletRequest) getRequest()).getHeaderNames();
        while (originalNames.hasMoreElements()) {
            names.add(originalNames.nextElement());
        }

        // 添加自定义请求头名称
        names.addAll(customHeaders.keySet());

        return Collections.enumeration(names);
    }

    @Override
    public String getHeader(String name) {
        String headerValue = customHeaders.get(name);
        if (headerValue != null) {
            return headerValue;
        }
        return ((HttpServletRequest) getRequest()).getHeader(name);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
    }

    @Override
    public String getParameter(String name) {
        String[] values = getParameterValues(name);
        return values != null && values.length > 0 ? values[0] : null;
    }

    @Override
    public String[] getParameterValues(String name) {
        parseParametersIfNeeded();
        return parameterMap != null ? parameterMap.get(name) : null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        parseParametersIfNeeded();
        return parameterMap != null ? parameterMap : new HashMap<>();
    }

    private void parseParametersIfNeeded() {
        if (!parametersParsed) {
            parseParameters();
            parametersParsed = true;
        }
    }

    private void parseParameters() {
        parameterMap = new HashMap<>();
        if (payload != null && !payload.isEmpty()) {
            try {
                String[] pairs = payload.split("&");
                for (String pair : pairs) {
                    String[] keyValue = pair.split("=", 2);
                    if (keyValue.length == 2) {
                        String key = java.net.URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
                        String value = java.net.URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                        parameterMap.computeIfAbsent(key, k -> new String[0]);
                        String[] existingValues = parameterMap.get(key);
                        String[] newValues = new String[existingValues.length + 1];
                        System.arraycopy(existingValues, 0, newValues, 0, existingValues.length);
                        newValues[existingValues.length] = value;
                        parameterMap.put(key, newValues);
                    }
                }
            } catch (Exception e) {
                // 解析失败时保持空的 parameterMap 而不是 null
                parameterMap = new HashMap<>();
            }
        } else {
            parameterMap = new HashMap<>();
        }
    }

}
