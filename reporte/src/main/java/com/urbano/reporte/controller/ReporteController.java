package com.urbano.reporte.controller;
import com.urbano.reporte.dto.CierreDTO;
import com.urbano.reporte.model.CierreVentaDiario;
import com.urbano.reporte.service.ReporteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
@RestController @RequestMapping("/api/v1/reportes")
public class ReporteController {
    @Autowired private ReporteService service;
    @GetMapping public ResponseEntity<List<CierreVentaDiario>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/periodo") public ResponseEntity<List<CierreVentaDiario>> getPeriodo(
        @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate inicio,
        @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate fin) { return ResponseEntity.ok(service.getPorPeriodo(inicio, fin)); }
    @PostMapping public ResponseEntity<CierreVentaDiario> registrar(@Valid @RequestBody CierreDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarCierre(dto)); }
}
