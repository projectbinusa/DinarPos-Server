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
    @Query(value = "SELECT * FROM kunjungan WHERE deal = 100  AND id_salesman = :id ", nativeQuery = true)
    List<Kunjungan> findDealFinishAndSalesman(Long id);

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
    @Query(value = "SELECT * FROM kunjungan WHERE id_salesman = :id AND DATE(tgl_kunjungan) = DATE(:tgl)", nativeQuery = true)
    List<Kunjungan> findByTanggalSalesman(@Param("tgl") Date tgl, @Param("id") Long id);
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
            "WHERE FUNCTION('DATE', k.tanggalKunjungan) = FUNCTION('DATE', :tglKunjungan) " +
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

    @Query(value = "SELECT a.* " +
            "FROM kunjungan a " +
            "JOIN tabel_salesman b ON a.id_salesman = b.id_salesman " +
            "WHERE b.status != '0' " +
            "GROUP BY b.id_salesman", nativeQuery = true)
    List<Kunjungan> findKunjunganGroupedBySalesman();

    @Query("SELECT k, c FROM Kunjungan k " +
            "JOIN k.customer c " +
            "WHERE k.tanggalKunjungan BETWEEN :startDate AND :endDate " +
            "AND k.salesman.id = :salesmanId " +
            "ORDER BY k.tanggalKunjungan ASC")
    List<Object[]> findKunjunganExportLaporanSync(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("salesmanId") Long salesmanId);

    List<Kunjungan> findBySalesmanId(Long salesmanId);

    @Query("SELECT COUNT(DISTINCT k.tanggalKunjungan) FROM Kunjungan k WHERE k.salesman.id = :salesmanId")
    int countWorkdaysBySalesmanId(@Param("salesmanId") Long salesmanId);

    @Query("SELECT COUNT(k) FROM Kunjungan k WHERE k.salesman.id = :salesmanId AND FUNCTION('MONTH', k.tanggalKunjungan) = :month AND FUNCTION('YEAR', k.tanggalKunjungan) = :year")
    int countPresensiBySalesmanId(@Param("salesmanId") Long salesmanId, @Param("month") int month, @Param("year") int year);

    @Query("SELECT k FROM Kunjungan k WHERE k.deal >= 51 AND k.deal <= 80")
    List<Kunjungan> findAllKunjunganBetween51And80();

    @Query("SELECT k FROM Kunjungan k WHERE k.deal >= 0 AND k.deal <= 50")
    List<Kunjungan> findAllKunjunganBetween0And50();

    @Query(value = "SELECT *, MAX(n_visit) AS max FROM kunjungan WHERE id_salesman = :idSalesman AND id_customer = :idCustomer AND visit = 'V'", nativeQuery = true)
    List<Kunjungan> findMaxVisitBySalesmanAndCustomer(@Param("idSalesman") Long idSalesman, @Param("idCustomer") Long idCustomer);

    @Query(value = "SELECT * FROM kunjungan WHERE waktu_pengadaan = :waktuPengadaan AND id_salesman = :idSalesman", nativeQuery = true)
    List<Kunjungan> findByWaktuPengadaanAndSalesman(@Param("waktuPengadaan") String waktuPengadaan, @Param("idSalesman") Long idSalesman);

    @Query(value = "SELECT * FROM kunjungan WHERE id_report = :id AND id_planning IS NULL", nativeQuery = true)
    Optional<Kunjungan> findByIdAndPlanningNotNull(Long id);
}


