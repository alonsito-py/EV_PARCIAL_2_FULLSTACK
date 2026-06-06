package com.urbano.promocion.controller;
import com.urbano.promocion.dto.*;
import com.urbano.promocion.model.*;
import com.urbano.promocion.service.PromocionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/promociones")
public class PromocionController {
    @Autowired private PromocionService service;
    @GetMapping public ResponseEntity<List<Promocion>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @PostMapping public ResponseEntity<Promocion> create(@Valid @RequestBody PromocionDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PostMapping("/cupones") public ResponseEntity<Cupon> createCupon(@Valid @RequestBody CuponDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.createCupon(dto)); }
    @PostMapping("/cupones/validar") public ResponseEntity<Cupon> validar(@RequestParam String codigo) { return ResponseEntity.ok(service.validarCupon(codigo)); }
}