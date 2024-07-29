package com.template.eazypos.service.eazypos;

import com.template.eazypos.dto.StokMasukDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Barang;
import com.template.eazypos.model.StokMasuk;
import com.template.eazypos.repository.BarangRepository;
import com.template.eazypos.repository.StokMasukRepository;
import com.template.eazypos.repository.SuplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StokMasukService {
    @Autowired
    private StokMasukRepository stokMasukRepository;

    @Autowired
    private SuplierRepository suplierRepository;

    @Autowired
    private BarangRepository barangRepository;

    // Menambahkan data stok masuk baru
    public StokMasuk add(StokMasukDTO stokMasukDTO){
        Barang barang = barangRepository.findById(stokMasukDTO.getId_barang()).get();
        int jmlsebelum = barang.getJumlahStok();
        int jmlstok_masuk = stokMasukDTO.getJumlah_stok();
        StokMasuk add = new StokMasuk();
        Date now = new Date();
        add.setcDate(now);
        add.setKeteranganStokMasuk(stokMasukDTO.getKeterangan());
        add.setJumlahStok(String.valueOf(stokMasukDTO.getJumlah_stok()));
        add.setDelFlag(1);

        add.setBarang(barangRepository.findById(stokMasukDTO.getId_barang()).orElseThrow(() -> new NotFoundException("Id Barang tidak dinemukan")));
        add.setSuplier(suplierRepository.findById(stokMasukDTO.getId_suplier()).orElseThrow(() -> new NotFoundException("Id Suplier tidak dinemukan")));

      barang.setJumlahStok(jmlsebelum + jmlstok_masuk);
        barangRepository.save(barang);
        return stokMasukRepository.save(add);
    }

    // Mengambil data stok masuk berdasarkan ID
    public StokMasuk get(Long id) {
        return stokMasukRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
    }

    // Mengambil semua data stok masuk
    public List<StokMasuk> getAll(){
        return stokMasukRepository.findAll();
    }

    // Mengedit data stok masuk berdasarkan ID
    public StokMasuk edit(StokMasukDTO stokMasukDTO , Long id){
        StokMasuk update = stokMasukRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        Barang barang = barangRepository.findById(stokMasukDTO.getId_barang()).get();
        int jmlsebelum = barang.getJumlahStok();
        int jmlstok_masuk = stokMasukDTO.getJumlah_stok();
        update.setKeteranganStokMasuk(stokMasukDTO.getKeterangan());
        update.setJumlahStok(String.valueOf(stokMasukDTO.getJumlah_stok()));
        update.setBarang(barangRepository.findById(stokMasukDTO.getId_barang()).get());
        update.setSuplier(suplierRepository.findById(stokMasukDTO.getId_suplier()).get());

        barang.setJumlahStok(jmlsebelum + jmlstok_masuk);
        barangRepository.save(barang);
        return stokMasukRepository.save(update);
    }

    // Menghapus data stok masuk berdasarkan ID
    public Map<String, Boolean> delete(Long id ) {
        try {
            StokMasuk update = stokMasukRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
            update.setDelFlag(0);
            stokMasukRepository.save(update);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
