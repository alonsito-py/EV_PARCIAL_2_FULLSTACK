package com.urbano.producto.controller;

import com.urbano.producto.dto.ProductoDTO;
import com.urbano.producto.model.Producto;
import com.urbano.producto.service.ProductoService;
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
@RequestMapping("/api/v2/productos")
@Tag(name = "Productos V2 (HATEOAS)", description = "Catálogo de productos con navegación hipermedia")
public class ProductoControllerV2 {

    @Autowired private ProductoService service;

    private EntityModel<Producto> toModel(Producto p) {
        return EntityModel.of(p,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoControllerV2.class).getById(p.getId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoControllerV2.class).getAll()).withRel("productos"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoControllerV2.class).delete(p.getId())).withRel("eliminar")
        );
    }

    @GetMapping
    @Operation(summary = "Listar productos activos con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getAll() {
        List<EntityModel<Producto>> list = service.getAll().stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductoControllerV2.class).getAll()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID con HATEOAS")
    public ResponseEntity<EntityModel<Producto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toModel(service.getById(id)));
    }

    @GetMapping("/sku/{sku}")
    @Operation(summary = "Obtener producto por SKU con HATEOAS")
    public ResponseEntity<EntityModel<Producto>> getBySku(@PathVariable String sku) {
        return ResponseEntity.ok(toModel(service.getBySku(sku)));
    }

    @PostMapping
    @Operation(summary = "Crear producto con HATEOAS")
    public ResponseEntity<EntityModel<Producto>> create(@Valid @RequestBody ProductoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toModel(service.create(dto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto con HATEOAS")
    public ResponseEntity<EntityModel<Producto>> update(@PathVariable Long id, @Valid @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(toModel(service.update(id, dto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desactivar producto")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
