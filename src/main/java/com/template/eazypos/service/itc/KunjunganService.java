package com.template.eazypos.service.itc;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.repository.KunjunganRepository;
import com.template.eazypos.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KunjunganService {
    @Autowired
    private KunjunganRepository kunjunganRepository;
    @Autowired
    private SalesmanRepository salesmanRepository;

    public List<Kunjungan> getAll(){
        return kunjunganRepository.findAll();
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

    public List<Kunjungan> getByCustomer(Long id){
        return kunjunganRepository.findByIdCustomer(id);
    }
    public List<Kunjungan> getByBulan(int bulan){
        return kunjunganRepository.findByBulan(bulan);
    }
    public Kunjungan getById(Long id) {
        return kunjunganRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            kunjunganRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }

}
