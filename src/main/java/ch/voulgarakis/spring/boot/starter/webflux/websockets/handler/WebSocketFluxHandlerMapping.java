package ch.voulgarakis.spring.boot.starter.webflux.websockets.handler;

import ch.voulgarakis.spring.boot.starter.webflux.websockets.WebSocketMapping;
import ch.voulgarakis.spring.boot.starter.webflux.websockets.exception.WebSocketAutoConfigurationException;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;

import java.util.List;
import java.util.stream.Stream;

public class WebSocketFluxHandlerMapping extends SimpleUrlHandlerMapping {

    public WebSocketFluxHandlerMapping(List<WebSocketHandler> webSocketHandlers) {
        webSocketHandlers.forEach(webSocketHandler -> {
            WebSocketMapping[] webSocketMappingAnnotations = webSocketHandler.getClass()
                    .getAnnotationsByType(WebSocketMapping.class);
            if (webSocketMappingAnnotations.length > 0)
                Stream.of(webSocketMappingAnnotations)
                        .map(WebSocketMapping::value)
                        .flatMap(Stream::of)
                        .forEach(urlPath -> registerHandler(urlPath, webSocketHandler));
            else
                throw new WebSocketAutoConfigurationException("No @WebSocketMapping defined for WebSocketHandler: " + webSocketHandler);
        });

        //Set this as the first handler mapper, otherwise: Invalid handshake response getStatus: 404 Not Found
        setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
