package com.urbano.despacho.controller;
import com.urbano.despacho.dto.GuiaDespachoDTO;
import com.urbano.despacho.model.GuiaDespacho;
import com.urbano.despacho.service.DespachoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/despacho")
public class DespachoController {
    @Autowired private DespachoService service;
    @GetMapping public ResponseEntity<List<GuiaDespacho>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/{id}") public ResponseEntity<GuiaDespacho> getById(@PathVariable Long id) { return ResponseEntity.ok(service.getById(id)); }
    @PostMapping public ResponseEntity<GuiaDespacho> crear(@Valid @RequestBody GuiaDespachoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto)); }
    @PatchMapping("/{id}/estado") public ResponseEntity<GuiaDespacho> cambiarEstado(@PathVariable Long id, @RequestParam String estado) { return ResponseEntity.ok(service.cambiarEstado(id, estado)); }
}