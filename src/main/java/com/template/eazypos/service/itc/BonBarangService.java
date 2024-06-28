package com.template.eazypos.service.itc;

import com.template.eazypos.dto.BonBarangDTO;
import com.template.eazypos.dto.UpdateBonBarangDTO;
import com.template.eazypos.dto.UpdateDataBonBarangDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Barang;
import com.template.eazypos.model.BonBarang;
import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.model.Teknisi;
import com.template.eazypos.repository.BarangRepository;
import com.template.eazypos.repository.BonBarangRepository;
import com.template.eazypos.repository.ServiceRepository;
import com.template.eazypos.repository.TeknisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // Menambahkan data BonBarang baru berdasarkan BonBarangDTO
    public BonBarang add(BonBarangDTO bonBarangDTO){
        BonBarang bonBarang = new BonBarang();
        bonBarang.setTgl_ambil(bonBarangDTO.getTanggal_ambil());
        bonBarang.setTeknisi(teknisiRepository.findById(bonBarangDTO.getId_teknisi()).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found")));
        bonBarang.setServiceBarang(serviceRepository.findByIdTT(bonBarangDTO.getId_tt()).orElseThrow(() -> new NotFoundException("Id Service Not Found")));
        Barang barang = barangRepository.findByBarcode(bonBarangDTO.getBarcode_brg());
        if (barang == null) {
            throw new NotFoundException("Barcode Barang Not Found");
        }
        bonBarang.setBarang(barang);
        bonBarang.setStatus_barang(bonBarangDTO.getStatus_barang());
        return bonBarangRepository.save(bonBarang);
    }

    // Mengambil data BonBarang berdasarkan ID
    public BonBarang getById(Long id){
        return bonBarangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }

    // Mengedit data BonBarang berdasarkan UpdateBonBarangDTO
    public BonBarang edit(UpdateBonBarangDTO bonBarangDTO , Long id){
        BonBarang bonBarang = bonBarangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        bonBarang.setTgl_kembalikan(bonBarangDTO.getTgl_kembali());
        bonBarang.setStatus_service(bonBarangDTO.getStatus_service());
        return bonBarangRepository.save(bonBarang);
    }

    // Mengambil semua data BonBarang yang tersimpan
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

    // Mengupdate data BonBarang berdasarkan UpdateDataBonBarangDTO
    @Transactional
    public BonBarang updateBonBarang(UpdateDataBonBarangDTO dataBonBarangDTO, Long id) {
        BonBarang bonBarang = bonBarangRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id Not Found"));

        bonBarang.setTgl_ambil(dataBonBarangDTO.getTgl_ambil());

        if (dataBonBarangDTO.getId_teknisi() != null) {
            Teknisi teknisi = teknisiRepository.findById(dataBonBarangDTO.getId_teknisi())
                    .orElseThrow(() -> new NotFoundException("Id Teknisi Not Found"));
            bonBarang.setTeknisi(teknisi);
        }

        if (dataBonBarangDTO.getId_tt() != null) {
            ServiceBarang serviceBarang = serviceRepository.findByIdTT(dataBonBarangDTO.getId_tt())
                    .orElseThrow(() -> new NotFoundException("Id Service Not Found"));
            bonBarang.setServiceBarang(serviceBarang);
        }

        if (dataBonBarangDTO.getBarcode_brg() != null) {
            Barang barang = barangRepository.findByBarcode(dataBonBarangDTO.getBarcode_brg());
            if (barang == null) {
                throw new NotFoundException("Barcode Barang Not Found");
            }
            bonBarang.setBarang(barang);
        }

        return bonBarangRepository.save(bonBarang);
    }
}
