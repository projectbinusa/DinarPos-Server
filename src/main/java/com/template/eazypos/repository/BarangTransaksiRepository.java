package com.template.eazypos.repository;

import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BarangTransaksiRepository extends JpaRepository<BarangTransaksi , Long> {
    @Query(value = "SELECT * FROM tabel_barang_transaksi WHERE status = 'excelcom'  AND del_flag = 0  ", nativeQuery = true)
    List<BarangTransaksi> findBarangTransaksiExcelcom();

    @Query(value = "SELECT * FROM tabel_barang_transaksi WHERE status = 'dinarpos'  AND del_flag = 0  ", nativeQuery = true)
    List<BarangTransaksi> findBarangTransaksiDinarpos();

    @Query(value = "SELECT * FROM tabel_barang_transaksi WHERE status = 'excelcom' AND MONTH(tanggal) = :bulan AND del_flag = 1  ", nativeQuery = true)
    List<BarangTransaksi> findBarangTransaksiExcelcomByPeriode(@Param("bulan") int bulan);
    @Query(value = "SELECT * FROM tabel_barang_transaksi WHERE status = 'dinarpos' AND MONTH(tanggal) = :bulan AND del_flag = 1  ", nativeQuery = true)
    List<BarangTransaksi> findBarangTransaksiDinarposByPeriode(@Param("bulan") int bulan);
}
