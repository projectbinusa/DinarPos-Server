package com.template.eazypos.repository;

import com.template.eazypos.model.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface PlanningRepository extends JpaRepository<Planning , Long> {
    @Query(value = "SELECT * FROM planning WHERE tgl BETWEEN :tglAwal AND :tglAkhir" , nativeQuery = true)
    List<Planning> findByTgl(Date tglAwal , Date tglAkhir);

    @Query(value = "SELECT * FROM planning WHERE id_customer = :id", nativeQuery = true)
    List<Planning> findByIdCustomer(Long id);
    @Query(value = "SELECT * FROM planning WHERE tgl = :tgl", nativeQuery = true)
    List<Planning> findByTglPlanning(Date tgl);

    @Query(value = "SELECT * FROM planning WHERE tgl = :tgl AND id_salesman = :id", nativeQuery = true)
    List<Planning> findByTglPlanningAndSalesman(Date tgl , Long id);

}
