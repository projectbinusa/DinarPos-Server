package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Barang;
import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.StokMasuk;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.BarangRepository;
import com.template.eazypos.repository.BarangTransaksiRepository;
import com.template.eazypos.repository.StokMasukRepository;
import com.template.eazypos.repository.TransaksiRepository;
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

    @Autowired
    private StokMasukRepository stokMasukRepository;

    // Mengambil daftar transaksi penjualan untuk produk Excelcom
    public List<Transaksi> getAllExcelcom() {
        return transaksiRepository.findTransaksiExcelcom();
    }

    // Mengambil daftar transaksi penjualan untuk produk Dinarpos
    public List<Transaksi> getAllDinarpos() {
        return transaksiRepository.findTransaksiDinarpos();
    }

    // Mengambil transaksi penjualan berdasarkan ID
    public Transaksi get(Long id) {
        return transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
    }

    // Memproses pengembalian transaksi penjualan
    public Transaksi put(Long id) {
        Transaksi transaksi = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id transaksi not found"));
        transaksi.setDelFlag(0);


        List<BarangTransaksi> barangTransaksiList = barangTransaksiRepository.findBarangTransaksiByIdTransaksi2(id);

        // Perbarui stok barang kembali ke nilai semula
        for (BarangTransaksi barangTransaksi : barangTransaksiList) {
            Barang barang = barangRepository.findByBarcode(barangTransaksi.getBarcodeBarang());
            barang.setJumlahStok(barang.getJumlahStok() + barangTransaksi.getQty());
            StokMasuk stokMasuk = new StokMasuk();
            stokMasuk.setDelFlag(1);
            stokMasuk.setSuplier(null);
            stokMasuk.setBarang(barang);
            stokMasuk.setJumlahStok(String.valueOf(barangTransaksi.getQty()));
            stokMasuk.setKeteranganStokMasuk("Return Barang " + transaksi.getNoFaktur());
            stokMasukRepository.save(stokMasuk);
            barangRepository.save(barang);
            barangTransaksi.setDelFlag(0);
            barangTransaksiRepository.save(barangTransaksi);
        }

        return transaksiRepository.save(transaksi);
    }

    // Menghapus transaksi penjualan berdasarkan ID
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
