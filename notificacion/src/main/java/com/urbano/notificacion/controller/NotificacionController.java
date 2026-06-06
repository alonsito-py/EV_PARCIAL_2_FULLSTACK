package com.urbano.notificacion.controller;
import com.urbano.notificacion.dto.NotificacionDTO;
import com.urbano.notificacion.model.HistorialNotificacion;
import com.urbano.notificacion.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/v1/notificaciones")
public class NotificacionController {
    @Autowired private NotificacionService service;
    @GetMapping public ResponseEntity<List<HistorialNotificacion>> getAll() { return ResponseEntity.ok(service.getAll()); }
    @GetMapping("/destinatario/{dest}") public ResponseEntity<List<HistorialNotificacion>> getByDest(@PathVariable String dest) { return ResponseEntity.ok(service.getByDestinatario(dest)); }
    @PostMapping public ResponseEntity<HistorialNotificacion> enviar(@Valid @RequestBody NotificacionDTO dto) { return ResponseEntity.status(HttpStatus.CREATED).body(service.enviar(dto)); }
}