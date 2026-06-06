package com.urbano.marketing.controller;
import com.urbano.marketing.dto.CampaniaDTO;
import com.urbano.marketing.model.CampaniaSocial;
import com.urbano.marketing.service.MarketingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/marketing")
public class MarketingController {
    @Autowired private MarketingService service;
    @GetMapping public ResponseEntity<List<CampaniaSocial>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/{id}") public ResponseEntity<CampaniaSocial> getById(@PathVariable Long id) { return ResponseEntity.ok(service.getById(id)); }
    @PostMapping public ResponseEntity<CampaniaSocial> crear(@Valid @RequestBody CampaniaDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(dto)); }
    @PatchMapping("/{id}/publicar") public ResponseEntity<CampaniaSocial> publicar(@PathVariable Long id) { return ResponseEntity.ok(service.publicar(id)); }
}