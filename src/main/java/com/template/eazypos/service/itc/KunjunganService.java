package com.template.eazypos.service.itc;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.repository.KunjunganRepository;
import com.template.eazypos.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KunjunganService {
    @Autowired
    private KunjunganRepository kunjunganRepository;
    @Autowired
    private SalesmanRepository salesmanRepository;

    public List<Kunjungan> getAll(){
        return kunjunganRepository.findAll();
    }
    public Kunjungan getById(Long id) {
        return kunjunganRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
    public List<Kunjungan> getDealPO(){
        return kunjunganRepository.findDealPo();
    }
    public List<Kunjungan> getDealFinish(){
        return kunjunganRepository.findDealFinish();
    }
    public List<Kunjungan> getDealPoAndSalesman(String nama){
        Salesman salesman = salesmanRepository.findByNama(nama).orElseThrow(() -> new NotFoundException("Nama Not Found"));
        return kunjunganRepository.findDealPoAndSalesman(salesman.getId());
    }
    public List<Kunjungan> getDealFinishAndSalesman(String nama){
        Salesman salesman = salesmanRepository.findByNama(nama).orElseThrow(() -> new NotFoundException("Nama Not Found"));
        return kunjunganRepository.findDealPoAndSalesman(salesman.getId());
    }
    public List<Kunjungan> getByDateBetween(Date tglAwal , Date tglAkhir){
        return kunjunganRepository.findByDate(tglAwal ,tglAkhir);
    }
    public List<Kunjungan> getByDate(Date date){
        return kunjunganRepository.findByTanggal(date);
    }

}
