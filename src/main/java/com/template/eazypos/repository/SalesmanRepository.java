package com.template.eazypos.repository;

import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Salesman;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SalesmanRepository extends JpaRepository<Salesman , Long> {
    @Query("SELECT s FROM Salesman s WHERE LOWER(s.namaSalesman) LIKE LOWER(concat('%', :keyword, '%'))")
    Page<Salesman> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
