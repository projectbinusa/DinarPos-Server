package com.template.eazypos.repository;

import com.template.eazypos.model.Take;
import org.springframework.data.jpa.repository.JpaRepository;

import java.nio.file.LinkOption;

public interface TakeRepository extends JpaRepository<Take , LinkOption> {
}
