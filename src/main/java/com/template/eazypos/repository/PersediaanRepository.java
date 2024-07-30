package com.template.eazypos.repository;

import com.template.eazypos.model.Persediaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PersediaanRepository extends JpaRepository<Persediaan, Long> {
    Optional<Persediaan> findByDate(Date date);
    @Query("SELECT p FROM Persediaan p WHERE p.date < :date ORDER BY p.date DESC")
    List<Persediaan> findLastBeforeDate(@Param("date") Date date);

    @Query(value = "SELECT * FROM persediaan WHERE del_flag = 1 ", nativeQuery = true)
    List<Persediaan> findAllPersediaan();

    @Query(value = "SELECT * FROM persediaan WHERE DATE(date) = DATE(:date)", nativeQuery = true)
    Optional<Persediaan> findByTanggal(@Param("date") Date date);

}
