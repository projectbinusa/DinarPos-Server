package com.template.eazypos.repository;

import com.template.eazypos.model.KasHarian;
import com.template.eazypos.model.ServiceBarang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface KasHarianRepository extends JpaRepository<KasHarian , Long> {
    @Query("SELECT k FROM KasHarian k WHERE k.timestamp BETWEEN :start AND :end")
    List<KasHarian> findByTimestampBetween(@Param("start") Date start, @Param("end") Date end);
}
