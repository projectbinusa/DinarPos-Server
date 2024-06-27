package com.template.eazypos.repository;

import com.template.eazypos.model.KasHarian;
import com.template.eazypos.model.ServiceBarang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface KasHarianRepository extends JpaRepository<KasHarian , Long> {
    List<KasHarian> findByTimestampBetween(Date awal, Date akhir);
}
