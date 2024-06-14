package com.template.eazypos.service.eazypos;

import com.template.eazypos.dto.StokKeluarDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Barang;
import com.template.eazypos.model.StokKeluar;
import com.template.eazypos.repository.BarangRepository;
import com.template.eazypos.repository.StokKeluarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StokKeluarService {
    @Autowired
    private StokKeluarRepository stokKeluarRepository;

    @Autowired
    private BarangRepository barangRepository;

    // Menambahkan data stok keluar baru
    public StokKeluar add(StokKeluarDTO stokKeluarDTO) {
        Barang barang = barangRepository.findById(stokKeluarDTO.getId_barang()).get();
        int jmlsebelum = barang.getJumlahStok();
        int jmlstok_keluar = stokKeluarDTO.getJumlah_stok();
        StokKeluar add = new StokKeluar();
        add.setKeteranganStokKeluar(stokKeluarDTO.getKeterangan());
        add.setJumlahStok(String.valueOf(stokKeluarDTO.getJumlah_stok()));
        add.setDelFlag(1);
        add.setBarang(barangRepository.findById(stokKeluarDTO.getId_barang()).get());

       barang.setJumlahStok(jmlsebelum - jmlstok_keluar);
        barangRepository.save(barang);
        return stokKeluarRepository.save(add);
    }

    // Mengambil data stok keluar berdasarkan ID
    public StokKeluar get(Long id) {
        return stokKeluarRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
    }

    // Mengambil semua data stok keluar
    public List<StokKeluar> getAll() {
        return stokKeluarRepository.findAll();
    }

    // Mengedit data stok keluar berdasarkan ID
    public StokKeluar edit(StokKeluarDTO stokKeluarDTO, Long id) {
        Barang barang = barangRepository.findById(stokKeluarDTO.getId_barang()).get();
        int jmlsebelum = barang.getJumlahStok();
        int jmlstok_keluar = stokKeluarDTO.getJumlah_stok();
        StokKeluar update = stokKeluarRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setKeteranganStokKeluar(stokKeluarDTO.getKeterangan());
        update.setJumlahStok(String.valueOf(stokKeluarDTO.getJumlah_stok()));
        update.setBarang(barangRepository.findById(stokKeluarDTO.getId_barang()).get());

        barang.setJumlahStok(jmlsebelum - jmlstok_keluar);
        barangRepository.save(barang);
        return stokKeluarRepository.save(update);
    }

    // Menghapus data stok keluar berdasarkan ID
    public Map<String, Boolean> delete(Long id) {
        try {
            stokKeluarRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
