package com.academia.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RouterHandler implements HttpHandler {
    private final List<RouteDefinition> routes;

    public RouterHandler(List<RouteDefinition> routes) {
        this.routes = routes;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        RequestContext context = new RequestContext(exchange, Map.of());
        HttpMethod method = HttpMethod.from(exchange.getRequestMethod()).orElse(null);
        if (method == null) {
            context.error(405, "Metodo nao suportado");
            return;
        }
        String path = exchange.getRequestURI().getPath();
        for (RouteDefinition route : routes) {
            Map<String, String> pathParams = new HashMap<>();
            if (route.matches(method, path, pathParams)) {
                RequestContext matchedContext = new RequestContext(exchange, pathParams);
                try {
                    route.handler().handle(matchedContext);
                } catch (IllegalArgumentException e) {
                    matchedContext.error(400, e.getMessage());
                } catch (Exception e) {
                    matchedContext.error(500, "Erro interno");
                }
                return;
            }
        }
        context.error(404, "Rota nao encontrada");
    }
}
