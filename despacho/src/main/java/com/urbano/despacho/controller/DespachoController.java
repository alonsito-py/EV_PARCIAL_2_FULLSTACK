package com.urbano.despacho.controller;
import com.urbano.despacho.dto.GuiaDespachoDTO;
import com.urbano.despacho.model.GuiaDespacho;
import com.urbano.despacho.service.DespachoService;
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
@Tag(name = "Despacho", description = "Guías de despacho y seguimiento")
@RestController @RequestMapping("/api/v1/despacho")
public class DespachoController {
    @Autowired private DespachoService service;
    @GetMapping
    @Operation(summary = "Operación GET")
    public ResponseEntity<List<GuiaDespacho>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/{id}")
    @Operation(summary = "Operación GET")
    public ResponseEntity<GuiaDespacho> getById(@PathVariable Long id) { return ResponseEntity.ok(service.getById(id)); }
    @PostMapping
    @Operation(summary = "Operación POST")
    public ResponseEntity<GuiaDespacho> crear(@Valid @RequestBody GuiaDespachoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto)); }
    @PatchMapping("/{id}/estado")
    @Operation(summary = "Operación PATCH")
    public ResponseEntity<GuiaDespacho> cambiarEstado(@PathVariable Long id, @RequestParam String estado) { return ResponseEntity.ok(service.cambiarEstado(id, estado)); }
}