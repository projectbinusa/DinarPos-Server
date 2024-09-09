package com.template.eazypos.repository;

import com.template.eazypos.model.KabKot;
import com.template.eazypos.model.Prov;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KabKotRepository extends JpaRepository<KabKot,Long> {
    @Query(value = "SELECT * FROM kabkot WHERE LOWER(nama_kabkot) = LOWER(:nama)", nativeQuery = true)
    Optional<KabKot> findByNama(@Param("nama") String nama);
}
