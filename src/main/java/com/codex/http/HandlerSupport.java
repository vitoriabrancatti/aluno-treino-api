package com.codex.http;

import com.codex.dto.ErrorResponse;
import com.codex.util.JsonMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public final class HandlerSupport {
    private HandlerSupport() {
    }

    public static String readBody(HttpExchange exchange) throws IOException {
        try (InputStream input = exchange.getRequestBody()) {
            return new String(input.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public static void writeJson(HttpExchange exchange, int status, Object body) throws IOException {
        byte[] payload = JsonMapper.getInstance().writeValueAsBytes(body);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, payload.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(payload);
        }
    }

    public static void handleError(HttpExchange exchange, int status, String message) throws IOException {
        writeJson(exchange, status, new ErrorResponse(message));
    }
}
