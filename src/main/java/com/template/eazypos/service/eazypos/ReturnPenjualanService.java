package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Barang;
import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.BarangRepository;
import com.template.eazypos.repository.BarangTransaksiRepository;
import com.template.eazypos.repository.TransaksiRepository;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReturnPenjualanService {
    @Autowired
    private TransaksiRepository transaksiRepository;
    @Autowired
    private BarangTransaksiRepository barangTransaksiRepository;

    @Autowired
    private BarangRepository barangRepository;

    public List<Transaksi> getAllExcelcom() {
        return transaksiRepository.findTransaksiExcelcom();
    }
    public List<Transaksi> getAllDinarpos() {
        return transaksiRepository.findTransaksiDinarpos();
    }

    public Transaksi get(Long id) {
        return transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }
    public Transaksi put(Long id) {
        Transaksi transaksi = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id transaksi not found"));
        transaksi.setDelFlag(0);


        List<BarangTransaksi> barangTransaksiList = barangTransaksiRepository.findBarangTransaksiByIdTransaksi2(id);

        // Perbarui stok barang kembali ke nilai semula
        for (BarangTransaksi barangTransaksi : barangTransaksiList) {
            Barang barang = barangRepository.findByBarcode(barangTransaksi.getBarcodeBarang());
            barang.setJumlahStok(barang.getJumlahStok() + barangTransaksi.getQty());
            barangRepository.save(barang);
            barangTransaksi.setDelFlag(0);
            barangTransaksiRepository.save(barangTransaksi);
        }

        return transaksiRepository.save(transaksi);
    }

    public Map<String, Boolean> delete(Long id ) {
        try {
            transaksiRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
