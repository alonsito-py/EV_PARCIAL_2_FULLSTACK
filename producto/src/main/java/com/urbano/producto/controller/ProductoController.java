package com.urbano.producto.controller;
import com.urbano.producto.dto.ProductoDTO;
import com.urbano.producto.model.Producto;
import com.urbano.producto.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/productos")
public class ProductoController {
    @Autowired private ProductoService service;
    @GetMapping public ResponseEntity<List<Producto>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/{id}") public ResponseEntity<Producto> getById(@PathVariable Long id) { return ResponseEntity.ok(service.getById(id)); }
    @GetMapping("/sku/{sku}") public ResponseEntity<Producto> getBySku(@PathVariable String sku) { return ResponseEntity.ok(service.getBySku(sku)); }
    @GetMapping("/categoria/{catId}") public ResponseEntity<List<Producto>> getByCategoria(@PathVariable Long catId) { return ResponseEntity.ok(service.getByCategoria(catId)); }
    @PostMapping public ResponseEntity<Producto> create(@Valid @RequestBody ProductoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PutMapping("/{id}") public ResponseEntity<Producto> update(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) { return ResponseEntity.ok(service.update(id, dto)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}