package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.*;
import com.template.eazypos.repository.BarangRepository;
import com.template.eazypos.repository.BarangTransaksiBeliRepository;
import com.template.eazypos.repository.StokMasukRepository;
import com.template.eazypos.repository.TransaksiBeliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReturnPembelianService {

    @Autowired
    private TransaksiBeliRepository transaksiBeliRepository;

    @Autowired
    private BarangTransaksiBeliRepository barangTransaksiBeliRepository;

    @Autowired
    private BarangRepository barangRepository;


    public List<TransaksiBeli> getAllExcelcom() {
        return transaksiBeliRepository.findTransaksiBeliExcelcom();
    }
    public List<TransaksiBeli> getAllDinarpos() {
        return transaksiBeliRepository.findTransaksiBeliDinarpos();
    }

    public TransaksiBeli get(Long id) {
        return transaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }
    public TransaksiBeli put(Long id) {
        TransaksiBeli transaksi = transaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id transaksi not found"));
        transaksi.setDelFlag(0);


        List<BarangTransaksiBeli> barangTransaksiList = barangTransaksiBeliRepository.findBarangTransaksiByIdTransaksi(id);

        // Perbarui stok barang kembali ke nilai semula
        for (BarangTransaksiBeli barangTransaksi : barangTransaksiList) {
            Barang barang = barangRepository.findByBarcode(barangTransaksi.getBarcodeBarang());
            barang.setJumlahStok(barang.getJumlahStok() - barangTransaksi.getQty());
            barangRepository.save(barang);
            barangTransaksi.setDelFlag(0);
            barangTransaksiBeliRepository.save(barangTransaksi);
        }

        return transaksiBeliRepository.save(transaksi);
    }

    public Map<String, Boolean> delete(Long id ) {
        try {
            transaksiBeliRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
