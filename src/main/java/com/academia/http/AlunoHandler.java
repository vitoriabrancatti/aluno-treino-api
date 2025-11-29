package com.academia.http;

import com.academia.controller.AlunoController;
import com.academia.dto.AlunoRequest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.List;

public class AlunoHandler implements HttpHandler {
    private final RouterHandler router;

    public AlunoHandler(AlunoController controller) {
        this.router = new RouterHandler(List.of(
                RouteDefinition.route(HttpMethod.GET, "/alunos", ctx -> ctx.ok(controller.listAll())),
                RouteDefinition.route(HttpMethod.GET, "/alunos/{id}", ctx -> ctx.ok(controller.find(ctx.pathParamAsLong("id")))),
                RouteDefinition.route(HttpMethod.POST, "/alunos", ctx -> ctx.created(controller.create(ctx.readJson(AlunoRequest.class)))),
                RouteDefinition.route(HttpMethod.PUT, "/alunos/{id}", ctx -> ctx.ok(controller.update(ctx.pathParamAsLong("id"), ctx.readJson(AlunoRequest.class)))),
                RouteDefinition.route(HttpMethod.DELETE, "/alunos/{id}", ctx -> {
                    controller.delete(ctx.pathParamAsLong("id"));
                    ctx.noContent();
                })
        ));
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        router.handle(exchange);
    }
}
