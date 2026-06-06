package com.urbano.facturacion.service;
import com.urbano.facturacion.dto.BoletaDTO;
import com.urbano.facturacion.model.BoletaElectronica;
import com.urbano.facturacion.repository.FacturacionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Service @Transactional @Slf4j
public class FacturacionService {
    @Autowired private FacturacionRepository repo;
    public List<BoletaElectronica> getAll() { return repo.findAll(); }
    public List<BoletaElectronica> getByPedido(Long pedidoId) { return repo.findByPedidoId(pedidoId); }
    public BoletaElectronica emitir(BoletaDTO dto) {
        log.info("Emitiendo boleta pedido: {}", dto.getPedidoId());
        BigDecimal iva = dto.getMontoNeto().multiply(new BigDecimal("0.19"));
        BigDecimal total = dto.getMontoNeto().add(iva);
        BoletaElectronica b=new BoletaElectronica();
        b.setPedidoId(dto.getPedidoId()); b.setRutEmisor(dto.getRutEmisor());
        b.setFolio("F-"+UUID.randomUUID().toString().substring(0,8).toUpperCase());
        b.setMontoNeto(dto.getMontoNeto()); b.setIva(iva); b.setMontoTotal(total);
        BoletaElectronica saved=repo.save(b); log.info("Boleta emitida folio: {}", saved.getFolio()); return saved;
    }
}
