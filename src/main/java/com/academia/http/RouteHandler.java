package com.academia.http;

import java.io.IOException;

@FunctionalInterface
public interface RouteHandler {
    void handle(RequestContext context) throws IOException;
}
