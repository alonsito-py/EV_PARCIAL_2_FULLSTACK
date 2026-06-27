package com.urbano.bodega.controller;
import com.urbano.bodega.dto.MovimientoDTO;
import com.urbano.bodega.model.MovimientoInventario;
import com.urbano.bodega.service.BodegaService;
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
@Tag(name = "Bodega", description = "Movimientos de inventario en bodega")
@RestController @RequestMapping("/api/v1/bodega")
public class BodegaController {
    @Autowired private BodegaService service;
    @GetMapping
    @Operation(summary = "Operación GET")
    public ResponseEntity<List<MovimientoInventario>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/sku/{sku}")
    @Operation(summary = "Operación GET")
    public ResponseEntity<List<MovimientoInventario>> getBySku(@PathVariable String sku) { return ResponseEntity.ok(service.getBySku(sku)); }
    @PostMapping
    @Operation(summary = "Operación POST")
    public ResponseEntity<MovimientoInventario> registrar(@Valid @RequestBody MovimientoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(dto)); }
}
