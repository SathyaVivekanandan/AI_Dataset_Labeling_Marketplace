
package com.labelai.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public String health() {
        return "{\"status\":\"OK\",\"service\":\"LabelAI Backend\"}";
    }
}