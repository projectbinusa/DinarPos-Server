package com.template.eazypos.repository;

import com.template.eazypos.model.Barang;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.TransaksiBeli;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface TransaksiRepository extends JpaRepository<Transaksi, Long> {
    @Query(value = "SELECT no_faktur FROM tabel_transaksi  WHERE MONTH(tanggal) = :bulan AND YEAR(tanggal) = :tahun ORDER BY id_transaksi DESC LIMIT 1", nativeQuery = true)
    String findLastNotaByMonthAndYear(@Param("bulan") int bulan, @Param("tahun") int tahun);

    @Query(value = "SELECT MAX(RIGHT(no_faktur, 4)) AS kd_max FROM tabel_transaksi WHERE no_faktur LIKE '%PJN%'", nativeQuery = true)
    Integer findMaxKd();

    @Query(value = "SELECT tanggal FROM tabel_transaksi WHERE no_faktur LIKE '%PJN%' ORDER BY tanggal DESC LIMIT 1", nativeQuery = true)
    String findLastDate();

    @Query(value = "SELECT h FROM Transaksi h WHERE h.kekurangan <> '0'")
    List<Transaksi> findAllPiutang();

    @Query(value = "SELECT * FROM tabel_transaksi WHERE status = 'excelcom' AND MONTH(tanggal) = :bulan AND YEAR(tanggal) =:tahun AND del_flag = 1  ", nativeQuery = true)
    List<Transaksi> findTransaksiExcelcomByPeriode(@Param("bulan") int bulan , @Param("tahun") int tahun);
    @Query(value = "SELECT * FROM tabel_transaksi WHERE status = 'excelcom'  AND del_flag = 0  ", nativeQuery = true)
    List<Transaksi> findTransaksiExcelcom();
    @Query(value = "SELECT * FROM tabel_transaksi WHERE status = 'dinarpos'  AND del_flag = 0  ", nativeQuery = true)
    List<Transaksi> findTransaksiDinarpos();
    @Query(value = "SELECT * FROM tabel_transaksi WHERE status = 'dinarpos' AND MONTH(tanggal) = :bulan AND YEAR(tanggal) =:tahun  AND del_flag = 1  ", nativeQuery = true)
    List<Transaksi> findTransaksiDinarposByPeriode(@Param("bulan") int bulan ,  @Param("tahun") int tahun);

    @Query(value = "SELECT * FROM tabel_transaksi  WHERE tanggal_notif_7 <= :tgl AND status = 'excelcom' AND 7_hari = 1" , nativeQuery = true)
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

    @Query(value = "SELECT * FROM tabel_transaksi WHERE 7_hari = 0 AND status = :status", nativeQuery = true)
    List<Transaksi> findAllKonfrimasi7Hari(@Param("status") String status);

    @Query(value = "SELECT * FROM tabel_transaksi WHERE 30_hari = 0 AND status = :status", nativeQuery = true)
    List<Transaksi> findAllKonfrimasi30Hari(@Param("status") String status);

    @Query(value = "SELECT * FROM tabel_transaksi WHERE 90_hari = 0 AND status = :status", nativeQuery = true)
    List<Transaksi> findAllKonfrimasi90Hari(@Param("status") String status);

    @Query(value = "SELECT * FROM tabel_transaksi WHERE 120_hari = 0 AND status = :status", nativeQuery = true)
    List<Transaksi> findAllKonfrimasi120Hari(@Param("status") String status);

    @Query(value = "SELECT * FROM tabel_transaksi WHERE 365_hari = 0 AND status = :status", nativeQuery = true)
    List<Transaksi> findAllKonfrimasi365Hari(@Param("status") String status);

    @Query("SELECT t FROM Transaksi t WHERE t.serviceBarang.idTT = :idTt")
    List<Transaksi> findTransaksiByIdTt(@Param("idTt") Long idTt);


}
