package com.template.eazypos.service.eazypos.dinarpos;

import com.template.eazypos.dto.BarangTransaksiDTO;
import com.template.eazypos.dto.TransaksiPenjualanDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.*;
import com.template.eazypos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransakiIndentDinarposService {
    @Autowired
    private TransaksiIndentRepository transaksiRepository;

    @Autowired
    private BarangTransaksiIndentRepository barangTransaksiRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalesmanRepository markettingRepository;
    @Autowired
    private BarangRepository barangRepository;

    public TransaksiIndent addTransaksi(TransaksiPenjualanDTO transaksiDTO) {
        Date now = new Date();
        String not = getNoNotaTransaksi() ; // method generateNotaNumber() menghasilkan nomor nota baru
        Customer customer = customerRepository.findById(transaksiDTO.getIdCustomer()).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        Salesman marketting = markettingRepository.findById(transaksiDTO.getIdSalesman()).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));


        TransaksiIndent transaksi = new TransaksiIndent();
        transaksi.setTotalBelanja(String.valueOf(transaksiDTO.getTotalBelanja()));
        transaksi.setPembayaran(String.valueOf(transaksiDTO.getPembayaran()));
        transaksi.setPotongan(String.valueOf(transaksiDTO.getPotongan()));
        transaksi.setDiskon(String.valueOf(transaksiDTO.getDiskon()));
        transaksi.setTotalBayarBarang(String.valueOf(transaksiDTO.getTotalBayarBarang()));
        transaksi.setCustomer(customerRepository.findById(transaksiDTO.getIdCustomer()).orElse(null));
        transaksi.setSalesman(markettingRepository.findById(transaksiDTO.getIdSalesman()).orElse(null));
        transaksi.setStatus("dinarpos");
        transaksi.setHari7(1);
        transaksi.setHari30(1);
        transaksi.setHari90(1);
        transaksi.setHari120(1);
        transaksi.setNota("1");
        transaksi.setHari365(1);
        transaksi.setKekurangan(transaksiDTO.getKekurangan());

        transaksi.setNoFaktur(not);
        transaksi.setKeterangan(transaksiDTO.getKeterangan());
        transaksi.setCashKredit(transaksiDTO.getCashKredit());
        transaksi.setSisa(String.valueOf(transaksiDTO.getSisa()));
        transaksi.setTtlBayarHemat(String.valueOf(transaksiDTO.getTtlBayarHemat()));
        transaksi.setTanggal(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

// Tambahkan 7 hari
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date tanggalNotif7 = calendar.getTime();
        transaksi.setTanggalNotif7(tanggalNotif7);

// Tambahkan 30 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.MONTH, 1);
        Date tanggalNotif30 = calendar.getTime();
        transaksi.setTanggalNotif30(tanggalNotif30);

// Tambahkan 90 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.MONTH, 3);
        Date tanggalNotif90 = calendar.getTime();
        transaksi.setTanggalNotif90(tanggalNotif90);

// Tambahkan 120 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.MONTH, 4);
        Date tanggalNotif120 = calendar.getTime();
        transaksi.setTanggalNotif120(tanggalNotif120);

// Tambahkan 365 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.YEAR, 1);
        Date tanggalNotif365 = calendar.getTime();
        transaksi.setTanggalNotif365(tanggalNotif365);
        transaksi.setDelFlag(1);

        TransaksiIndent savedTransaksi = transaksiRepository.save(transaksi);

        List<BarangTransaksiDTO> listProduk = transaksiDTO.getProduk();
        for (BarangTransaksiDTO barangDTO : listProduk) {
            BarangTransaksiIndent barangTransaksi = new BarangTransaksiIndent();
            barangTransaksi.setTransaksiIndent(transaksiRepository.findById(savedTransaksi.getId()).get());
            barangTransaksi.setBarcodeBarang(barangDTO.getBarcodeBarang());
            barangTransaksi.setQty(barangDTO.getQty());
            barangTransaksi.setDiskon(barangDTO.getDiskon());
            barangTransaksi.setHargaBrng(barangDTO.getHargaBrng());
            barangTransaksi.setTotalHarga(barangDTO.getTotalHarga());
            barangTransaksi.setTotalHargaBarang(barangDTO.getTotalHargaBarang());
            barangTransaksi.setTanggal(now);
            barangTransaksi.setHari7(1);
            barangTransaksi.setHari30(1);
            barangTransaksi.setHari90(1);
            barangTransaksi.setHari120(1);
            barangTransaksi.setHari367(1);
            barangTransaksi.setDelFlag(1);
            barangTransaksi.setHemat(String.valueOf(barangDTO.getHemat()));
            barangTransaksi.setStatus("dinarpos");
            barangTransaksiRepository.save(barangTransaksi);

        }
        return savedTransaksi;
    }
    public String getNoNotaTransaksi() {
        try {
            String kd = "";
            LocalDateTime now = LocalDateTime.now();
            int dateNow = Integer.parseInt(now.format(DateTimeFormatter.ofPattern("MM")));

            Integer kdMax = transaksiRepository.findMaxKd();
            int tmp = (kdMax != null) ? kdMax + 1 : 1;

            String fullLastDate = transaksiRepository.findLastDate();
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
            LocalDateTime now = LocalDateTime.now();
            String nomor = now.format(DateTimeFormatter.ofPattern("MMyy")); // Format bulan dan tahun
            String nota = nomor + "-PST-PJN-0001" ;
            e.printStackTrace(); // Cetak stack trace untuk mengetahui sumber NullPointerException
            return nota;
        }
    }
}
