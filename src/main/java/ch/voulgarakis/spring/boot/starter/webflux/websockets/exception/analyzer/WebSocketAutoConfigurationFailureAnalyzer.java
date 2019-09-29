package ch.voulgarakis.spring.boot.starter.webflux.websockets.exception.analyzer;

import ch.voulgarakis.spring.boot.starter.webflux.websockets.exception.WebSocketAutoConfigurationException;
import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class WebSocketAutoConfigurationFailureAnalyzer extends AbstractFailureAnalyzer<WebSocketAutoConfigurationException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, WebSocketAutoConfigurationException cause) {
        return new FailureAnalysis(cause.getMessage(), "Websocket endpoint registration failed", cause);
    }

}
