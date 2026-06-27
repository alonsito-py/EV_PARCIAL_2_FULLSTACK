package com.urbano.inventario.controller;

import com.urbano.inventario.model.Inventario;
import com.urbano.inventario.service.InventarioService;
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
@RequestMapping("/api/v2/inventario")
@Tag(name = "Inventario V2 (HATEOAS)", description = "Control de stock con navegación hipermedia")
public class InventarioControllerV2 {

    @Autowired private InventarioService service;

    private EntityModel<Inventario> toModel(Inventario i) {
        return EntityModel.of(i,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventarioControllerV2.class).getBySku(i.getSku())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventarioControllerV2.class).getAll()).withRel("inventario"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventarioControllerV2.class).reducir(i.getSku(), 1)).withRel("reducir"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventarioControllerV2.class).aumentar(i.getSku(), 1)).withRel("aumentar")
        );
    }

    @GetMapping
    @Operation(summary = "Listar inventario con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<Inventario>>> getAll() {
        List<EntityModel<Inventario>> list = service.getAll().stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(InventarioControllerV2.class).getAll()).withSelfRel()));
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Obtener stock por SKU con HATEOAS")
    public ResponseEntity<EntityModel<Inventario>> getBySku(@PathVariable String sku) {
        return ResponseEntity.ok(toModel(service.getBySku(sku)));
    }

    @PatchMapping("/sku/{sku}/reducir")
    @Operation(summary = "Reducir stock con HATEOAS")
    public ResponseEntity<EntityModel<Inventario>> reducir(@PathVariable String sku, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(toModel(service.reducirStock(sku, cantidad)));
    }

    @PatchMapping("/sku/{sku}/aumentar")
    @Operation(summary = "Aumentar stock con HATEOAS")
    public ResponseEntity<EntityModel<Inventario>> aumentar(@PathVariable String sku, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(toModel(service.aumentarStock(sku, cantidad)));
    }
}
