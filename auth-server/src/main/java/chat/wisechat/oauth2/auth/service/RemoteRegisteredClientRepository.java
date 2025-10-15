package chat.wisechat.oauth2.auth.service;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

/**
 * @Author Siberia.Hu
 * @Date 2025/10/15 19:46
 */
@Component
public class RemoteRegisteredClientRepository implements RegisteredClientRepository {
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
