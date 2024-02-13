package com.template.eazypos.service.excelcom;

import com.template.eazypos.dto.ExcelcomRequest;
import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.CustomerRepository;
import com.template.eazypos.repository.SalesmanRepository;
import com.template.eazypos.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class TransaksiPenjualan {
    @Autowired
    private TransaksiRepository transaksiRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalesmanRepository salesmanRepository;

    public Transaksi addExcelcom(ExcelcomRequest request) {
        // Cek apakah nomor faktur sudah ada
        boolean fakturExists = transaksiRepository.existsByNoFaktur(request.getNoFaktur());
        if (fakturExists) {
            throw new BadRequestException("No Faktur sudah digunakan");
        }

        // Generate nomor faktur baru
        String nomor = String.valueOf(generateNomorFaktur());

        // Buat objek Transaksi baru
        Transaksi transaksi = new Transaksi();
        transaksi.setNoFaktur(nomor);
        transaksi.setTotalBelanja(request.getTotalBayar());
        transaksi.setPembayaran(request.getPembayaran());
        transaksi.setPotongan(request.getPotongan());
        transaksi.setDiskon(request.getDiskon());
        transaksi.setTotalBayarBarang(request.getTotalBayarBarang());
        transaksi.setCustomer(customerRepository.findById(request.getIdCustomer()).get());
        transaksi.setSalesman(salesmanRepository.findById(request.getIdSalesman()).get());
        transaksi.setKeterangan(request.getKeterangan());
        transaksi.setCashKredit(request.getCashKredit());
        transaksi.setSisa(request.getSisa());
        transaksi.setTtlBayarHemat(request.getTtlBayarHemat());
        Date currentDate = new Date();

        // Atur tanggal transaksi
        transaksi.setTanggal(currentDate);

        // Atur tanggal notifikasi
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // Tanggal notifikasi 7 hari mendatang
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        transaksi.setTanggalNotif7(calendar.getTime());

        // Tanggal notifikasi 30 hari mendatang
        calendar.setTime(currentDate); // Kembalikan tanggal ke saat ini
        calendar.add(Calendar.MONTH, 1);
        transaksi.setTanggalNotif30(calendar.getTime());

        // Tanggal notifikasi 90 hari mendatang
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, 3);
        transaksi.setTanggalNotif90(calendar.getTime());

        // Tanggal notifikasi 120 hari mendatang
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, 4);
        transaksi.setTanggalNotif120(calendar.getTime());

        // Tanggal notifikasi 365 hari mendatang
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, 1);
        transaksi.setTanggalNotif365(calendar.getTime());
        transaksi.setDelFlag(1);
        transaksi.setStatus("excelcom");

        // Simpan transaksi
        Transaksi savedTransaksi = transaksiRepository.save(transaksi);

        // Proses setiap barang transaksi
        for (BarangTransaksi barangTransaksi : request.getProduk()) {
            // Lakukan operasi lain yang diperlukan untuk setiap barang transaksi
        }

        return savedTransaksi;
    }

    private int generateNomorFaktur() {
        Optional<String> kdMaxOptional = transaksiRepository.findMaxKode("%PJN%");
        int tmp = kdMaxOptional.map(Integer::parseInt).orElse(0) + 1;

        // Ambil bulan terakhir dari tanggal transaksi terakhir
        Optional<Transaksi> lastTransactionOptional = transaksiRepository.findTopByNoFakturLikeOrderByTanggalDesc("%PJN%");
        String lastMonth = lastTransactionOptional.map(transaksi -> {
            Date tanggalDate = transaksi.getTanggal();
            LocalDate tanggalLocalDate = tanggalDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
            return tanggalLocalDate.format(formatter);
        }).orElse("");

        // Bandingkan dengan bulan saat ini untuk menentukan apakah nomor harus direset menjadi 1
        String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
        if (!lastMonth.equals(currentMonth)) {
            tmp = 1;
        }

        return tmp;
    }

}
