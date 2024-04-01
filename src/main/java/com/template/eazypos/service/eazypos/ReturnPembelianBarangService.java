package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.*;
import com.template.eazypos.repository.BarangRepository;
import com.template.eazypos.repository.BarangTransaksiBeliRepository;
import com.template.eazypos.repository.TransaksiBeliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ReturnPembelianBarangService {
    @Autowired
    private BarangTransaksiBeliRepository barangTransaksiBeliRepository;

    @Autowired
    private TransaksiBeliRepository transaksiBeliRepository;

    @Autowired
    private BarangRepository barangRepository;


    public List<BarangTransaksiBeli> getAllExcelcom() {
        return barangTransaksiBeliRepository.findBarangTransaksiBeliExcelcom();
    }
    public List<BarangTransaksiBeli> getAllBarangReturn(Long idTransaksi) {
        return barangTransaksiBeliRepository.findBarangTransaksiReturnByIdTransaksi(idTransaksi);
    }
    public List<BarangTransaksiBeli> getAllDinarpos() {
        return barangTransaksiBeliRepository.findBarangTransaksiBeliDinarpos();
    }


    public BarangTransaksiBeli get(Long id) {
        return barangTransaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }
    public BarangTransaksiBeli retur(Long id){
        Optional<BarangTransaksiBeli> barangTransaksiOptional = barangTransaksiBeliRepository.findById(id);
        if (barangTransaksiOptional.isPresent()) {
            BarangTransaksiBeli barangTransaksi = barangTransaksiOptional.get();
            TransaksiBeli transaksi = transaksiBeliRepository.findById(barangTransaksi.getTransaksiBeli().getIdTransaksiBeli())
                    .orElseThrow(() -> new NotFoundException("Transaksi tidak ditemukan"));

            // Mengembalikan stok barang
            // Ambil jumlah barang yang diretur
            int jumlahRetur = barangTransaksi.getQty();
            Barang barang = barangRepository.findByBarcode(barangTransaksi.getBarcodeBarang());
            barang.setJumlahStok(barang.getJumlahStok() - jumlahRetur);
            // Simpan perubahan stok barang
            barangRepository.save(barang);

            // Menghapus barang transaksi
            barangTransaksi.setDelFlag(0);
            barangTransaksi = barangTransaksiBeliRepository.save(barangTransaksi);

            // Periksa apakah semua barang dalam transaksi telah diretur
            List<BarangTransaksiBeli> allBarangTransaksiBeli = barangTransaksiBeliRepository.findBarangTransaksiByIdTransaksi(transaksi.getIdTransaksiBeli());
            boolean allBarangRetur = allBarangTransaksiBeli.stream().allMatch(bt -> bt.getDelFlag() == 0);
            if (allBarangRetur) {
                // Jika semua barang telah diretur, atur delFlag transaksi menjadi 0
                transaksi.setDelFlag(0);
                transaksiBeliRepository.save(transaksi);
            }

            return barangTransaksi;
        } else {
            throw new NotFoundException("Barang transaksi tidak ditemukan");
        }
    }

    public Map<String, Boolean> delete(Long id ) {
        try {
            barangTransaksiBeliRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
