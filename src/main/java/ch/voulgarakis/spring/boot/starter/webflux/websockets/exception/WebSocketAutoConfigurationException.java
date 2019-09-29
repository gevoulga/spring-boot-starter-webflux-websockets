package ch.voulgarakis.spring.boot.starter.webflux.websockets.exception;

public class WebSocketAutoConfigurationException extends RuntimeException {

    public WebSocketAutoConfigurationException(String message) {
        super(message);
    }

    public WebSocketAutoConfigurationException(Throwable cause) {
        super(cause);
    }

    public WebSocketAutoConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
