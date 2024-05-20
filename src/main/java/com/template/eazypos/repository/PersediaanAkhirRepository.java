package com.template.eazypos.repository;

import com.template.eazypos.model.PersediaanAkhir;
import com.template.eazypos.model.PersediaanAwal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PersediaanAkhirRepository extends JpaRepository<PersediaanAkhir, Long> {

    List<PersediaanAkhir> findByTanggal(Date date);
}
