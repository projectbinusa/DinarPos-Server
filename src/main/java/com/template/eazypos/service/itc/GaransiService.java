package com.template.eazypos.service.itc;

import com.template.eazypos.dto.GaransiDTO;
import com.template.eazypos.dto.TglJadiGaransiDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Garansi;
import com.template.eazypos.repository.GaransiRepository;
import com.template.eazypos.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GaransiService {
    @Autowired
    private GaransiRepository garansiRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    // Add tambah input
    public Garansi add(GaransiDTO garansiDTO){
        Garansi garansi = new Garansi();
        garansi.setNamaBrg(garansiDTO.getNamaBrg());
        garansi.setMerek(garansiDTO.getMerek());
        garansi.setKerusakan(garansiDTO.getKerusakan());
        garansi.setMasukKe(garansiDTO.getMasukKe());
        garansi.setTanggalMasuk(garansiDTO.getTanggalMasuk());
        garansi.setServiceBarang(serviceRepository.findByIdTT(garansiDTO.getId_tt()).orElseThrow(() -> new NotFoundException("Id Garansi Not Found")));
        return garansiRepository.save(garansi);
    }

    public Garansi updateTglJadi(TglJadiGaransiDTO tglJadiGaransiDTO , Long id){
        Garansi garansi = garansiRepository.findById(id).orElseThrow(() -> new NotFoundException( "Id Not Found"));
        garansi.setTanggalJadi(tglJadiGaransiDTO.getTgl_jadi());
        return garansiRepository.save(garansi);
    }

    // get By Id
    public Garansi getById(Long id){
        return garansiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }

    // update input data
    public Garansi edit(GaransiDTO garansiDTO , Long id){
        Garansi garansi = garansiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        garansi.setServiceBarang(serviceRepository.findByIdTT(garansiDTO.getId_tt()).orElseThrow(() -> new NotFoundException("Id Service Not Found")));
        garansi.setNamaBrg(garansiDTO.getNamaBrg());
        garansi.setMerek(garansiDTO.getMerek());
        garansi.setKerusakan(garansiDTO.getKerusakan());
        garansi.setMasukKe(garansiDTO.getMasukKe());
        garansi.setTanggalMasuk(garansiDTO.getTanggalMasuk());
        return garansiRepository.save(garansi);
    }

    // getAll data
    public List<Garansi> getAll(){
        return garansiRepository.findAll();
    }

    // delete input data
    public Map<String, Boolean> delete(Long id){
        try {
            garansiRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Delete", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
