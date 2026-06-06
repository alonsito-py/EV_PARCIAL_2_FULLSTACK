package com.urbano.pedido.controller;
import com.urbano.pedido.dto.PedidoDTO;
import com.urbano.pedido.model.Pedido;
import com.urbano.pedido.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/pedidos")
public class PedidoController {
    @Autowired private PedidoService service;
    @GetMapping public ResponseEntity<List<Pedido>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/{id}") public ResponseEntity<Pedido> getById(@PathVariable Long id) { return ResponseEntity.ok(service.getById(id)); }
    @GetMapping("/cliente/{clienteId}") public ResponseEntity<List<Pedido>> getByCliente(@PathVariable Long clienteId) { return ResponseEntity.ok(service.getByCliente(clienteId)); }
    @PostMapping public ResponseEntity<Pedido> create(@Valid @RequestBody PedidoDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto)); }
    @PatchMapping("/{id}/estado") public ResponseEntity<Pedido> cambiarEstado(@PathVariable Long id, @RequestParam String estado) { return ResponseEntity.ok(service.cambiarEstado(id, estado)); }
}
