package com.template.eazypos.repository;

import com.template.eazypos.model.ServiceBarang;
import org.springframework.data.jpa.repository.*;

import java.util.List;

public interface LaporanServiceRepository extends JpaRepository<ServiceBarang, Long> {

    @Query(value = "SELECT * FROM service", nativeQuery = true)
    List<ServiceBarang> findAllService();

    @Query(value = "SELECT * FROM service WHERE status_end = 'PROSES'", nativeQuery = true)
    List<ServiceBarang> findAllServiceWithStatusProses();

    @Query(value = "SELECT * FROM service WHERE status_end = 'CANCEL'", nativeQuery = true)
    List<ServiceBarang> findAllServiceWithStatusCancel();

    @Query(value = "SELECT * FROM service WHERE status_end = 'READY'", nativeQuery = true)
    List<ServiceBarang> findAllServiceWithStatusReady();

    @Query(value = "SELECT * FROM service WHERE taken = 'Y'", nativeQuery = true)
    List<ServiceBarang> findAllServiceWithTakenY();
}
