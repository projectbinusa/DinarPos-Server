package com.template.eazypos.repository;

import com.template.eazypos.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DealRepository extends JpaRepository<Deal,Long> {
    @Query(value = "SELECT d.* FROM deal d JOIN kunjungan k ON d.id_report = k.id JOIN customer c ON k.id_customer = :id", nativeQuery = true)
    List<Deal> findByCustomerId(Long id);

    @Query(value = "SELECT d.* " +
            "FROM deal d " +
            "JOIN kunjungan k ON d.id_report = k.id_report " +
            "JOIN tabel_salesman s ON k.id_salesman = s.id_salesman " +
            "WHERE s.id_salesman = :idSalesman",
            nativeQuery = true)
    List<Deal> findDealsBySalesmanId(@Param("idSalesman") Long idSalesman);


}
