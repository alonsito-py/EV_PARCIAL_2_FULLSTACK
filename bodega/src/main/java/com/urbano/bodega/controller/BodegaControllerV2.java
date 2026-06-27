package com.urbano.bodega.controller;

import com.urbano.bodega.model.MovimientoInventario;
import com.urbano.bodega.service.BodegaService;
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
@RequestMapping("/api/v2/bodega")
@Tag(name = "Bodega V2 (HATEOAS)", description = "Movimientos de bodega con navegación hipermedia")
public class BodegaControllerV2 {

    @Autowired private BodegaService service;

    private EntityModel<MovimientoInventario> toModel(MovimientoInventario m) {
        return EntityModel.of(m,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BodegaControllerV2.class).getBySku(m.getSku())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BodegaControllerV2.class).getAll()).withRel("bodega")
        );
    }

    @GetMapping
    @Operation(summary = "Listar movimientos de bodega con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<MovimientoInventario>>> getAll() {
        List<EntityModel<MovimientoInventario>> list = service.getAll().stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BodegaControllerV2.class).getAll()).withSelfRel()));
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Movimientos por SKU con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<MovimientoInventario>>> getBySku(@PathVariable String sku) {
        List<EntityModel<MovimientoInventario>> list = service.getBySku(sku).stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BodegaControllerV2.class).getBySku(sku)).withSelfRel()));
    }
}
