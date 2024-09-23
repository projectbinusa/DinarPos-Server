package com.template.eazypos.repository;

import com.template.eazypos.model.Planning;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Query(value = "SELECT * FROM planning WHERE DATE(timestamp) = :tgl", nativeQuery = true)
    List<Planning> findByTglPlanning(@Param("tgl") Date tgl);

    @Query(value = "SELECT * FROM planning WHERE DATE(tgl) = :tgl AND id_salesman = :id", nativeQuery = true)
    List<Planning> findByTglPlanningAndSalesman(@Param("tgl") Date tgl, @Param("id") Long id);

    @Query(value = "SELECT * FROM planning WHERE DATE(timestamp) = :tgl AND id_salesman = :id", nativeQuery = true)
    Page<Planning> findByTglPlanningAndSalesman(@Param("tgl") Date tgl, @Param("id") Long id, Pageable pageable);


    @Query("SELECT p FROM Planning p JOIN p.customer c WHERE DATE(p.timestamp) = :tanggal " +
            "AND p.salesman.id = :salesmanId " +
            "AND p.idPlan NOT IN (SELECT k.planning FROM Kunjungan k)")
    List<Planning> findPlanningsWithoutKunjungan(
            @Param("tanggal") Date tanggal,
            @Param("salesmanId") Long salesmanId
    );
    @Query("SELECT p FROM Planning p JOIN p.customer c " +
            "WHERE p.tgl = :tglKunjungan " +
            "AND p.salesman.id = :idSalesman")
    List<Planning> findByTglAndSalesman(
            @Param("tglKunjungan") Date tglKunjungan,
            @Param("idSalesman") Long idSalesman);

    @Query(value = "SELECT * FROM planning WHERE id_salesman = :id GROUP BY DATE(tgl) ORDER BY DATE(tgl) DESC", nativeQuery = true)
    List<Planning> findBySalesmanGroupByDate(@Param("id") Long id);

    @Query("SELECT s.id AS idMarketting, s.target AS target, s.namaSalesman AS nama, MAX(p.tgl) AS upDate, s.foto AS fotopp " +
            "FROM Planning p " +
            "JOIN p.salesman s " +
            "WHERE s.status != '0' " +
            "GROUP BY s.id " +
            "ORDER BY MAX(p.tgl) DESC")
    List<Object[]> findPlanningAndSalesmanWithMaxTgl();
}
