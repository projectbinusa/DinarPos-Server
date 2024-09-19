package com.template.eazypos.repository;

import com.template.eazypos.model.Ijin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IjinRepository extends JpaRepository<Ijin,Long> {
    @Query(value = "SELECT * FROM ijin WHERE tgl_a BETWEEN :tglAwal AND :tglAkhir" , nativeQuery = true)
    List<Ijin> getByTanggal(Date tglAwal , Date tglAkhir);
    @Query(value = "SELECT * FROM ijin WHERE id_marketting = :idSalesman " +
            "AND tgl_a BETWEEN :tglMulai AND :tglSelesai", nativeQuery = true)
    List<Ijin> findIjinBySalesmanAndDateRangeNative(
            @Param("idSalesman") Long idSalesman,
            @Param("tglMulai") Date tglMulai,
            @Param("tglSelesai") Date tglSelesai);
}
