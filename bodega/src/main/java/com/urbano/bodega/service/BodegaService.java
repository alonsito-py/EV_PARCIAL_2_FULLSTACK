package com.urbano.bodega.service;
import com.urbano.bodega.dto.MovimientoDTO;
import com.urbano.bodega.model.MovimientoInventario;
import com.urbano.bodega.repository.MovimientoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
@Service @Transactional @Slf4j
public class BodegaService {
    @Autowired private MovimientoRepository repo;
    @Autowired private WebClient webClientInventario;
    public List<MovimientoInventario> getAll() { return repo.findAll(); }
    public List<MovimientoInventario> getBySku(String sku) { return repo.findBySku(sku); }
    public MovimientoInventario registrar(MovimientoDTO dto) {
        log.info("Registrando movimiento {} sku:{} cant:{}", dto.getTipo(), dto.getSku(), dto.getCantidad());
        MovimientoInventario m=new MovimientoInventario(); m.setSku(dto.getSku()); m.setTipo(dto.getTipo()); m.setCantidad(dto.getCantidad()); m.setMotivo(dto.getMotivo());
        MovimientoInventario saved=repo.save(m);
        // Actualizar inventario en MS5
        String endpoint = dto.getTipo().equals("ENTRADA") ? "/api/v1/inventario/sku/"+dto.getSku()+"/aumentar?cantidad="+dto.getCantidad()
                                                           : "/api/v1/inventario/sku/"+dto.getSku()+"/reducir?cantidad="+dto.getCantidad();
        try { webClientInventario.patch().uri(endpoint).retrieve().bodyToMono(Object.class).block();
              log.info("Inventario actualizado para sku: {}", dto.getSku());
        } catch(Exception e) { log.error("Error actualizando inventario: {}", e.getMessage()); }
        return saved;
    }
}
