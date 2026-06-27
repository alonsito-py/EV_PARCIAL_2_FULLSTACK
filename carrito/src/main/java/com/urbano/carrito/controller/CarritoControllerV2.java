package com.urbano.carrito.controller;

import com.urbano.carrito.model.Carrito;
import com.urbano.carrito.service.CarritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/carrito")
@Tag(name = "Carrito V2 (HATEOAS)", description = "Carrito de compras con navegación hipermedia")
public class CarritoControllerV2 {

    @Autowired private CarritoService service;

    private EntityModel<Carrito> toModel(Carrito c) {
        return EntityModel.of(c,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CarritoControllerV2.class).getCarrito(c.getClienteId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CarritoControllerV2.class).vaciar(c.getId())).withRel("vaciar"),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CarritoControllerV2.class).procesar(c.getId())).withRel("procesar")
        );
    }

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Obtener carrito del cliente con HATEOAS")
    public ResponseEntity<EntityModel<Carrito>> getCarrito(@PathVariable Long clienteId) {
        return ResponseEntity.ok(toModel(service.getOrCreate(clienteId)));
    }

    @DeleteMapping("/{carritoId}/vaciar")
    @Operation(summary = "Vaciar carrito")
    public ResponseEntity<Void> vaciar(@PathVariable Long carritoId) {
        service.vaciar(carritoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{carritoId}/procesar")
    @Operation(summary = "Procesar carrito")
    public ResponseEntity<Void> procesar(@PathVariable Long carritoId) {
        service.procesar(carritoId);
        return ResponseEntity.ok().build();
    }
}
