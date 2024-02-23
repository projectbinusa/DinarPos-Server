package com.template.eazypos.service.eazypos.dinarpos;

import com.template.eazypos.dto.BarangTransaksiDTO;
import com.template.eazypos.dto.TransaksiBeliDTO;
import com.template.eazypos.model.Barang;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.repository.BarangRepository;
import com.template.eazypos.repository.SuplierRepository;
import com.template.eazypos.repository.TransaksiBeliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransaksiBeliDinarposService {
    @Autowired
    private TransaksiBeliRepository transaksiBeliRepository;

    @Autowired
    private BarangRepository barangRepository;

    @Autowired
    private SuplierRepository suplierRepository;

    public TransaksiBeli addTransaksiBeli(TransaksiBeliDTO transaksiBeliDTO) {
        Date now = new Date();
        String not = generateNotaNumber(); // method generateNotaNumber() menghasilkan nomor nota baru

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
        transaksiBeli.setStatus("dinarpos");

        TransaksiBeli savedTransaksiBeli = transaksiBeliRepository.save(transaksiBeli);

        List<BarangTransaksiDTO> listProduk = transaksiBeliDTO.getProduk();
        for (BarangTransaksiDTO barangDTO : listProduk) {
            Barang barang = barangRepository.findByBarcode(barangDTO.getBarcodeBarang());
            if (barang != null) {
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
}
