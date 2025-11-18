package chat.wisechat.oauth2.auth.service;

import chat.wisechat.oauth2.auth.feign.RemoteClientFeign;
import jakarta.annotation.Resource;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

/**
 * @Author Siberia.Hu
 * @Date 2025/11/18  21:23
 */
@Service
public class RemoteRegisteredClientRepository implements RegisteredClientRepository {

    @Resource
    private RemoteClientFeign remoteClientFeign;

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return null;
    }
}
