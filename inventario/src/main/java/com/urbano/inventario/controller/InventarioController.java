package com.urbano.inventario.controller;
import com.urbano.inventario.dto.InventarioDTO;
import com.urbano.inventario.model.Inventario;
import com.urbano.inventario.service.InventarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/inventario")
public class InventarioController {
    @Autowired private InventarioService service;
    @GetMapping public ResponseEntity<List<Inventario>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/sku/{sku}") public ResponseEntity<Inventario> getBySku(@PathVariable String sku) { return ResponseEntity.ok(service.getBySku(sku)); }
    @PostMapping public ResponseEntity<Inventario> create(@Valid @RequestBody InventarioDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PatchMapping("/sku/{sku}/reducir") public ResponseEntity<Inventario> reducir(@PathVariable String sku, @RequestParam Integer cantidad) { return ResponseEntity.ok(service.reducirStock(sku, cantidad)); }
    @PatchMapping("/sku/{sku}/aumentar") public ResponseEntity<Inventario> aumentar(@PathVariable String sku, @RequestParam Integer cantidad) { return ResponseEntity.ok(service.aumentarStock(sku, cantidad)); }
}
