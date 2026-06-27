package com.urbano.marketing.repository;
import com.urbano.marketing.model.CampaniaSocial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface MarketingRepository extends JpaRepository<CampaniaSocial, Long> {
    List<CampaniaSocial> findByRed(String red);
    List<CampaniaSocial> findByEstado(String estado);
}