package com.template.eazypos.repository;

import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Pengguna;
import com.template.eazypos.model.Suplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer , Long> {
    @Query(value = "SELECT * FROM customer WHERE email = :email OR telp = :telp ", nativeQuery = true)
    Optional<Customer> findByEmailOrTelp(String email , String telp);

    @Query(value = "SELECT * FROM customer WHERE telp = :telp ", nativeQuery = true)
    Optional<Customer> findByTelp( String telp);

    @Query(value = "SELECT * FROM customer  WHERE LOWER(nama_customer) LIKE LOWER(concat('%', :keyword, '%'))" , nativeQuery = true)
    Page<Customer> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "SELECT * FROM customer WHERE del_flag = 1 ", nativeQuery = true)
    List<Customer> findAllCustomer();

    @Query(value = "SELECT * FROM customer WHERE DATE(created_date) BETWEEN :startDate AND :endDate ORDER BY DATE(created_date) ASC", nativeQuery = true)
    List<Customer> findCustomersByTimestampBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
