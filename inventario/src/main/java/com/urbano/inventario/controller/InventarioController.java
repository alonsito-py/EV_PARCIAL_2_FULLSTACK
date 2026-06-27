package com.urbano.inventario.controller;
import com.urbano.inventario.dto.InventarioDTO;
import com.urbano.inventario.model.Inventario;
import com.urbano.inventario.service.InventarioService;
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
@Tag(name = "Inventario", description = "Control de stock por SKU")
@RestController @RequestMapping("/api/v1/inventario")
public class InventarioController {
    @Autowired private InventarioService service;
    @GetMapping
    @Operation(summary = "Operación GET")
    public ResponseEntity<List<Inventario>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/sku/{sku}")
    @Operation(summary = "Operación GET")
    public ResponseEntity<Inventario> getBySku(@PathVariable String sku) { return ResponseEntity.ok(service.getBySku(sku)); }
    @PostMapping
    @Operation(summary = "Operación POST")
    public ResponseEntity<Inventario> create(@Valid @RequestBody InventarioDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PatchMapping("/sku/{sku}/reducir")
    @Operation(summary = "Operación PATCH")
    public ResponseEntity<Inventario> reducir(@PathVariable String sku, @RequestParam Integer cantidad) { return ResponseEntity.ok(service.reducirStock(sku, cantidad)); }
    @PatchMapping("/sku/{sku}/aumentar")
    @Operation(summary = "Operación PATCH")
    public ResponseEntity<Inventario> aumentar(@PathVariable String sku, @RequestParam Integer cantidad) { return ResponseEntity.ok(service.aumentarStock(sku, cantidad)); }
}