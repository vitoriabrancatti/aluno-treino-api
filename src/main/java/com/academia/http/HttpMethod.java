package com.academia.http;

import java.util.Arrays;
import java.util.Optional;

public enum HttpMethod {
    GET, POST, PUT, DELETE;

    public static Optional<HttpMethod> from(String raw) {
        return Arrays.stream(values())
                .filter(method -> method.name().equalsIgnoreCase(raw))
                .findFirst();
    }
}
