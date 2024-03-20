package com.template.eazypos.repository;

import com.template.eazypos.model.Customer;
import com.template.eazypos.model.CustomerCP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerCPRepository extends JpaRepository<CustomerCP , Long> {
    @Query(value = "SELECT * FROM cp WHERE email = :email OR no_hp = :telp ", nativeQuery = true)
    Optional<CustomerCP> findByEmailOrTelp(String email , String telp);
}
