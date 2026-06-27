package com.urbano.inventario.service;
import com.urbano.inventario.dto.InventarioDTO;
import com.urbano.inventario.exception.InventarioNotFoundException;
import com.urbano.inventario.model.Inventario;
import com.urbano.inventario.repository.InventarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @Transactional @Slf4j
public class InventarioService {
    @Autowired private InventarioRepository repo;
    public List<Inventario> getAll() { return repo.findAll(); }
    public Inventario getBySku(String sku) { return repo.findBySku(sku).orElseThrow(()->new InventarioNotFoundException("Inventario no encontrado sku: "+sku)); }
    public Inventario create(InventarioDTO dto) {
        log.info("Creando inventario sku: {}", dto.getSku());
        Inventario i=new Inventario(); i.setSku(dto.getSku()); i.setCantidadDisponible(dto.getCantidadDisponible()); i.setAlertaMinimo(dto.getAlertaMinimo());
        return repo.save(i);
    }
    public Inventario reducirStock(String sku, Integer cantidad) {
        log.info("Reduciendo stock sku:{} cantidad:{}", sku, cantidad);
        Inventario i=getBySku(sku);
        if (i.getCantidadDisponible()<cantidad) throw new RuntimeException("Stock insuficiente sku: "+sku);
        i.setCantidadDisponible(i.getCantidadDisponible()-cantidad);
        if (i.getCantidadDisponible()<=i.getAlertaMinimo()) log.warn("ALERTA STOCK MINIMO sku:{} cantidad:{}", sku, i.getCantidadDisponible());
        return repo.save(i);
    }
    public Inventario aumentarStock(String sku, Integer cantidad) {
        log.info("Aumentando stock sku:{} cantidad:{}", sku, cantidad);
        Inventario i=getBySku(sku); i.setCantidadDisponible(i.getCantidadDisponible()+cantidad); return repo.save(i);
    }
}