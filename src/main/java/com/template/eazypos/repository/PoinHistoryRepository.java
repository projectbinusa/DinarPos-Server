package com.template.eazypos.repository;

import com.template.eazypos.dto.PoinHistoryMonthDTO;
import com.template.eazypos.model.Poin;
import com.template.eazypos.model.PoinHistory;
import com.template.eazypos.model.Transaksi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PoinHistoryRepository extends JpaRepository<PoinHistory, Long> {
    @Query("SELECT new com.template.eazypos.dto.PoinHistoryMonthDTO(ph.teknisi.id, ph.teknisi.nama, SUM(ph.poin), SUM(ph.nominal)) " +
            "FROM PoinHistory ph " +
            "JOIN ph.teknisi t " +
            "WHERE FUNCTION('YEAR', ph.tanggal) = FUNCTION('YEAR', :month) " +
            "AND FUNCTION('MONTH', ph.tanggal) = FUNCTION('MONTH', :month) " +
            "GROUP BY ph.teknisi.id, ph.teknisi.nama " +
            "ORDER BY SUM(ph.poin) DESC")
    List<PoinHistoryMonthDTO> findByMonth(@Param("month") LocalDate month);

    @Query(value = "SELECT * FROM poin_history  WHERE tgl BETWEEN :tanggalAwal AND :tanggalAkhir" , nativeQuery = true)
    List<PoinHistory> findByTanggal(Date tanggalAwal, Date tanggalAkhir);

    @Query(value = "SELECT * FROM poin_history WHERE ket = :id" , nativeQuery = true)
    Optional<PoinHistory> findByIdTT(String id);
    @Query(value = "SELECT * FROM poin_history WHERE ket = :id" , nativeQuery = true)
    List<PoinHistory> findByIdTeknisi(String id);

    @Query("SELECT p FROM PoinHistory p WHERE p.teknisi = :idTeknisi AND FUNCTION('YEAR', p.tanggal) = FUNCTION('YEAR', CURRENT_DATE) AND FUNCTION('MONTH', p.tanggal) = :month")
    List<PoinHistory> findPoinByTeknisiForMonth(@Param("idTeknisi") Long idTeknisi, @Param("month") int month);

    @Query(value = "SELECT * FROM poin_history  WHERE tgl BETWEEN :tanggalAwal AND :tanggalAkhir AND id_teknisi = :id" , nativeQuery = true)
    List<PoinHistory> findByDateRangeAndTeknisi( Date tanggalAwal, Date tanggalAkhir, Long id);

    @Query(value = "SELECT * FROM poin_history WHERE ket = :keterangan", nativeQuery = true)
    List<PoinHistory> findAllByKeterangan(@Param("keterangan") String keterangan);

    @Query("SELECT COALESCE(SUM(ph.poin), 0.0) FROM PoinHistory ph")
    Double getTotalPoin();

    @Query("SELECT SUM(ph.poin) FROM PoinHistory ph WHERE FUNCTION('MONTH', ph.tanggal) = :month")
    Integer findTotalPoinByMonth(@Param("month") int month);

    @Query("SELECT ph FROM PoinHistory ph JOIN ph.teknisi t " +
            "WHERE FUNCTION('DATE_FORMAT', ph.tanggal, '%Y-%m') = :month " +
            "ORDER BY ph.poin DESC")
    List<PoinHistory> findPoinByMonth(@Param("month") String month);

    @Query("SELECT SUM(ph.poin) FROM PoinHistory ph WHERE ph.teknisi.id = :idTeknisi AND YEAR(ph.tanggal) = :year AND MONTH(ph.tanggal) = :month")
    Double findTotalPoinByMonthAndYear(@Param("idTeknisi") Long idTeknisi, @Param("year") int year, @Param("month") int month);
    @Query("SELECT SUM(ph.nominal) FROM PoinHistory ph " +
            "JOIN ph.teknisi t " +
            "WHERE t.bagian = :bagian AND t.status = 'Y' AND DATE(ph.tanggal) = :tanggal")
    Integer findNominalByBagianAndTanggal(@Param("bagian") String bagian, @Param("tanggal") Date tanggal);
}
