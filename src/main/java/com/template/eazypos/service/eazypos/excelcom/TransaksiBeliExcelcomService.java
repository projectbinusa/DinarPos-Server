package com.template.eazypos.service.eazypos.excelcom;

import com.template.eazypos.dto.BarangTransaksiDTO;
import com.template.eazypos.dto.TransaksiBeliDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Barang;
import com.template.eazypos.model.BarangTransaksiBeli;
import com.template.eazypos.model.Suplier;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.repository.BarangRepository;
import com.template.eazypos.repository.BarangTransaksiBeliRepository;
import com.template.eazypos.repository.SuplierRepository;
import com.template.eazypos.repository.TransaksiBeliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        String not = getNoNotaTransaksi();

        Suplier suplier = suplierRepository.findById(transaksiBeliDTO.getIdSuplier()).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
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
        transaksiBeli.setKekurangan(transaksiBeliDTO.getKekurangan());
        transaksiBeli.setNamaSuplier(suplier.getNamaSuplier());
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
                barangTransaksiBeli.setUnit(barang.getUnit());
                barangTransaksiBeli.setQty(barangDTO.getQty());
                barangTransaksiBeli.setDiskon(barangDTO.getDiskon());
                barangTransaksiBeli.setHargaBrng(barangDTO.getHargaBrng());
                barangTransaksiBeli.setTotalHarga(barangDTO.getTotalHarga());
                barangTransaksiBeli.setHemat(String.valueOf(barangDTO.getHemat()));
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

    public String getNoNotaTransaksi() {
        try {
            String kd = "";
            LocalDateTime now = LocalDateTime.now();
            int dateNow = Integer.parseInt(now.format(DateTimeFormatter.ofPattern("MM")));

            Integer kdMax = transaksiBeliRepository.findMaxKd();
            int tmp = (kdMax != null) ? kdMax + 1 : 1;

            String fullLastDate = transaksiBeliRepository.findLastDate();
            int lastDate = Integer.parseInt(fullLastDate.substring(5, 7)); // Extract month from the full date

            // Check if it's a new month
            if (lastDate != dateNow) {
                tmp = 1;
            }

            kd = String.format("%04d", tmp);

            // Format nota
            String nomor = now.format(DateTimeFormatter.ofPattern("MMyy")); // Format bulan dan tahun
            String nota = nomor + "-PST-PJN-" + kd;

            return nota;
        } catch (Exception e) {
            e.printStackTrace(); // Cetak stack trace untuk mengetahui sumber NullPointerException
            return null; // Mengembalikan null jika terjadi kesalahan
        }
    }

    public TransaksiBeli getById (Long id){
        return transaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }
    public List<BarangTransaksiBeli> getByIdTransaksi (Long idTransaksi){
        String status = "excelcom";
        return barangTransaksiBeliRepository.findBarangTransaksiDinarposByIdTransaksi(status , idTransaksi);
    }
    public List<TransaksiBeli> getExcelcomBYMonthAndYear(int bulan , int tahun){
        return transaksiBeliRepository.findTransaksiByMonthAndYear(bulan,tahun , "excelcom");
    }

}
