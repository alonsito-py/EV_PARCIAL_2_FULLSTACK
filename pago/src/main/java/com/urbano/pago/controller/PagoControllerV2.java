package com.urbano.pago.controller;

import com.urbano.pago.model.TransaccionPago;
import com.urbano.pago.service.PagoService;
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
@RequestMapping("/api/v2/pagos")
@Tag(name = "Pagos V2 (HATEOAS)", description = "Transacciones de pago con navegación hipermedia")
public class PagoControllerV2 {

    @Autowired private PagoService service;

    private EntityModel<TransaccionPago> toModel(TransaccionPago t) {
        return EntityModel.of(t,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PagoControllerV2.class).getByPedido(t.getPedidoId())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PagoControllerV2.class).getByPedido(t.getPedidoId())).withRel("pagos-pedido")
        );
    }

    @GetMapping("/pedido/{pedidoId}")
    @Operation(summary = "Pagos de un pedido con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<TransaccionPago>>> getByPedido(@PathVariable Long pedidoId) {
        List<EntityModel<TransaccionPago>> list = service.getByPedido(pedidoId).stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PagoControllerV2.class).getByPedido(pedidoId)).withSelfRel()));
    }
}
