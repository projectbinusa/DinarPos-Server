package com.template.eazypos.repository;

import com.template.eazypos.model.Ijin;
import com.template.eazypos.model.Kunjungan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface KunjunganRepository extends JpaRepository<Kunjungan , Long> {
    @Query(value = "SELECT * FROM kunjungan WHERE deal < 100" , nativeQuery = true)
    List<Kunjungan> findDealPo();

    @Query(value = "SELECT * FROM kunjungan WHERE deal < 100 AND deal >= 80 AND id_salesman = :id ", nativeQuery = true)
    List<Kunjungan> findDealPoAndSalesman(Long id);

    @Query(value = "SELECT * FROM kunjungan WHERE deal = 100" , nativeQuery = true)
    List<Kunjungan> findDealFinish();

    @Query(value = "SELECT * FROM kunjungan WHERE deal = 100 AND id_salesman = :id ", nativeQuery = true)
    List<Kunjungan> findDealFinishAndCustomer(Long id);

    @Query(value = "SELECT * FROM kunjungan WHERE tgl_kunjungan BETWEEN :tglAwal AND :tglAkhir" , nativeQuery = true)
    List<Kunjungan> findByDate(Date tglAwal , Date tglAkhir);
    @Query(value = "SELECT * FROM kunjungan WHERE tgl_kunjungan BETWEEN :tglAwal AND :tglAkhir AND id_salesman = :id" , nativeQuery = true)
    List<Kunjungan> findByDateAndSalesman(Date tglAwal , Date tglAkhir , Long id);
    @Query(value = "SELECT * FROM kunjungan WHERE tgl_kunjungan = :tgl" , nativeQuery = true)
    List<Kunjungan> findByTanggal(Date tgl);
    @Query(value = "SELECT * FROM kunjungan WHERE tgl_kunjungan = :tgl AND id_salesman = :id" , nativeQuery = true)
    List<Kunjungan> findByTanggalSalesman(Date tgl , Long id);
    @Query(value = "SELECT * FROM kunjungan WHERE id_customer = :id" , nativeQuery = true)
    List<Kunjungan> findByIdCustomer(Long id);

    @Query(value = "SELECT * FROM kunjungan WHERE MONTH(tgl_kunjungan) = :bulan" , nativeQuery = true)
    List<Kunjungan> findByBulan(int bulan);

    @Query(value = "SELECT k FROM Kunjungan k JOIN k.salesman s " +
            "GROUP BY k.tanggalKunjungan, s.id " +
            "ORDER BY k.tanggalKunjungan DESC")
    List<Kunjungan> findKunjunganGroupedByTanggalAndSalesman();

    @Query(value = "SELECT k FROM Kunjungan k JOIN k.salesman s " +
            "WHERE k.tanggalKunjungan BETWEEN :tgl1 AND :tgl2 " +
            "GROUP BY k.tanggalKunjungan, s.id " +
            "ORDER BY k.tanggalKunjungan ASC")
    List<Kunjungan> findKunjunganByDateRangeGroupedBySalesman(
            @Param("tgl1") Date tgl1,
            @Param("tgl2") Date tgl2);

    @Query(value = "SELECT k FROM Kunjungan k JOIN k.salesman s " +
            "WHERE k.tanggalKunjungan BETWEEN :tgl1 AND :tgl2 " +
            "AND s.id = :idm " +
            "GROUP BY k.tanggalKunjungan " +
            "ORDER BY k.tanggalKunjungan ASC")
    List<Kunjungan> findKunjunganByDateRangeAndSalesman(
            @Param("tgl1") Date tgl1,
            @Param("tgl2") Date tgl2,
            @Param("idm") Long idm);
    @Query("SELECT k FROM Kunjungan k WHERE k.salesman.id = :idSalesman GROUP BY k.tanggalKunjungan")
    List<Kunjungan> findBySalesmanGroupedByDate(@Param("idSalesman") Long idSalesman);

    @Query("SELECT k FROM Kunjungan k " +
            "JOIN FETCH k.planning p " +
            "JOIN FETCH p.customer c " +
            "JOIN FETCH k.salesman m " +
            "WHERE k.tanggalKunjungan = :tglKunjungan " +
            "AND k.salesman.id = :idSalesman")
    List<Kunjungan> findKunjunganByTglAndSalesman(
            @Param("tglKunjungan") Date tglKunjungan,
            @Param("idSalesman") Long idSalesman);

    @Query("SELECT k FROM Kunjungan k " +
            "WHERE k.salesman.id = :idSalesman " +
            "AND k.tanggalKunjungan BETWEEN :tglMulai AND :tglSelesai " +
            "AND k.foto <> ''")
    List<Kunjungan> findKunjunganBySalesmanAndDateRangeWithFoto(
            @Param("idSalesman") Long idSalesman,
            @Param("tglMulai") Date tglMulai,
            @Param("tglSelesai") Date tglSelesai);

    @Query("SELECT MAX(k.nVisit) FROM Kunjungan k WHERE k.salesman.id = :idSalesman AND k.customer.id = :idCustomer AND k.visit = 'V'")
    Optional<Long> findMaxNVisitBySalesmanAndCustomer(@Param("idSalesman") Long idSalesman, @Param("idCustomer") Long idCustomer);

    // Query untuk menghitung jumlah baris yang sesuai kriteria
    @Query("SELECT COUNT(k) FROM Kunjungan k WHERE k.salesman.id = :idSalesman AND k.customer.id = :idCustomer AND k.visit = 'V'")
    Long countKunjunganBySalesmanAndCustomer(@Param("idSalesman") Long idSalesman, @Param("idCustomer") Long idCustomer);

    @Query("SELECT s.id AS idMarketting, s.target AS target, s.namaSalesman AS nama, MAX(k.tanggalKunjungan) AS upDate, s.foto AS fotopp " +
            "FROM Kunjungan k " +
            "JOIN k.salesman s " +
            "WHERE s.status != '0' " +
            "GROUP BY s.id " +
            "ORDER BY MAX(k.tanggalKunjungan) DESC")
    List<Object[]> findKunjunganGroupedBySalesman();

}


