package com.template.eazypos.repository;

import com.template.eazypos.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service , Long> {
}
