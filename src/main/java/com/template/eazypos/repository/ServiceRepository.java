package com.template.eazypos.repository;


import com.template.eazypos.model.ServiceBarang;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<ServiceBarang, Long> {
}
