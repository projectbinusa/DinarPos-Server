package com.template.eazypos.repository;

import com.template.eazypos.model.Customer;
import com.template.eazypos.model.CustomerCP;
import com.template.eazypos.model.Take;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CustomerCPRepository extends JpaRepository<CustomerCP , Long> {
    @Query(value = "SELECT * FROM cp WHERE email = :email OR no_hp = :telp ", nativeQuery = true)
    Optional<CustomerCP> findByEmailOrTelp(String email , String telp);

    @Query(value = "SELECT * FROM cp WHERE id_customer = :id" , nativeQuery = true)
    Optional<CustomerCP> findByIdCustomer (Long id);

    @Query(value = "SELECT * FROM cp WHERE id_customer = :id" , nativeQuery = true)
    Page<CustomerCP> findByIdCustomer (Long id , Pageable pageable);


}
