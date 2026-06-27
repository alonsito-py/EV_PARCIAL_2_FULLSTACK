package com.urbano.marketing.controller;

import com.urbano.marketing.model.CampaniaSocial;
import com.urbano.marketing.service.MarketingService;
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
@RequestMapping("/api/v2/marketing")
@Tag(name = "Marketing V2 (HATEOAS)", description = "Campañas sociales con navegación hipermedia")
public class MarketingControllerV2 {

    @Autowired private MarketingService service;

    private EntityModel<CampaniaSocial> toModel(CampaniaSocial c) {
        return EntityModel.of(c,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MarketingControllerV2.class).getById(c.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MarketingControllerV2.class).getAll()).withRel("campanias"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MarketingControllerV2.class).publicar(c.getId())).withRel("publicar")
        );
    }

    @GetMapping
    @Operation(summary = "Listar campañas con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<CampaniaSocial>>> getAll() {
        List<EntityModel<CampaniaSocial>> list = service.getAll().stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MarketingControllerV2.class).getAll()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener campaña por ID con HATEOAS")
    public ResponseEntity<EntityModel<CampaniaSocial>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toModel(service.getById(id)));
    }

    @PatchMapping("/{id}/publicar")
    @Operation(summary = "Publicar campaña")
    public ResponseEntity<EntityModel<CampaniaSocial>> publicar(@PathVariable Long id) {
        return ResponseEntity.ok(toModel(service.publicar(id)));
    }
}
