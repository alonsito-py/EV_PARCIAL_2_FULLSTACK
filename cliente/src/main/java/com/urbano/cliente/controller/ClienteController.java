package com.urbano.cliente.controller;
import com.urbano.cliente.dto.ClienteDTO;
import com.urbano.cliente.model.Cliente;
import com.urbano.cliente.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/clientes")
public class ClienteController {
    @Autowired private ClienteService service;
    @GetMapping public ResponseEntity<List<Cliente>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/{id}") public ResponseEntity<Cliente> getById(@PathVariable Long id) { return ResponseEntity.ok(service.getById(id)); }
    @PostMapping public ResponseEntity<Cliente> create(@Valid @RequestBody ClienteDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PutMapping("/{id}") public ResponseEntity<Cliente> update(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) { return ResponseEntity.ok(service.update(id, dto)); }
    @PatchMapping("/{id}/verificar") public ResponseEntity<Void> verificar(@PathVariable Long id) { service.verificar(id); return ResponseEntity.ok().build(); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
