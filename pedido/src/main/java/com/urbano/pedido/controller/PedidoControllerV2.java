package com.urbano.pedido.controller;

import com.urbano.pedido.dto.PedidoDTO;
import com.urbano.pedido.model.Pedido;
import com.urbano.pedido.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/pedidos")
@Tag(name = "Pedidos V2 (HATEOAS)", description = "Gestión de pedidos con navegación hipermedia")
public class PedidoControllerV2 {

    @Autowired private PedidoService service;

    private EntityModel<Pedido> toModel(Pedido p) {
        return EntityModel.of(p,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PedidoControllerV2.class).getById(p.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PedidoControllerV2.class).getAll()).withRel("pedidos"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PedidoControllerV2.class).getByCliente(p.getClienteId())).withRel("pedidos-cliente"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PedidoControllerV2.class).cambiarEstado(p.getId(), "PAGADO")).withRel("pagar"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PedidoControllerV2.class).cambiarEstado(p.getId(), "CANCELADO")).withRel("cancelar")
        );
    }

    @GetMapping
    @Operation(summary = "Listar pedidos con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getAll() {
        List<EntityModel<Pedido>> list = service.getAll().stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PedidoControllerV2.class).getAll()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener pedido por ID con HATEOAS")
    public ResponseEntity<EntityModel<Pedido>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toModel(service.getById(id)));
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Pedidos de un cliente con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> getByCliente(@PathVariable Long clienteId) {
        List<EntityModel<Pedido>> list = service.getByCliente(clienteId).stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PedidoControllerV2.class).getByCliente(clienteId)).withSelfRel()));
    }

    @PostMapping
    @Operation(summary = "Crear pedido con HATEOAS")
    public ResponseEntity<EntityModel<Pedido>> create(@Valid @RequestBody PedidoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(service.create(dto)));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de pedido")
    public ResponseEntity<EntityModel<Pedido>> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(toModel(service.cambiarEstado(id, estado)));
    }
}
