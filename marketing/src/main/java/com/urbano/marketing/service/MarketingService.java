package com.urbano.marketing.service;
import com.urbano.marketing.dto.CampaniaDTO;
import com.urbano.marketing.exception.MarketingNotFoundException;
import com.urbano.marketing.model.CampaniaSocial;
import com.urbano.marketing.repository.MarketingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service @Transactional @Slf4j
public class MarketingService {
    @Autowired private MarketingRepository repo;
    public List<CampaniaSocial> getAll() { return repo.findAll(); }
    public CampaniaSocial getById(Long id) { return repo.findById(id).orElseThrow(()->new MarketingNotFoundException("Campaña no encontrada id: "+id)); }
    public CampaniaSocial crear(CampaniaDTO dto) {
        log.info("Creando campaña: {} red: {}", dto.getNombre(), dto.getRed());
        CampaniaSocial c=new CampaniaSocial(); c.setNombre(dto.getNombre()); c.setRed(dto.getRed());
        c.setContenido(dto.getContenido()); c.setFechaProgramada(dto.getFechaProgramada()); c.setEstado("PROGRAMADA");
        return repo.save(c);
    }
    public CampaniaSocial publicar(Long id) {
        log.info("Publicando campaña id: {}", id);
        CampaniaSocial c=getById(id); c.setEstado("PUBLICADA"); return repo.save(c);
    }
}
