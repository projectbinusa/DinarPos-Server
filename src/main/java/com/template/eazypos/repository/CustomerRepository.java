package com.template.eazypos.repository;

import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Pengguna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer , Long> {
    @Query(value = "SELECT * FROM customer WHERE email = :email OR telp = :telp ", nativeQuery = true)
    Optional<Customer> findByEmailOrTelp(String email , String telp);
}
