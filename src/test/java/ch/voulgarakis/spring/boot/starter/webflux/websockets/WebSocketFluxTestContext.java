package ch.voulgarakis.spring.boot.starter.webflux.websockets;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Configuration
@EnableAutoConfiguration
public class WebSocketFluxTestContext {
    @Bean
    public WebSocketHandler echoHandler() {
        return new EchoHandler();
    }

    @WebSocketMapping("/echo")
    public class EchoHandler implements WebSocketHandler {
        @Override
        public Mono<Void> handle(WebSocketSession session) {
            return session
                    .send(session.receive()
                            .map(msg -> "RECEIVED ON SERVER :: " + msg.getPayloadAsText())
                            .map(session::textMessage)
                    );
        }
    }
}
