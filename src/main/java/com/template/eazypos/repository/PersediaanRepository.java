package com.template.eazypos.repository;

import com.template.eazypos.model.Persediaan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface PersediaanRepository extends JpaRepository<Persediaan, Long> {
    @Query("SELECT p FROM Persediaan p WHERE p.date = :date")
    Optional<Persediaan> findByDate(@Param("date") Date date);

    @Query("SELECT p FROM Persediaan p WHERE p.date < :date ORDER BY p.date DESC")
    Optional<Persediaan> findLastBeforeDate(@Param("date") Date date);
}
