package com.template.eazypos.service.eazypos.excelcom;

import com.template.eazypos.dto.BarangTransaksiDTO;
import com.template.eazypos.dto.TransaksiBeliDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Barang;
import com.template.eazypos.model.BarangTransaksiBeli;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.repository.BarangRepository;
import com.template.eazypos.repository.BarangTransaksiBeliRepository;
import com.template.eazypos.repository.SuplierRepository;
import com.template.eazypos.repository.TransaksiBeliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransaksiBeliExcelcomService {
    @Autowired
    private TransaksiBeliRepository transaksiBeliRepository;

    @Autowired
    private BarangTransaksiBeliRepository barangTransaksiBeliRepository;

    @Autowired
    private BarangRepository barangRepository;

    @Autowired
    private SuplierRepository suplierRepository;

    public TransaksiBeli addTransaksiBeli(TransaksiBeliDTO transaksiBeliDTO) {
        Date now = new Date();
        String not = generateNotaNumber();

        TransaksiBeli transaksiBeli = new TransaksiBeli();
        transaksiBeli.setTotalBelanja(transaksiBeliDTO.getTotalBelanja());
        transaksiBeli.setPembayaran(transaksiBeliDTO.getPembayaran());
        transaksiBeli.setPotongan(transaksiBeliDTO.getPotongan());
        transaksiBeli.setDiskon(transaksiBeliDTO.getDiskon());
        transaksiBeli.setTotalBayarBarang(transaksiBeliDTO.getTotalBayarBarang());
        transaksiBeli.setSuplier(suplierRepository.findById(transaksiBeliDTO.getIdSuplier()).orElse(null));
        transaksiBeli.setNoFaktur(not);
        transaksiBeli.setKeterangan(transaksiBeliDTO.getKeterangan());
        transaksiBeli.setCashCredit(transaksiBeliDTO.getCashCredit());
        transaksiBeli.setSisa(transaksiBeliDTO.getSisa());
        transaksiBeli.setTtlBayarHemat(transaksiBeliDTO.getTtlBayarHemat());
        transaksiBeli.setTanggal(now);
        transaksiBeli.setDelFlag(1);
        transaksiBeli.setStatus("excelcom");

        TransaksiBeli savedTransaksiBeli = transaksiBeliRepository.save(transaksiBeli);

        // Simpan detail barang transaksi beli
        List<BarangTransaksiDTO> listProduk = transaksiBeliDTO.getProduk();
        for (BarangTransaksiDTO barangDTO : listProduk) {
            Barang barang = barangRepository.findByBarcode(barangDTO.getBarcodeBarang());
            if (barang != null) {
                BarangTransaksiBeli barangTransaksiBeli = new BarangTransaksiBeli();
                barangTransaksiBeli.setTransaksiBeli(savedTransaksiBeli);
                barangTransaksiBeli.setBarcodeBarang(barangDTO.getBarcodeBarang());
                barangTransaksiBeli.setNamaBarang(barang.getNamaBarang());
                barangTransaksiBeli.setQty(barangDTO.getQty());
                barangTransaksiBeli.setDiskon(barangDTO.getDiskon());
                barangTransaksiBeli.setHargaBrng(barangDTO.getHargaBrng());
                barangTransaksiBeli.setTotalHarga(barangDTO.getTotalHarga());
                barangTransaksiBeli.setTotalHargaBarang(barangDTO.getTotalHargaBarang());
                barangTransaksiBeli.setTanggal(now);
                barangTransaksiBeli.setDelFlag(1);
                barangTransaksiBeli.setStatus("excelcom");

                barangTransaksiBeliRepository.save(barangTransaksiBeli);

                // Update stok barang
                barang.setJumlahStok(barang.getJumlahStok() + barangDTO.getQty());
                barangRepository.save(barang);
            } else {
                // Barang tidak ditemukan, lakukan tindakan sesuai kebutuhan
                // Contoh: throw exception, kirim notifikasi ke admin, dll.
            }
        }
        return savedTransaksiBeli;
    }

    private String generateNotaNumber() {
        // Dapatkan tanggal saat ini
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR) % 100;

        // Dapatkan nomor nota terakhir dari database untuk bulan dan tahun saat ini
        String lastNota = transaksiBeliRepository.findLastNotaByMonthAndYear(month, year);

        // Jika nomor nota terakhir tidak ditemukan
        if (lastNota == null) {
            return String.format("%02d%02d-PST-PJN-0001", month, year);
        } else {
            // Periksa apakah nomor nota terakhir berasal dari bulan dan tahun yang sama
            String[] parts = lastNota.split("-");
            int lastMonth = Integer.parseInt(parts[0]);
            int lastYear = Integer.parseInt(parts[1]);
            int lastNumber = Integer.parseInt(parts[3]);

            if (lastMonth == month && lastYear == year) {
                lastNumber++; // Tambahkan satu pada angka terakhir
                return String.format("%02d%02d-PST-PJN-%04d", month, year, lastNumber);
            } else {
                return String.format("%02d%02d-PST-PJN-0001", month, year); // Mulai nomor nota baru
            }
        }
    }
    public TransaksiBeli getById (Long id){
        return transaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }
    public List<BarangTransaksiBeli> getByIdTransaksi (Long idTransaksi){
        String status = "excelcom";
        return barangTransaksiBeliRepository.findBarangTransaksiDinarposByIdTransaksi(status , idTransaksi);
    }
}
