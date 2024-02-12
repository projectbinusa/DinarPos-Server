package com.template.eazypos.repository;

import com.template.eazypos.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status , Long> {
}
