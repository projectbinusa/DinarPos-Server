package com.template.eazypos.repository;

import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.BarangTransaksiBeli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BarangTransaksiBeliRepository extends JpaRepository<BarangTransaksiBeli , Long> {
    @Query(value = "SELECT * FROM tabel_barang_transaksi_beli WHERE status = 'excelcom'  AND del_flag = 0  ", nativeQuery = true)
    List<BarangTransaksiBeli> findBarangTransaksiBeliExcelcom();
    @Query(value = "SELECT * FROM tabel_barang_transaksi_beli WHERE status = 'dinarpos'  AND del_flag = 0  ", nativeQuery = true)
    List<BarangTransaksiBeli> findBarangTransaksiBeliDinarpos();

    @Query(value = "SELECT * FROM tabel_barang_transaksi_beli WHERE status = 'excelcom' AND MONTH(tanggal) = :bulan AND del_flag = 1  ", nativeQuery = true)
    List<BarangTransaksiBeli> findBarangTransaksiExcelcomByPeriode(@Param("bulan") int bulan);
    @Query(value = "SELECT * FROM tabel_barang_transaksi_beli WHERE status = 'dinarpos' AND MONTH(tanggal) = :bulan AND del_flag = 1  ", nativeQuery = true)
    List<BarangTransaksiBeli> findBarangTransaksiDinarposByPeriode(@Param("bulan") int bulan);
    @Query(value = "SELECT * FROM tabel_barang_transaksi_beli WHERE status = :status AND id_transakasi_beli = :idTransaksi AND del_flag = 1  ", nativeQuery = true)
    List<BarangTransaksiBeli> findBarangTransaksiDinarposByIdTransaksi(String status , Long idTransaksi);


}
