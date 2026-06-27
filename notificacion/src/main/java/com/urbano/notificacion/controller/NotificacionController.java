package com.urbano.notificacion.controller;
import com.urbano.notificacion.dto.NotificacionDTO;
import com.urbano.notificacion.model.HistorialNotificacion;
import com.urbano.notificacion.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "Notificaciones", description = "Envío y registro de notificaciones")
@RestController @RequestMapping("/api/v1/notificaciones")
public class NotificacionController {
    @Autowired private NotificacionService service;
    @GetMapping
    @Operation(summary = "Operación GET")
    public ResponseEntity<List<HistorialNotificacion>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/destinatario/{dest}")
    @Operation(summary = "Operación GET")
    public ResponseEntity<List<HistorialNotificacion>> getByDest(@PathVariable String dest) { return ResponseEntity.ok(service.getByDestinatario(dest)); }
    @PostMapping
    @Operation(summary = "Operación POST")
    public ResponseEntity<HistorialNotificacion> enviar(@Valid @RequestBody NotificacionDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.enviar(dto)); }
}