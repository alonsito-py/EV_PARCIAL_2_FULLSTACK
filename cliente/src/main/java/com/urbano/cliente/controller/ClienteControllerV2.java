package com.urbano.cliente.controller;

import com.urbano.cliente.dto.ClienteDTO;
import com.urbano.cliente.model.Cliente;
import com.urbano.cliente.service.ClienteService;
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
@RequestMapping("/api/v2/clientes")
@Tag(name = "Clientes V2 (HATEOAS)", description = "Gestión de clientes con navegación hipermedia")
public class ClienteControllerV2 {

    @Autowired private ClienteService service;

    private EntityModel<Cliente> toModel(Cliente c) {
        return EntityModel.of(c,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControllerV2.class).getById(c.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControllerV2.class).getAll()).withRel("clientes"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControllerV2.class).verificar(c.getId())).withRel("verificar"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControllerV2.class).delete(c.getId())).withRel("eliminar")
        );
    }

    @GetMapping
    @Operation(summary = "Listar clientes con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> getAll() {
        List<EntityModel<Cliente>> list = service.getAll().stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ClienteControllerV2.class).getAll()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID con HATEOAS")
    public ResponseEntity<EntityModel<Cliente>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toModel(service.getById(id)));
    }

    @PostMapping
    @Operation(summary = "Crear cliente con HATEOAS")
    public ResponseEntity<EntityModel<Cliente>> create(@Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(service.create(dto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente con HATEOAS")
    public ResponseEntity<EntityModel<Cliente>> update(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(toModel(service.update(id, dto)));
    }

    @PatchMapping("/{id}/verificar")
    @Operation(summary = "Verificar cliente")
    public ResponseEntity<Void> verificar(@PathVariable Long id) {
        service.verificar(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar cliente")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
