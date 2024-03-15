package com.template.eazypos.repository;

import com.template.eazypos.model.Barang;
import com.template.eazypos.model.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransaksiRepository extends JpaRepository<Transaksi, Long> {
    @Query(value = "SELECT t.no_faktur FROM tabel_transaksi t WHERE MONTH(t.tanggal) = ?1 AND YEAR(t.tanggal) = ?2 ORDER BY t.id_transaksi DESC LIMIT 1", nativeQuery = true)
    String findLastNotaByMonthAndYear(int month, int year);

    @Query(value = "SELECT * FROM tabel_transaksi WHERE status = 'excelcom' AND MONTH(tanggal) = :bulan AND del_flag = 1  ", nativeQuery = true)
    List<Transaksi> findTransaksiExcelcomByPeriode(@Param("bulan") int bulan);
    @Query(value = "SELECT * FROM tabel_transaksi WHERE status = 'excelcom'  AND del_flag = 0  ", nativeQuery = true)
    List<Transaksi> findTransaksiExcelcom();
    @Query(value = "SELECT * FROM tabel_transaksi WHERE status = 'dinarpos'  AND del_flag = 0  ", nativeQuery = true)
    List<Transaksi> findTransaksiDinarpos();
    @Query(value = "SELECT * FROM tabel_transaksi WHERE status = 'dinarpos' AND MONTH(tanggal) = :bulan AND del_flag = 1  ", nativeQuery = true)
    List<Transaksi> findTransaksiDinarposByPeriode(@Param("bulan") int bulan);

    @Query(value = "SELECT t FROM Transaksi t WHERE t.tanggalNotif7 <= :tgl AND t.status = 'excelcom' AND t.hari7 = 1")
    List<Transaksi> findAllTransaksi7Excelcom(Date tgl);

    @Query(value = "SELECT t FROM Transaksi t WHERE t.tanggalNotif30 <= :tgl AND t.status = 'excelcom' AND t.hari30 = 1")
    List<Transaksi> findAllTransaksi30Excelcom(Date tgl);

    @Query(value = "SELECT t FROM Transaksi t WHERE t.tanggalNotif90 <= :tgl AND t.status = 'excelcom' AND t.hari90 = 1")
    List<Transaksi> findAllTransaksi90Excelcom(Date tgl);

    @Query(value = "SELECT t FROM Transaksi t WHERE t.tanggalNotif120 <= :tgl AND t.status = 'excelcom' AND t.hari120 = 1")
    List<Transaksi> findAllTransaksi120Excelcom(Date tgl);

    @Query(value = "SELECT t FROM Transaksi t WHERE t.tanggalNotif365 <= :tgl AND t.status = 'excelcom' AND t.hari365 = 1")
    List<Transaksi> findAllTransaksi365Excelcom(Date tgl);

    @Query(value = "SELECT t FROM Transaksi t WHERE t.tanggalNotif7 <= :tgl AND t.status = 'dinarpos' AND t.hari7 = 1")
    List<Transaksi> findAllTransaksi7Dinarpos(Date tgl);

    @Query(value = "SELECT t FROM Transaksi t WHERE t.tanggalNotif30 <= :tgl AND t.status = 'dinarpos' AND t.hari30 = 1")
    List<Transaksi> findAllTransaksi30Dinarpos(Date tgl);

    @Query(value = "SELECT t FROM Transaksi t WHERE t.tanggalNotif90 <= :tgl AND t.status = 'dinarpos' AND t.hari90 = 1")
    List<Transaksi> findAllTransaksi90Dinarpos(Date tgl);

    @Query(value = "SELECT t FROM Transaksi t WHERE t.tanggalNotif120 <= :tgl AND t.status = 'dinarpos' AND t.hari120 = 1")
    List<Transaksi> findAllTransaksi120Dinarpos(Date tgl);

    @Query(value = "SELECT t FROM Transaksi t WHERE t.tanggalNotif365 <= :tgl AND t.status = 'dinarpos' AND t.hari365 = 1")
    List<Transaksi> findAllTransaksi365Dinarpos(Date tgl);

    @Query(value = "SELECT t FROM Transaksi t WHERE t.tanggal BETWEEN :tanggalAwal AND :tanggalAkhir AND t.salesman.id = :salesmanId AND t.status = :status")
    List<Transaksi> findByTanggal(Date tanggalAwal, Date tanggalAkhir, Long salesmanId, String status);
    @Query(value = "SELECT t FROM Transaksi t WHERE t.tanggal BETWEEN :tanggalAwal AND :tanggalAkhir AND t.customer.id = :customerId AND t.status = :status")
    List<Transaksi> findCustomerByTanggal(Date tanggalAwal, Date tanggalAkhir, Long customerId, String status);

    @Query(value = "SELECT * FROM tabel_transaksi WHERE status = :status AND MONTH(tanggal) = :bulan AND YEAR(tanggal) = :tahun AND del_flag = 1  ", nativeQuery = true)
    List<Transaksi> findTransaksiByMonthAndYear(@Param("bulan") int bulan,@Param("tahun") int tahun , @Param("status") String status);

}
