package com.urbano.autenticacion.controller;
import com.urbano.autenticacion.dto.UsuarioDTO;
import com.urbano.autenticacion.model.Usuario;
import com.urbano.autenticacion.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/usuarios") @Slf4j
public class UsuarioController {
    @Autowired private UsuarioService service;
    @GetMapping public ResponseEntity<List<Usuario>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/{id}") public ResponseEntity<Usuario> getById(@PathVariable Long id) { return ResponseEntity.ok(service.getById(id)); }
    @PostMapping public ResponseEntity<Usuario> create(@Valid @RequestBody UsuarioDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PostMapping("/login") public ResponseEntity<Usuario> login(@RequestParam String username, @RequestParam String password) { return ResponseEntity.ok(service.login(username, password)); }
    @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
