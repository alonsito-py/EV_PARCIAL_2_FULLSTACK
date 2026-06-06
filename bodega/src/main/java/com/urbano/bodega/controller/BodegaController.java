package com.urbano.bodega.controller;
import com.urbano.bodega.dto.MovimientoDTO;
import com.urbano.bodega.model.MovimientoInventario;
import com.urbano.bodega.service.BodegaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/bodega")
public class BodegaController {
    @Autowired private BodegaService service;
    @GetMapping public ResponseEntity<List<MovimientoInventario>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/sku/{sku}") public ResponseEntity<List<MovimientoInventario>> getBySku(@PathVariable String sku) { return ResponseEntity.ok(service.getBySku(sku)); }
    @PostMapping public ResponseEntity<MovimientoInventario> registrar(@Valid @RequestBody MovimientoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(dto)); }
}
