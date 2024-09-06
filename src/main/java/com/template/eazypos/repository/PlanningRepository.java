package com.template.eazypos.repository;

import com.template.eazypos.model.Planning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PlanningRepository extends JpaRepository<Planning , Long> {
    @Query(value = "SELECT * FROM planning WHERE tgl BETWEEN :tglAwal AND :tglAkhir" , nativeQuery = true)
    List<Planning> findByTgl(Date tglAwal , Date tglAkhir);

    @Query(value = "SELECT * FROM planning WHERE tgl BETWEEN :tglAwal AND :tglAkhir AND id_salesman = :id" , nativeQuery = true)
    List<Planning> findByTglAndSalesman(Date tglAwal , Date tglAkhir , Long id);

    @Query(value = "SELECT * FROM planning WHERE id_customer = :id", nativeQuery = true)
    List<Planning> findByIdCustomer(Long id);
    @Query(value = "SELECT * FROM planning WHERE tgl = :tgl", nativeQuery = true)
    List<Planning> findByTglPlanning(Date tgl);

    @Query(value = "SELECT * FROM planning WHERE tgl = :tgl AND id_salesman = :id", nativeQuery = true)
    List<Planning> findByTglPlanningAndSalesman(Date tgl , Long id);

    @Query("SELECT p FROM Planning p JOIN p.customer c WHERE p.tgl = :tanggal " +
            "AND p.salesman.idSalesman = :salesmanId " +
            "AND p.idPlan NOT IN (SELECT k.idPlan FROM Kunjungan k)")
    List<Planning> findPlanningsWithoutKunjungan(
            @Param("tanggal") Date tanggal,
            @Param("salesmanId") Long salesmanId
    );

}
