package com.template.eazypos.repository;

import com.template.eazypos.model.Prov;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProvRepository extends JpaRepository<Prov , Long> {
    @Query(value = "SELECT * FROM prov WHERE LOWER(nama_prov) = LOWER(:nama)", nativeQuery = true)
    Optional<Prov> findByNama(@Param("nama") String nama);
}
