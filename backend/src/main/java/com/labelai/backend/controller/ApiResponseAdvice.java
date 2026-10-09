package com.labelai.backend.controller;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.util.Map;

/** Standardizes successful application API payloads as success/data/message. */
@RestControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        String packageName = returnType.getContainingClass().getPackageName();
        return packageName.startsWith("com.labelai.backend.controller") || packageName.startsWith("com.labelai.backend.ai");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (response instanceof org.springframework.http.server.ServletServerHttpResponse servletResponse
                && servletResponse.getServletResponse().getStatus() >= 200
                && servletResponse.getServletResponse().getStatus() < 300
                && body != null) {
            return Map.of("success", true, "data", body, "message", "Request successful");
        }
        return body;
    }
}
