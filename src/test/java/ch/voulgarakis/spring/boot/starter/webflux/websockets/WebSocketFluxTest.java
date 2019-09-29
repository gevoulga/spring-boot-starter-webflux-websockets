package ch.voulgarakis.spring.boot.starter.webflux.websockets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebSocketFluxTestContext.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class WebSocketFluxTest {
    private static final Logger LOG = LoggerFactory.getLogger(WebSocketFluxTest.class);

    @LocalServerPort
    private int port;

    @Test
    public void testAutoConfiguredBeans() {

        WebSocketHandler sendingOnServer = session -> session.send(
                Mono.just(session.textMessage("SENDING ON SERVER")))
                .thenMany(session.receive()
                        .map(WebSocketMessage::getPayloadAsText)
                        .log()
                        .doOnNext(s -> assertEquals("RECEIVED ON SERVER :: SENDING ON SERVER", s))
                        .take(Duration.ofSeconds(1))
                )
                .then();

        LOG.info("port used: {}", port);
        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(URI.create("ws://localhost:" + port + "/echo"), sendingOnServer)
                .block(Duration.ofSeconds(10L));
    }
}