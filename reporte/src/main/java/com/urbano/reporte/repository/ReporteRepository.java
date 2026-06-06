package com.urbano.reporte.repository;
import com.urbano.reporte.model.CierreVentaDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface ReporteRepository extends JpaRepository<CierreVentaDiario, Long> {
    List<CierreVentaDiario> findByFechaBetween(LocalDate inicio, LocalDate fin);
}
