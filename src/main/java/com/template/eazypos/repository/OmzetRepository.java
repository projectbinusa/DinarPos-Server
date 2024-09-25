package com.template.eazypos.repository;

import com.template.eazypos.model.BarangTransaksiBeli;
import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.model.Omzet;
import com.template.eazypos.model.TransaksiIndent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OmzetRepository extends JpaRepository<Omzet , Long> {
    @Query(value = "SELECT * FROM omzet WHERE id_transaksi = :id ", nativeQuery = true)
    Optional<Omzet> findByIdTransaksi(Long id);

    @Query(value = "SELECT * FROM omzet WHERE  MONTH(tgl) = :bulan AND YEAR(tgl) = :tahun", nativeQuery = true)
    List<Omzet> findByBulanTahun(@Param("bulan") int bulan , @Param("tahun") int tahun);

    @Query(value = "SELECT * FROM omzet WHERE MONTH(tgl) = :bulan AND YEAR(tgl) = :tahun AND id_salesman = :id", nativeQuery = true)
    List<Omzet> findByBulanTahunSalesman(@Param("bulan") int bulan, @Param("tahun") int tahun, @Param("id") Long id);

    @Query(value = "SELECT * FROM omzet WHERE tgl BETWEEN :tglAwal AND :tglAkhir AND id_salesman = :id" , nativeQuery = true)
    List<Omzet> findByDateAndSalesman(Date tglAwal , Date tglAkhir , Long id);

    List<Omzet> findBySalesmanId(Long salesmanId);


}
