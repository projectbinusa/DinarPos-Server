package com.template.eazypos.service.eazypos.dinarpos;

import com.template.eazypos.dto.BarangTransaksiDTO;
import com.template.eazypos.dto.TransaksiPenjualanDTO;
import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.BarangTransaksiRepository;
import com.template.eazypos.repository.CustomerRepository;
import com.template.eazypos.repository.SalesmanRepository;
import com.template.eazypos.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransaksiPenjualanDinarposService {
    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private BarangTransaksiRepository barangTransaksiRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalesmanRepository salesmanRepository;

    public Transaksi addTransaksi(TransaksiPenjualanDTO transaksiDTO) {
        Date now = new Date();
        String nomor = new SimpleDateFormat("MMyy").format(now);
        String not = nomor + "-PST-PJN-000" + generateNotaNumber(); // method generateNotaNumber() menghasilkan nomor nota baru

        Transaksi transaksi = new Transaksi();
        transaksi.setTotalBelanja(transaksiDTO.getTotalBelanja());
        transaksi.setPembayaran(transaksiDTO.getPembayaran());
        transaksi.setPotongan(transaksiDTO.getPotongan());
        transaksi.setDiskon(transaksiDTO.getDiskon());
        transaksi.setTotalBayarBarang(transaksiDTO.getTotalBayarBarang());
        transaksi.setCustomer(customerRepository.findById(transaksiDTO.getIdCustomer()).get());
        transaksi.setSalesman(salesmanRepository.findById(transaksiDTO.getIdSalesman()).get());
        transaksi.setNoFaktur(not);
        transaksi.setKeterangan(transaksiDTO.getKeterangan());
        transaksi.setCashKredit(transaksiDTO.getCashKredit());
        transaksi.setSisa(transaksiDTO.getSisa());
        transaksi.setTtlBayarHemat(transaksiDTO.getTtlBayarHemat());
        transaksi.setTanggal(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

// Tambahkan 7 hari
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        transaksi.setTanggalNotif7(calendar.getTime());

// Tambahkan 30 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.MONTH, 1);
        transaksi.setTanggalNotif30(calendar.getTime());

// Tambahkan 90 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.MONTH, 3);
        transaksi.setTanggalNotif90(calendar.getTime());

// Tambahkan 120 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.MONTH, 4);
        transaksi.setTanggalNotif120(calendar.getTime());

// Tambahkan 365 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.YEAR, 1);
        transaksi.setTanggalNotif365(calendar.getTime());
        transaksi.setDelFlag(1);
        transaksi.setStatus("dinarpos");

        Transaksi savedTransaksi = transaksiRepository.save(transaksi);

        List<BarangTransaksiDTO> listProduk = transaksiDTO.getProduk();
        for (BarangTransaksiDTO barangDTO : listProduk) {
            BarangTransaksi barangTransaksi = new BarangTransaksi();
            barangTransaksi.setTransaksi(transaksiRepository.findById(savedTransaksi.getIdTransaksi()).get());
            barangTransaksi.setBarcodeBarang(barangDTO.getBarcodeBarang());
            barangTransaksi.setQty(barangDTO.getQty());
            barangTransaksi.setDiskon(barangDTO.getDiskon());
            barangTransaksi.setHargaBrng(barangDTO.getHargaBrng());
            barangTransaksi.setTotalHarga(barangDTO.getTotalHarga());
            barangTransaksi.setTotalHargaBarang(barangDTO.getTotalHargaBarang());
            barangTransaksi.setTanggal(now);
            barangTransaksi.setStatus("excelcom");
            barangTransaksiRepository.save(barangTransaksi);

            // Lakukan update stok barang di sini
        }
        return savedTransaksi;
    }

    private String generateNotaNumber() {
        // Dapatkan tanggal saat ini
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1; // Ingat bahwa January dimulai dari 0
        int year = calendar.get(Calendar.YEAR) % 100; // Ambil dua digit terakhir tahun

        // Dapatkan nomor nota terakhir dari database untuk bulan dan tahun saat ini
        String lastNota = transaksiRepository.findLastNotaByMonthAndYear(month, year);

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
