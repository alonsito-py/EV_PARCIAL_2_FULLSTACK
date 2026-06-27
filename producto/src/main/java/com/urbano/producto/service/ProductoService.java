package com.urbano.producto.service;
import com.urbano.producto.dto.ProductoDTO;
import com.urbano.producto.exception.ProductoNotFoundException;
import com.urbano.producto.model.*;
import com.urbano.producto.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @Transactional @Slf4j
public class ProductoService {
    @Autowired private ProductoRepository repo;
    @Autowired private CategoriaRepository catRepo;
    public List<Producto> getAll() { log.info("Listando productos activos"); return repo.findByActivoTrue(); }
    public Producto getBySku(String sku) { return repo.findBySku(sku).orElseThrow(()->new ProductoNotFoundException("Producto no encontrado sku: "+sku)); }
    public Producto getById(Long id) { return repo.findById(id).orElseThrow(()->new ProductoNotFoundException("Producto no encontrado id: "+id)); }
    public List<Producto> getByCategoria(Long catId) { return repo.findByCategoriaId(catId); }
    public Producto create(ProductoDTO dto) {
        log.info("Creando producto sku: {}", dto.getSku());
        if (repo.findBySku(dto.getSku()).isPresent()) throw new RuntimeException("SKU ya existe: "+dto.getSku());
        Categoria cat=catRepo.findById(dto.getCategoriaId()).orElseThrow(()->new RuntimeException("Categoria no encontrada"));
        Producto p=new Producto(); p.setSku(dto.getSku()); p.setNombre(dto.getNombre()); p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio()); p.setTalla(dto.getTalla()); p.setColor(dto.getColor()); p.setCategoria(cat);
        return repo.save(p);
    }
    public Producto update(Long id, ProductoDTO dto) {
        Producto p=getById(id); p.setNombre(dto.getNombre()); p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio()); p.setTalla(dto.getTalla()); p.setColor(dto.getColor()); return repo.save(p);
    }
    public void delete(Long id) { log.warn("Desactivando producto id: {}",id); Producto p=getById(id); p.setActivo(false); repo.save(p); }
}