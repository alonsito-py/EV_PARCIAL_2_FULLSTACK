package com.urbano.reporte.service;
import com.urbano.reporte.dto.CierreDTO;
import com.urbano.reporte.model.CierreVentaDiario;
import com.urbano.reporte.repository.ReporteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
@Service @Transactional @Slf4j
public class ReporteService {
    @Autowired private ReporteRepository repo;
    public List<CierreVentaDiario> getAll() { return repo.findAll(); }
    public List<CierreVentaDiario> getPorPeriodo(LocalDate inicio, LocalDate fin) { log.info("Reporte periodo {} - {}", inicio, fin); return repo.findByFechaBetween(inicio, fin); }
    public CierreVentaDiario registrarCierre(CierreDTO dto) {
        log.info("Registrando cierre fecha: {}", dto.getFecha());
        CierreVentaDiario c=new CierreVentaDiario(); c.setFecha(dto.getFecha()); c.setTotalPedidos(dto.getTotalPedidos());
        c.setMontoTotal(dto.getMontoTotal()); c.setObservaciones(dto.getObservaciones()); return repo.save(c);
    }
}
