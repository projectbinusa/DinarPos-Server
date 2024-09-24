package com.template.eazypos.repository;

import com.template.eazypos.model.Finish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FinishRepository extends JpaRepository<Finish,Long> {
    @Query(value = "SELECT f.* FROM finish f JOIN kunjungan k ON f.id_report = k.id JOIN customer c ON k.id_customer = :id", nativeQuery = true)
    List<Finish> findByCustomerId(Long id);

    @Query(value = "SELECT f.* " +
            "FROM finish f " +
            "JOIN kunjungan k ON f.id_report = k.id_report " +
            "JOIN salesman s ON k.id_salesman = s.id_salesman " +
            "WHERE s.id_salesman = :idSalesman",
            nativeQuery = true)
    List<Finish> findAllFinishBySalesmanId(@Param("idSalesman") Long idSalesman);

}
