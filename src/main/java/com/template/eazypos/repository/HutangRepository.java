package com.template.eazypos.repository;

import com.template.eazypos.model.BarangTransaksiBeli;
import com.template.eazypos.model.Hutang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface HutangRepository extends JpaRepository<Hutang , Long> {
    @Query(value = "SELECT h FROM Hutang h WHERE h.pelunasan <> '0'")
    List<Hutang> findAllHutang();

    @Query("SELECT h FROM Hutang h WHERE h.date BETWEEN :tglAwal AND :tglAkhir")
    List<Hutang> findByTanggalBetween(@Param("tglAwal") Date tglAwal, @Param("tglAkhir") Date tglAkhir);
}
