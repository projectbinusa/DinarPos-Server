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

    // Mengambil daftar barang transaksi pembelian Excelcom
    public List<BarangTransaksiBeli> getAllExcelcom() {
        return barangTransaksiBeliRepository.findBarangTransaksiBeliExcelcom();
    }

    // Mengambil daftar barang yang diretur berdasarkan ID transaksi
    public List<BarangTransaksiBeli> getAllBarangReturn(Long idTransaksi) {
        return barangTransaksiBeliRepository.findBarangTransaksiReturnByIdTransaksi(idTransaksi);
    }

    // Mengambil daftar barang transaksi pembelian Dinarpos
    public List<BarangTransaksiBeli> getAllDinarpos() {
        return barangTransaksiBeliRepository.findBarangTransaksiBeliDinarpos();
    }

    // Mengambil barang transaksi pembelian berdasarkan ID
    public BarangTransaksiBeli get(Long id) {
        return barangTransaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
    }

    // Melakukan proses retur barang dari transaksi pembelian
    public BarangTransaksiBeli retur(Long id) {
        Optional<BarangTransaksiBeli> barangTransaksiOptional = barangTransaksiBeliRepository.findById(id);
        if (barangTransaksiOptional.isPresent()) {
            BarangTransaksiBeli barangTransaksi = barangTransaksiOptional.get();

            // Mengambil transaksi pembelian terkait
            TransaksiBeli transaksi = transaksiBeliRepository.findById(barangTransaksi.getTransaksiBeli().getIdTransaksiBeli())
                    .orElseThrow(() -> new NotFoundException("Transaksi tidak ditemukan"));

            // Mengurangi stok barang yang diretur dari jumlah stok saat ini
            int jumlahRetur = barangTransaksi.getQty();
            Barang barang = barangRepository.findByBarcode(barangTransaksi.getBarcodeBarang());
            barang.setJumlahStok(barang.getJumlahStok() - jumlahRetur);
            barangRepository.save(barang);

            // Menghapus barang transaksi dari daftar pembelian
            barangTransaksi.setDelFlag(0);
            barangTransaksi = barangTransaksiBeliRepository.save(barangTransaksi);

            // Memeriksa apakah semua barang dalam transaksi telah diretur
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

    // Menghapus barang transaksi pembelian berdasarkan ID
    public Map<String, Boolean> delete(Long id) {
        try {
            BarangTransaksiBeli update = barangTransaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
            update.setDelFlag(0);
            barangTransaksiBeliRepository.save(update);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
}
