package com.codex.http;

import com.codex.dto.AlunoRequest;
import com.codex.service.AlunoService;
import com.codex.util.JsonMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class AlunoHandler implements HttpHandler {
    private final AlunoService alunoService;

    public AlunoHandler(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            if ("GET".equalsIgnoreCase(method) && path.matches("/alunos/\\d+")) {
                handleGetById(exchange);
            } else if ("GET".equalsIgnoreCase(method) && "/alunos".equals(path)) {
                HandlerSupport.writeJson(exchange, 200, alunoService.listAll());
            } else if ("POST".equalsIgnoreCase(method) && "/alunos".equals(path)) {
                handleCreate(exchange);
            } else if ("PUT".equalsIgnoreCase(method) && path.matches("/alunos/\\d+")) {
                handleUpdate(exchange);
            } else if ("DELETE".equalsIgnoreCase(method) && path.matches("/alunos/\\d+")) {
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
        HandlerSupport.writeJson(exchange, 200, alunoService.find(id));
    }

    private void handleCreate(HttpExchange exchange) throws IOException {
        String body = HandlerSupport.readBody(exchange);
        AlunoRequest request = JsonMapper.getInstance().readValue(body, AlunoRequest.class);
        HandlerSupport.writeJson(exchange, 201, alunoService.create(request));
    }

    private void handleUpdate(HttpExchange exchange) throws IOException {
        Long id = extractId(exchange.getRequestURI().getPath());
        String body = HandlerSupport.readBody(exchange);
        AlunoRequest request = JsonMapper.getInstance().readValue(body, AlunoRequest.class);
        HandlerSupport.writeJson(exchange, 200, alunoService.update(id, request));
    }

    private void handleDelete(HttpExchange exchange) throws IOException {
        Long id = extractId(exchange.getRequestURI().getPath());
        alunoService.delete(id);
        exchange.sendResponseHeaders(204, -1);
        exchange.close();
    }

    private Long extractId(String path) {
        String[] parts = path.split("/");
        return Long.parseLong(parts[parts.length - 1]);
    }
}
