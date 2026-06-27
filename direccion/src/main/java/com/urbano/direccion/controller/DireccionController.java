package com.urbano.direccion.controller;
import com.urbano.direccion.dto.DireccionDTO;
import com.urbano.direccion.model.Direccion;
import com.urbano.direccion.service.DireccionService;
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
@Tag(name = "Direcciones", description = "Gestión de direcciones de clientes")
@RestController @RequestMapping("/api/v1/direcciones")
public class DireccionController {
    @Autowired private DireccionService service;
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Operación GET")
    public ResponseEntity<List<Direccion>> getByCliente(@PathVariable Long clienteId) { return ResponseEntity.ok(service.getByCliente(clienteId)); }
    @GetMapping("/{id}")
    @Operation(summary = "Operación GET")
    public ResponseEntity<Direccion> getById(@PathVariable Long id) { return ResponseEntity.ok(service.getById(id)); }
    @PostMapping
    @Operation(summary = "Operación POST")
    public ResponseEntity<Direccion> create(@Valid @RequestBody DireccionDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @DeleteMapping("/{id}")
    @Operation(summary = "Operación DELETE")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}