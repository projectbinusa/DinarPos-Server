package com.template.eazypos.repository;

import com.template.eazypos.model.Salesman;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesmanRepository extends JpaRepository<Salesman , Long> {
}
