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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Usuarios", description = "Gestión de usuarios y roles")
@RestController @RequestMapping("/api/v1/usuarios") @Slf4j
public class UsuarioController {
    @Autowired private UsuarioService service;
    @GetMapping
    @Operation(summary = "Operación GET")
    public ResponseEntity<List<Usuario>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/{id}")
    @Operation(summary = "Operación GET")
    public ResponseEntity<Usuario> getById(@PathVariable Long id) { return ResponseEntity.ok(service.getById(id)); }
    @PostMapping
    @Operation(summary = "Operación POST")
    public ResponseEntity<Usuario> create(@Valid @RequestBody UsuarioDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PostMapping("/login")
    @Operation(summary = "Operación POST")
    public ResponseEntity<Usuario> login(@RequestParam String username, @RequestParam String password) { return ResponseEntity.ok(service.login(username, password)); }
    @DeleteMapping("/{id}")
    @Operation(summary = "Operación DELETE")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
