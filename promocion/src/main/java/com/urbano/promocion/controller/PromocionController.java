package com.urbano.promocion.controller;
import com.urbano.promocion.dto.*;
import com.urbano.promocion.model.*;
import com.urbano.promocion.service.PromocionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Promociones", description = "Gestión de promociones y cupones")
@RestController @RequestMapping("/api/v1/promociones")
public class PromocionController {
    @Autowired private PromocionService service;
    @GetMapping
    @Operation(summary = "Operación GET")
    public ResponseEntity<List<Promocion>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @PostMapping
    @Operation(summary = "Operación POST")
    public ResponseEntity<Promocion> create(@Valid @RequestBody PromocionDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PostMapping("/cupones")
    @Operation(summary = "Operación POST")
    public ResponseEntity<Cupon> createCupon(@Valid @RequestBody CuponDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.createCupon(dto)); }
    @PostMapping("/cupones/validar")
    @Operation(summary = "Operación POST")
    public ResponseEntity<Cupon> validar(@RequestParam String codigo) { return ResponseEntity.ok(service.validarCupon(codigo)); }
}