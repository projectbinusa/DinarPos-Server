package com.template.eazypos.service.itc;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.repository.KunjunganRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KunjunganService {
    @Autowired
    private KunjunganRepository kunjunganRepository;

    public List<Kunjungan> getAll(){
        return kunjunganRepository.findAll();
    }
    public Kunjungan getById(Long id) {
        return kunjunganRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
}
