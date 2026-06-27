package com.urbano.direccion.controller;

import com.urbano.direccion.model.Direccion;
import com.urbano.direccion.service.DireccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/direcciones")
@Tag(name = "Direcciones V2 (HATEOAS)", description = "Direcciones de entrega con navegación hipermedia")
public class DireccionControllerV2 {

    @Autowired private DireccionService service;

    private EntityModel<Direccion> toModel(Direccion d) {
        return EntityModel.of(d,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DireccionControllerV2.class).getById(d.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DireccionControllerV2.class).getByCliente(d.getClienteId())).withRel("direcciones-cliente"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DireccionControllerV2.class).delete(d.getId())).withRel("eliminar")
        );
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Direcciones de un cliente con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<Direccion>>> getByCliente(@PathVariable Long clienteId) {
        List<EntityModel<Direccion>> list = service.getByCliente(clienteId).stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DireccionControllerV2.class).getByCliente(clienteId)).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener dirección por ID con HATEOAS")
    public ResponseEntity<EntityModel<Direccion>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toModel(service.getById(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar dirección")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
