package com.academia.http;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RouteDefinition {
    private final HttpMethod method;
    private final List<String> templateSegments;
    private final RouteHandler handler;

    private RouteDefinition(HttpMethod method, List<String> templateSegments, RouteHandler handler) {
        this.method = method;
        this.templateSegments = templateSegments;
        this.handler = handler;
    }

    public static RouteDefinition route(HttpMethod method, String template, RouteHandler handler) {
        return new RouteDefinition(method, splitPath(template), handler);
    }

    public boolean matches(HttpMethod requestMethod, String path, Map<String, String> pathParams) {
        if (!method.equals(requestMethod)) {
            return false;
        }
        List<String> currentSegments = splitPath(path);
        if (currentSegments.size() != templateSegments.size()) {
            return false;
        }
        for (int i = 0; i < templateSegments.size(); i++) {
            String templateSegment = templateSegments.get(i);
            String currentSegment = currentSegments.get(i);
            if (isPathParam(templateSegment)) {
                pathParams.put(paramName(templateSegment), currentSegment);
            } else if (!templateSegment.equals(currentSegment)) {
                pathParams.clear();
                return false;
            }
        }
        return true;
    }

    public RouteHandler handler() {
        return handler;
    }

    private static boolean isPathParam(String segment) {
        return segment.startsWith("{") && segment.endsWith("}");
    }

    private static String paramName(String segment) {
        return segment.substring(1, segment.length() - 1);
    }

    private static List<String> splitPath(String path) {
        if (path == null || path.isBlank()) {
            return List.of();
        }
        String normalized = path.startsWith("/") ? path.substring(1) : path;
        String[] tokens = normalized.split("/");
        List<String> parts = new ArrayList<>();
        for (String token : tokens) {
            if (!token.isBlank()) {
                parts.add(token);
            }
        }
        return parts;
    }
}
