package com.urbano.promocion.controller;

import com.urbano.promocion.model.Promocion;
import com.urbano.promocion.service.PromocionService;
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
@RequestMapping("/api/v2/promociones")
@Tag(name = "Promociones V2 (HATEOAS)", description = "Promociones y cupones con navegación hipermedia")
public class PromocionControllerV2 {

    @Autowired private PromocionService service;

    private EntityModel<Promocion> toModel(Promocion p) {
        return EntityModel.of(p,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PromocionControllerV2.class).getAll()).withRel("promociones")
        );
    }

    @GetMapping
    @Operation(summary = "Listar promociones con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<Promocion>>> getAll() {
        List<EntityModel<Promocion>> list = service.getAll().stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PromocionControllerV2.class).getAll()).withSelfRel()));
    }
}
