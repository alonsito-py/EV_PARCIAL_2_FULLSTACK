package com.urbano.reporte.controller;

import com.urbano.reporte.model.CierreVentaDiario;
import com.urbano.reporte.service.ReporteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v2/reportes")
@Tag(name = "Reportes V2 (HATEOAS)", description = "Reportes de venta con navegación hipermedia")
public class CierreVentaDiarioControllerV2 {

    @Autowired private ReporteService service;

    private EntityModel<CierreVentaDiario> toModel(CierreVentaDiario c) {
        return EntityModel.of(c,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CierreVentaDiarioControllerV2.class).getAll()).withRel("reportes")
        );
    }

    @GetMapping
    @Operation(summary = "Listar reportes con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<CierreVentaDiario>>> getAll() {
        List<EntityModel<CierreVentaDiario>> list = service.getAll().stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CierreVentaDiarioControllerV2.class).getAll()).withSelfRel()));
    }

    @GetMapping("/periodo")
    @Operation(summary = "Reportes por periodo con HATEOAS")
    public ResponseEntity<CollectionModel<EntityModel<CierreVentaDiario>>> getPeriodo(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        List<EntityModel<CierreVentaDiario>> list = service.getPorPeriodo(inicio, fin).stream().map(this::toModel).collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(list,
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CierreVentaDiarioControllerV2.class).getAll()).withRel("todos")));
    }
}
