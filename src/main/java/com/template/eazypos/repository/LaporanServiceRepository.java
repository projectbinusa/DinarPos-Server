package com.template.eazypos.repository;

import com.template.eazypos.model.ServiceBarang;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface LaporanServiceRepository extends JpaRepository<ServiceBarang, Long> {

    @Query(value = "SELECT * FROM service", nativeQuery = true)
    List<ServiceBarang> findAllService();
}
