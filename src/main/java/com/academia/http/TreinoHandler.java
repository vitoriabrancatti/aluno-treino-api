package com.academia.http;

import com.academia.controller.TreinoController;
import com.academia.dto.TreinoRequest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.List;

public class TreinoHandler implements HttpHandler {
    private final RouterHandler router;

    public TreinoHandler(TreinoController controller) {
        this.router = new RouterHandler(List.of(
                RouteDefinition.route(HttpMethod.GET, "/treinos/{id}", ctx -> ctx.ok(controller.find(ctx.pathParamAsLong("id")))),
                RouteDefinition.route(HttpMethod.GET, "/treinos", ctx -> ctx.ok(controller.listAll(alunoIdFrom(ctx)))),
                RouteDefinition.route(HttpMethod.POST, "/treinos", ctx -> ctx.created(controller.create(ctx.readJson(TreinoRequest.class)))),
                RouteDefinition.route(HttpMethod.PUT, "/treinos/{id}", ctx -> ctx.ok(controller.update(ctx.pathParamAsLong("id"), ctx.readJson(TreinoRequest.class)))),
                RouteDefinition.route(HttpMethod.DELETE, "/treinos/{id}", ctx -> {
                    controller.delete(ctx.pathParamAsLong("id"));
                    ctx.noContent();
                })
        ));
    }

    private Long alunoIdFrom(RequestContext ctx) {
        return ctx.queryParam("alunoId")
                .map(raw -> {
                    try {
                        return Long.parseLong(raw);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("alunoId invalido");
                    }
                })
                .orElse(null);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        router.handle(exchange);
    }
}
