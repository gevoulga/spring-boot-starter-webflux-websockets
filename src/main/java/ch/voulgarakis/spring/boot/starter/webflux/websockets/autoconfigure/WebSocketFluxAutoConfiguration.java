package ch.voulgarakis.spring.boot.starter.webflux.websockets.autoconfigure;

import ch.voulgarakis.spring.boot.starter.webflux.websockets.handler.WebSocketFluxHandlerMapping;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;

import java.util.List;

@Configuration
@AutoConfigurationPackage
//@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
public class WebSocketFluxAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WebSocketFluxHandlerMapping webSocketFluxHandlerMapping(List<WebSocketHandler> webSocketHandlers) {
        return new WebSocketFluxHandlerMapping(webSocketHandlers);
    }

    @Bean
    @ConditionalOnMissingBean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter(webSocketService());
    }

    @Bean
    @ConditionalOnMissingBean
    public WebSocketService webSocketService() {
        return new HandshakeWebSocketService(new ReactorNettyRequestUpgradeStrategy());
    }
}