package com.template.eazypos.repository;

import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.TransaksiIndent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransaksiIndentRepository extends JpaRepository<TransaksiIndent, Long> {
    @Query(value = "SELECT MAX(RIGHT(no_faktur, 4)) AS kd_max FROM tabel_transaksi_indent WHERE no_faktur LIKE '%PJN%'", nativeQuery = true)
    Integer findMaxKd();

    @Query(value = "SELECT tanggal FROM tabel_transaksi_indent WHERE no_faktur LIKE '%PJN%' ORDER BY tanggal DESC LIMIT 1", nativeQuery = true)
    String findLastDate();

    @Query(value = "SELECT * FROM tabel_transaksi_indent WHERE status = 'excelcom'  AND del_flag = 1  ", nativeQuery = true)
    List<TransaksiIndent> findTransaksiExcelcom();
    @Query(value = "SELECT * FROM tabel_transaksi_indent WHERE status = 'dinarpos'  AND del_flag = 1  ", nativeQuery = true)
    List<TransaksiIndent> findTransaksiDinarpos();
}
