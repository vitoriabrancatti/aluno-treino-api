package com.codex.http;

import com.codex.dto.TreinoRequest;
import com.codex.service.TreinoService;
import com.codex.util.JsonMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class TreinoHandler implements HttpHandler {
    private final TreinoService treinoService;

    public TreinoHandler(TreinoService treinoService) {
        this.treinoService = treinoService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            if ("GET".equalsIgnoreCase(method) && path.matches("/treinos/\\d+")) {
                handleGetById(exchange);
            } else if ("GET".equalsIgnoreCase(method) && "/treinos".equals(path)) {
                handleList(exchange);
            } else if ("POST".equalsIgnoreCase(method) && "/treinos".equals(path)) {
                handleCreate(exchange);
            } else if ("PUT".equalsIgnoreCase(method) && path.matches("/treinos/\\d+")) {
                handleUpdate(exchange);
            } else if ("DELETE".equalsIgnoreCase(method) && path.matches("/treinos/\\d+")) {
                handleDelete(exchange);
            } else {
                HandlerSupport.handleError(exchange, 404, "Rota nao encontrada");
            }
        } catch (IllegalArgumentException e) {
            HandlerSupport.handleError(exchange, 400, e.getMessage());
        } catch (Exception e) {
            HandlerSupport.handleError(exchange, 500, "Erro interno");
        }
    }

    private void handleGetById(HttpExchange exchange) throws IOException {
        Long id = extractId(exchange.getRequestURI().getPath());
        HandlerSupport.writeJson(exchange, 200, treinoService.find(id));
    }

    private void handleList(HttpExchange exchange) throws IOException {
        Long alunoId = parseAlunoId(exchange.getRequestURI().getQuery());
        HandlerSupport.writeJson(exchange, 200, treinoService.listAll(alunoId));
    }

    private void handleCreate(HttpExchange exchange) throws IOException {
        String body = HandlerSupport.readBody(exchange);
        TreinoRequest request = JsonMapper.getInstance().readValue(body, TreinoRequest.class);
        HandlerSupport.writeJson(exchange, 201, treinoService.create(request));
    }

    private void handleUpdate(HttpExchange exchange) throws IOException {
        Long id = extractId(exchange.getRequestURI().getPath());
        String body = HandlerSupport.readBody(exchange);
        TreinoRequest request = JsonMapper.getInstance().readValue(body, TreinoRequest.class);
        HandlerSupport.writeJson(exchange, 200, treinoService.update(id, request));
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        Long id = extractId(exchange.getRequestURI().getPath());
        treinoService.delete(id);
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }

    private Long extractId(String path) {
        String[] parts = path.split("/");
        return Long.parseLong(parts[parts.length - 1]);
    }

    private Long parseAlunoId(String query) {
        if (query == null || query.isBlank()) {
            return null;
        }
        Map<String, String> params = Arrays.stream(query.split("&"))
                .map(param -> param.split("=", 2))
                .filter(pair -> pair.length == 2)
                .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));
        if (!params.containsKey("alunoId")) {
            return null;
        }
        return Long.parseLong(params.get("alunoId"));
    }
}
