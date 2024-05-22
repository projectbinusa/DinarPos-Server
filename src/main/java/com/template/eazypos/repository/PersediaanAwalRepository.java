package com.template.eazypos.repository;

import com.template.eazypos.model.PersediaanAwal;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

public interface PersediaanAwalRepository extends JpaRepository<PersediaanAwal , Long> {
    List<PersediaanAwal> findByTanggal(Date date);
}
