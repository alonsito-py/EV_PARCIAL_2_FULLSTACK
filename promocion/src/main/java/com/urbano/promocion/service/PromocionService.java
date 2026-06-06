package com.urbano.promocion.service;
import com.urbano.promocion.dto.*;
import com.urbano.promocion.exception.PromocionNotFoundException;
import com.urbano.promocion.model.*;
import com.urbano.promocion.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @Transactional @Slf4j
public class PromocionService {
    @Autowired private PromocionRepository promoRepo;
    @Autowired private CuponRepository cuponRepo;
    public List<Promocion> getAll() { return promoRepo.findByActivoTrue(); }
    public Promocion create(PromocionDTO dto) {
        Promocion p=new Promocion(); p.setNombre(dto.getNombre()); p.setDescuento(dto.getDescuento());
        p.setFechaInicio(dto.getFechaInicio()); p.setFechaFin(dto.getFechaFin()); return promoRepo.save(p);
    }
    public Cupon createCupon(CuponDTO dto) {
        log.info("Creando cupon: {}", dto.getCodigo());
        if (cuponRepo.findByCodigo(dto.getCodigo()).isPresent()) throw new RuntimeException("Cupon ya existe: "+dto.getCodigo());
        Cupon c=new Cupon(); c.setCodigo(dto.getCodigo()); c.setDescuento(dto.getDescuento()); c.setUsosMaximos(dto.getUsosMaximos()); return cuponRepo.save(c);
    }
    public Cupon validarCupon(String codigo) {
        Cupon c=cuponRepo.findByCodigo(codigo).orElseThrow(()->new PromocionNotFoundException("Cupon no encontrado: "+codigo));
        if (!c.isActivo() || c.getUsosActuales()>=c.getUsosMaximos()) throw new RuntimeException("Cupon no valido o agotado: "+codigo);
        c.setUsosActuales(c.getUsosActuales()+1); return cuponRepo.save(c);
    }
}