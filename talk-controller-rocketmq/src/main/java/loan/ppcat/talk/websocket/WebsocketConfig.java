package loan.ppcat.talk.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@Configuration
@EnableWebSocket
public class WebsocketConfig implements WebSocketConfigurer {
    @Inject
    private WebSocketHandler webSocketHandler;
    @Inject
    private HandshakeInterceptor handshakeInterceptor;

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/sendDataApi").addInterceptors(handshakeInterceptor);
    }

}