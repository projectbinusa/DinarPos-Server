package com.template.eazypos.repository;

import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Salesman;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesmanRepository extends JpaRepository<Salesman , Long> {
    @Query(value = "SELECT * FROM tabel_salesman  WHERE LOWER(nama_salesman) LIKE LOWER(concat('%', :keyword, '%'))" , nativeQuery = true)
    Page<Salesman> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM tabel_salesman WHERE del_flag = 1 ", nativeQuery = true)
    List<Salesman> findAllSalesmen();
}
