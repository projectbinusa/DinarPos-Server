package com.template.eazypos.service.itc;

import com.template.eazypos.dto.BonBarangDTO;
import com.template.eazypos.dto.UpdateBonBarangDTO;
import com.template.eazypos.dto.UpdateDataBonBarangDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.BonBarang;
import com.template.eazypos.repository.BarangRepository;
import com.template.eazypos.repository.BonBarangRepository;
import com.template.eazypos.repository.ServiceRepository;
import com.template.eazypos.repository.TeknisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BonBarangService {

    @Autowired
    BonBarangRepository bonBarangRepository;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    TeknisiRepository teknisiRepository;

    @Autowired
    BarangRepository barangRepository;


    public BonBarang add(BonBarangDTO bonBarangDTO){
        BonBarang bonBarang = new BonBarang();
        bonBarang.setTgl_ambil(bonBarangDTO.getTanggal_ambil());
        bonBarang.setTeknisi(teknisiRepository.findById(bonBarangDTO.getId_teknisi()).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found")));
        bonBarang.setServiceBarang(serviceRepository.findByIdTT(bonBarangDTO.getId_tt()).orElseThrow(() -> new NotFoundException("Id Service Not Found")));
        bonBarang.setBarang(barangRepository.findByBarcode(bonBarangDTO.getBarcode_brg()));
        return bonBarangRepository.save(bonBarang);
    }
    public BonBarang getById(Long id){
        return bonBarangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }

    public BonBarang edit(UpdateBonBarangDTO bonBarangDTO , Long id){
        BonBarang bonBarang = bonBarangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        bonBarang.setTgl_kembalikan(bonBarangDTO.getTgl_kembali());
        return bonBarangRepository.save(bonBarang);
    }

    public List<BonBarang> getAll(){
        return bonBarangRepository.findAll();
    }
    public Map<String, Boolean> delete(Long id ) {
        try {
            bonBarangRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    public BonBarang updateBonBarang(UpdateDataBonBarangDTO dataBonBarangDTO, Long id) {
        BonBarang bonBarang = bonBarangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        bonBarang.setTgl_ambil(dataBonBarangDTO.getTgl_ambil());
        return bonBarangRepository.save(bonBarang);
    }
}
