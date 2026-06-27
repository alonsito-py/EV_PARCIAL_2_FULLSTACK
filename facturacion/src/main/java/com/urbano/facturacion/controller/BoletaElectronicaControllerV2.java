package com.urbano.facturacion.controller;

import com.urbano.facturacion.model.BoletaElectronica;
import com.urbano.facturacion.service.FacturacionService;
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
@RequestMapping("/api/v2/facturacion")
@Tag(name = "Facturación V2 (HATEOAS)", description = "Boletas electrónicas con navegación hipermedia")
public class BoletaElectronicaControllerV2 {

    @Autowired private FacturacionService service;

    private EntityModel<BoletaElectronica> toModel(BoletaElectronica b) {
        return EntityModel.of(b,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BoletaElectronicaControllerV2.class).getByPedido(b.getPedidoId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BoletaElectronicaControllerV2.class).getAll()).withRel("facturacion")
        );
    }

    @GetMapping
    @Operation(summary = "Listar boletas con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<BoletaElectronica>>> getAll() {
        List<EntityModel<BoletaElectronica>> list = service.getAll().stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BoletaElectronicaControllerV2.class).getAll()).withSelfRel()));
    }

    @GetMapping("/pedido/{pedidoId}")
    @Operation(summary = "Obtener boletas por pedido con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<BoletaElectronica>>> getByPedido(@PathVariable Long pedidoId) {
        List<EntityModel<BoletaElectronica>> list = service.getByPedido(pedidoId).stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(BoletaElectronicaControllerV2.class).getByPedido(pedidoId)).withSelfRel()));
    }
}
