package com.urbano.direccion.controller;
import com.urbano.direccion.dto.DireccionDTO;
import com.urbano.direccion.model.Direccion;
import com.urbano.direccion.service.DireccionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/direcciones")
public class DireccionController {
    @Autowired private DireccionService service;
    @GetMapping("/cliente/{clienteId}") public ResponseEntity<List<Direccion>> getByCliente(@PathVariable Long clienteId) { return ResponseEntity.ok(service.getByCliente(clienteId)); }
    @GetMapping("/{id}") public ResponseEntity<Direccion> getById(@PathVariable Long id) { return ResponseEntity.ok(service.getById(id)); }
    @PostMapping public ResponseEntity<Direccion> create(@Valid @RequestBody DireccionDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
