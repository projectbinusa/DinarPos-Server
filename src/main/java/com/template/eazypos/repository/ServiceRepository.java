package com.template.eazypos.repository;


import com.template.eazypos.model.ServiceBarang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ServiceRepository extends JpaRepository<ServiceBarang, Long> {
    @Query(value = "SELECT * FROM service  WHERE tgl_masuk BETWEEN :tanggalAwal AND :tanggalAkhir  AND status_end = :status" , nativeQuery = true)
    List<ServiceBarang> findByTanggalAndStatus(Date tanggalAwal, Date tanggalAkhir, String status);
}
