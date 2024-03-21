package com.template.eazypos.repository;

import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface BarangTransaksiRepository extends JpaRepository<BarangTransaksi , Long> {
    @Query(value = "SELECT * FROM tabel_barang_transaksi WHERE status = 'excelcom'  AND del_flag = 0  ", nativeQuery = true)
    List<BarangTransaksi> findBarangTransaksiExcelcom();

    @Query(value = "SELECT * FROM tabel_barang_transaksi WHERE status = 'dinarpos'  AND del_flag = 0  ", nativeQuery = true)
    List<BarangTransaksi> findBarangTransaksiDinarpos();
    @Query(value = "SELECT * FROM tabel_barang_transaksi WHERE id_transaksi =:transaksi AND status =:status", nativeQuery = true)
    List<BarangTransaksi> findBarangTransaksiByIdTransaksi(Long transaksi , String status);

    @Query(value = "SELECT * FROM tabel_barang_transaksi WHERE status = 'excelcom' AND MONTH(tanggal) = :bulan AND YEAR(tahun) =:tahun AND del_flag = 1  ", nativeQuery = true)
    List<BarangTransaksi> findBarangTransaksiExcelcomByPeriode(@Param("bulan") int bulan , @Param("tahun") int tahun);
    @Query(value = "SELECT * FROM tabel_barang_transaksi WHERE status = 'dinarpos' AND MONTH(bulan) = :bulan AND YEAR(tahun) =:tahun  AND del_flag = 1  ", nativeQuery = true)
    List<BarangTransaksi> findBarangTransaksiDinarposByPeriode(@Param("bulan") int bulan,  @Param("tahun") int tahun);

    @Query(value = "SELECT t FROM BarangTransaksi t WHERE t.tanggal BETWEEN :tanggalAwal AND :tanggalAkhir AND t.barcodeBarang = :barcode AND t.status = :status")
    List<BarangTransaksi> findByTanggal(Date tanggalAwal, Date tanggalAkhir, String barcode, String status);

}
