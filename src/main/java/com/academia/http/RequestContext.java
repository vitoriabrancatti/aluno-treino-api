package com.academia.http;

import com.academia.dto.ErrorResponse;
import com.academia.util.JsonMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RequestContext {
    private final HttpExchange exchange;
    private final Map<String, String> pathParams;
    private String cachedBody;

    public RequestContext(HttpExchange exchange, Map<String, String> pathParams) {
        this.exchange = exchange;
        this.pathParams = new HashMap<>(pathParams);
    }

    public String path() {
        return exchange.getRequestURI().getPath();
    }

    public HttpMethod method() {
        return HttpMethod.from(exchange.getRequestMethod()).orElseThrow();
    }

    public Optional<String> queryParam(String name) {
        URI uri = exchange.getRequestURI();
        String query = uri.getQuery();
        if (query == null || query.isBlank()) {
            return Optional.empty();
        }
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2 && URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8).equals(name)) {
                return Optional.of(URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8));
            }
        }
        return Optional.empty();
    }

    public <T> T readJson(Class<T> type) throws IOException {
        String body = readBody();
        return JsonMapper.getInstance().readValue(body, type);
    }

    public long pathParamAsLong(String name) {
        if (!pathParams.containsKey(name)) {
            throw new IllegalArgumentException("Parametro de caminho ausente: " + name);
        }
        try {
            return Long.parseLong(pathParams.get(name));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Parametro de caminho invalido: " + name);
        }
    }

    public void ok(Object body) throws IOException {
        writeJson(200, body);
    }

    public void created(Object body) throws IOException {
        writeJson(201, body);
    }

    public void noContent() throws IOException {
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }

    public void error(int status, String message) throws IOException {
        writeJson(status, new ErrorResponse(message));
    }

    private String readBody() throws IOException {
        if (cachedBody != null) {
            return cachedBody;
        }
        try (InputStream input = exchange.getRequestBody()) {
            cachedBody = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            return cachedBody;
        }
    }

    private void writeJson(int status, Object body) throws IOException {
        byte[] payload = JsonMapper.getInstance().writeValueAsBytes(body);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, payload.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(payload);
        }
    }
}
