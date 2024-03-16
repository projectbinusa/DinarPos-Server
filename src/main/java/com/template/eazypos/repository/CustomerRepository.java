package com.template.eazypos.repository;

import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Pengguna;
import com.template.eazypos.model.Suplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer , Long> {
    @Query(value = "SELECT * FROM customer WHERE email = :email OR telp = :telp ", nativeQuery = true)
    Optional<Customer> findByEmailOrTelp(String email , String telp);

    @Query("SELECT s FROM Customer s WHERE LOWER(s.nama_customer) LIKE LOWER(concat('%', :keyword, '%'))")
    Page<Customer> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
