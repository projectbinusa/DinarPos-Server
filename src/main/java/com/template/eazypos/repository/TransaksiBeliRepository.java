package com.template.eazypos.repository;

import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.TransaksiBeli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface TransaksiBeliRepository extends JpaRepository<TransaksiBeli , Long> {
    @Query(value = "SELECT t.no_faktur FROM tabel_transaksi_beli t WHERE MONTH(t.tanggal) = ?1 AND YEAR(t.tanggal) = ?2 ORDER BY t.id_transaksi_beli DESC LIMIT 1", nativeQuery = true)
    String findLastNotaByMonthAndYear(int month, int year);

    @Query(value = "SELECT MAX(RIGHT(no_faktur, 4)) AS kd_max FROM tabel_transaksi_beli WHERE no_faktur LIKE '%PJN%'", nativeQuery = true)
    Integer findMaxKd();

    @Query(value = "SELECT tanggal FROM tabel_transaksi_beli WHERE no_faktur LIKE '%PJN%' ORDER BY tanggal DESC LIMIT 1", nativeQuery = true)
    String findLastDate();

    @Query(value = "SELECT * FROM tabel_transaksi_beli WHERE status = 'excelcom'  AND del_flag = 0  ", nativeQuery = true)
    List<TransaksiBeli> findTransaksiBeliExcelcom();
    @Query(value = "SELECT * FROM tabel_transaksi_beli WHERE status = 'dinarpos'  AND del_flag = 0  ", nativeQuery = true)
    List<TransaksiBeli> findTransaksiBeliDinarpos();

    @Query(value = "SELECT * FROM tabel_transaksi_beli WHERE status = 'excelcom' AND MONTH(tanggal) = :bulan AND YEAR(tanggal) =:tahun AND del_flag = 1  ", nativeQuery = true)
    List<TransaksiBeli> findTransaksiBeliExcelcomByPeriode(@Param("bulan") int bulan ,  @Param("tahun") int tahun);
    @Query(value = "SELECT * FROM tabel_transaksi_beli WHERE status = 'dinarpos' AND MONTH(tanggal) = :bulan AND YEAR(tanggal) =:tahun AND del_flag = 1  ", nativeQuery = true)
    List<TransaksiBeli> findTransaksiBeliDinarposByPeriode(@Param("bulan") int bulan ,  @Param("tahun") int tahun);

    @Query(value = "SELECT t FROM TransaksiBeli t WHERE t.tanggal BETWEEN :tanggalAwal AND :tanggalAkhir AND t.suplier.id = :suplierId AND t.status = :status")
    List<TransaksiBeli> findByTanggal(Date tanggalAwal, Date tanggalAkhir, Long suplierId, String status);

    @Query(value = "SELECT * FROM tabel_transaksi_beli WHERE status = :status AND MONTH(tanggal) = :bulan AND YEAR(tanggal) = :tahun AND del_flag = 1  ", nativeQuery = true)
    List<TransaksiBeli> findTransaksiByMonthAndYear(@Param("bulan") int bulan,@Param("tahun") int tahun , @Param("status") String status);


}
