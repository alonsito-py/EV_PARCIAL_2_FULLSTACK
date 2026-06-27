package com.urbano.pedido.controller;

import com.urbano.pedido.dto.PedidoDTO;
import com.urbano.pedido.model.Pedido;
import com.urbano.pedido.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Gestión del ciclo de vida de pedidos Urbano")
public class PedidoController {

    @Autowired private PedidoService service;

    @GetMapping
    @Operation(summary = "Listar todos los pedidos")
    public ResponseEntity<List<Pedido>> getAll() { return ResponseEntity.ok(service.getAll()); }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    public ResponseEntity<Pedido> getById(@Parameter(description = "ID del pedido") @PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Obtener pedidos de un cliente")
    public ResponseEntity<List<Pedido>> getByCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(service.getByCliente(clienteId));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Pedido creado"),
        @ApiResponse(responseCode = "400", description = "Cliente inválido o datos incorrectos")
    })
    public ResponseEntity<Pedido> create(@Valid @RequestBody PedidoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de un pedido", description = "Estados válidos: PENDIENTE, PAGADO, ENVIADO, ENTREGADO, CANCELADO")
    public ResponseEntity<Pedido> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(service.cambiarEstado(id, estado));
    }
}
