package com.urbano.notificacion.controller;

import com.urbano.notificacion.model.HistorialNotificacion;
import com.urbano.notificacion.service.NotificacionService;
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
@RequestMapping("/api/v2/notificaciones")
@Tag(name = "Notificaciones V2 (HATEOAS)", description = "Historial de notificaciones con navegación hipermedia")
public class HistorialNotificacionControllerV2 {

    @Autowired private NotificacionService service;

    private EntityModel<HistorialNotificacion> toModel(HistorialNotificacion n) {
        return EntityModel.of(n,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HistorialNotificacionControllerV2.class).getByDestinatario(n.getDestinatario())).withSelfRel(),
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HistorialNotificacionControllerV2.class).getAll()).withRel("notificaciones")
        );
    }

    @GetMapping
    @Operation(summary = "Listar notificaciones con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<HistorialNotificacion>>> getAll() {
        List<EntityModel<HistorialNotificacion>> list = service.getAll().stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HistorialNotificacionControllerV2.class).getAll()).withSelfRel()));
    }

    @GetMapping("/destinatario/{dest}")
    @Operation(summary = "Notificaciones por destinatario con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<HistorialNotificacion>>> getByDestinatario(@PathVariable String dest) {
        List<EntityModel<HistorialNotificacion>> list = service.getByDestinatario(dest).stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HistorialNotificacionControllerV2.class).getByDestinatario(dest)).withSelfRel()));
    }
}
