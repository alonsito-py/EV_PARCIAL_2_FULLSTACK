package com.urbano.carrito.service;
import com.urbano.carrito.dto.ItemCarritoDTO;
import com.urbano.carrito.exception.CarritoNotFoundException;
import com.urbano.carrito.model.*;
import com.urbano.carrito.repository.CarritoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
@Service @Transactional @Slf4j
public class CarritoService {
    @Autowired private CarritoRepository repo;
    @Autowired private WebClient webClientInventario;
    private void verificarStock(String sku) {
        try { webClientInventario.get().uri("/api/v1/inventario/sku/"+sku).retrieve()
            .onStatus(HttpStatusCode::is4xxClientError,r->{throw new RuntimeException("Producto no encontrado en inventario: "+sku);})
            .bodyToMono(Object.class).block();
        } catch(Exception e) { throw new RuntimeException("Error verificando stock: "+e.getMessage()); }
    }
    public Carrito getOrCreate(Long clienteId) {
        return repo.findByClienteIdAndEstado(clienteId,"ACTIVO").orElseGet(()->{
            Carrito c=new Carrito(); c.setClienteId(clienteId); c.setEstado("ACTIVO"); return repo.save(c);
        });
    }
    public Carrito agregarItem(Long clienteId, ItemCarritoDTO dto) {
        log.info("Agregando item sku:{} cliente:{}", dto.getSku(), clienteId);
        verificarStock(dto.getSku());
        Carrito c=getOrCreate(clienteId);
        ItemCarrito item=new ItemCarrito(); item.setCarrito(c); item.setSku(dto.getSku());
        item.setCantidad(dto.getCantidad()); item.setPrecioUnitario(dto.getPrecioUnitario());
        c.getItems().add(item); return repo.save(c);
    }
    public void vaciar(Long carritoId) { Carrito c=repo.findById(carritoId).orElseThrow(()->new CarritoNotFoundException("Carrito no encontrado")); c.getItems().clear(); repo.save(c); }
    public void procesar(Long carritoId) { Carrito c=repo.findById(carritoId).orElseThrow(()->new CarritoNotFoundException("Carrito no encontrado")); c.setEstado("PROCESADO"); repo.save(c); }
}