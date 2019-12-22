package ch.voulgarakis.spring.boot.starter.webflux.websockets.converter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class WebSocketMessageConverter {

    private final List<HttpMessageConverter> converters;

    public WebSocketMessageConverter(List<HttpMessageConverter> converters) {
        this.converters = converters;
    }

    public <T> T convert(String text, Class<? extends T> clazz, MediaType mediaType) {
        for (HttpMessageConverter converter : converters) {
            if (converter.canRead(clazz, mediaType)) {
                InputMessage inputMessage = new InputMessage(text.getBytes());
                try {
                    return (T) converter.read(clazz, inputMessage);
                } catch (IOException e) {
                    throw new RuntimeException("Failed to convert to " + clazz + " using mediaType:[" + mediaType + "] the following text: " + text, e);
                }
            }
        }
        throw new RuntimeException(
                "Failed to convert to " + clazz + " using mediaType:[" + mediaType + "] the following text: " + text);
    }

    public <T> String convert(T t, Class<? extends T> clazz, MediaType mediaType) {
        for (HttpMessageConverter converter : converters) {
            if (converter.canWrite(clazz, mediaType)) {
                OutputMessage outputMessage = new OutputMessage();
                try {
                    converter.write(t, mediaType, outputMessage);
                    return new String(outputMessage.getBody().toByteArray());
                } catch (IOException e) {
                    throw new RuntimeException("Failed to convert to " + clazz + " using mediaType:[" + mediaType + "] the following object: " + t, e);
                }
            }
        }
        throw new RuntimeException("Failed to convert to " + clazz + " using mediaType:[" + mediaType + "] the following object: " + t);
    }

    public boolean canConvert(Class<?> clazz, MediaType mediaType) {
        return converters.stream()
                .anyMatch(converter -> converter.canWrite(clazz, mediaType) && converter.canRead(clazz, mediaType));
    }

    public static class OutputMessage implements HttpOutputMessage {

        private final HttpHeaders headers = new HttpHeaders();
        private final ByteArrayOutputStream body = new ByteArrayOutputStream(1024);


        @Override
        public ByteArrayOutputStream getBody() {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }
    }

    public static class InputMessage implements HttpInputMessage {

        private final HttpHeaders headers = new HttpHeaders();
        private final InputStream body;


        InputMessage(byte[] content) {
            Assert.notNull(content, "Byte array must not be null");
            this.body = new ByteArrayInputStream(content);
        }

        public InputMessage(InputStream body) {
            Assert.notNull(body, "InputStream must not be null");
            this.body = body;
        }


        @Override
        public HttpHeaders getHeaders() {
            return this.headers;
        }

        @Override
        public InputStream getBody() {
            return this.body;
        }
    }
}
