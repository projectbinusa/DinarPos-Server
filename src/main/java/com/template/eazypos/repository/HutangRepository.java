package com.template.eazypos.repository;

import com.template.eazypos.model.BarangTransaksiBeli;
import com.template.eazypos.model.Hutang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HutangRepository extends JpaRepository<Hutang , Long> {


    @Query("SELECT h FROM Hutang h WHERE h.date BETWEEN :tglAwal AND :tglAkhir")
    List<Hutang> findByTanggalBetween(@Param("tglAwal") Date tglAwal, @Param("tglAkhir") Date tglAkhir);

    @Query(value = "SELECT * FROM hutang WHERE id_transaksi_beli =:id" ,nativeQuery = true)
    List<Hutang> findByidTransaksiBeli(Long id);

    @Query("SELECT h.date FROM Hutang h WHERE h.transaksiBeli = :idTransaksiBeli ORDER BY h.date DESC ")
    Optional<Date> findLatestPelunasanDateByIdTransaksiBeli(@Param("idTransaksiBeli") Long idTransaksiBeli);
}
