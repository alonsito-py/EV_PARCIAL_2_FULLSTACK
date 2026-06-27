package com.urbano.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/autenticacion")
    public Mono<ResponseEntity<Map<String, String>>> fallbackAutenticacion() {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Map.of(
                "error", "Servicio de autenticación no disponible",
                "mensaje", "Por favor intente nuevamente en unos momentos."
            )));
    }

    @GetMapping("/{service}")
    public Mono<ResponseEntity<Map<String, String>>> fallbackGenerico() {
        return Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(Map.of(
                "error", "Servicio no disponible temporalmente",
                "mensaje", "El microservicio no responde. Intente más tarde."
            )));
    }
}
