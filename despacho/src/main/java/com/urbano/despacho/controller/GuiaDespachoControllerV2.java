package com.urbano.despacho.controller;

import com.urbano.despacho.model.GuiaDespacho;
import com.urbano.despacho.service.DespachoService;
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
@RequestMapping("/api/v2/despacho")
@Tag(name = "Despacho V2 (HATEOAS)", description = "Guías de despacho con navegación hipermedia")
public class GuiaDespachoControllerV2 {

    @Autowired private DespachoService service;

    private EntityModel<GuiaDespacho> toModel(GuiaDespacho g) {
        return EntityModel.of(g,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GuiaDespachoControllerV2.class).getById(g.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GuiaDespachoControllerV2.class).getAll()).withRel("despachos"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GuiaDespachoControllerV2.class).cambiarEstado(g.getId(), "EN_CAMINO")).withRel("en-camino"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GuiaDespachoControllerV2.class).cambiarEstado(g.getId(), "ENTREGADO")).withRel("entregado")
        );
    }

    @GetMapping
    @Operation(summary = "Listar guías de despacho con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<GuiaDespacho>>> getAll() {
        List<EntityModel<GuiaDespacho>> list = service.getAll().stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GuiaDespachoControllerV2.class).getAll()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener guía por ID con HATEOAS")
    public ResponseEntity<EntityModel<GuiaDespacho>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toModel(service.getById(id)));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de despacho con HATEOAS")
    public ResponseEntity<EntityModel<GuiaDespacho>> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(toModel(service.cambiarEstado(id, estado)));
    }
}
