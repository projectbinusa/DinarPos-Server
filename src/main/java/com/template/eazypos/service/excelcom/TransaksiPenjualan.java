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

    public Transaksi addExcelcom(Transaksi transaksi) {
        // Cek apakah no_faktur sudah ada dalam database
        boolean fakturExists = transaksiRepository.existsByNoFaktur(transaksi.getNoFaktur());
        if (fakturExists) {
           throw new BadRequestException("Nomor faktur sudah ada");
        }

        // Mendapatkan nomor nota transaksi penjualan excelcom
        Long nota = getNoNotaTransaksiPenjualanExcelcom();

        // Format no_faktur
        String nomor = String.format("%tm%tY", new Date(), new Date()); // Format bulan dan tahun
        String noFaktur = String.format("%s-PST-PJN-000%d", nomor, nota);

        // Set no_faktur dalam objek transaksi
        transaksi.setNoFaktur(noFaktur);

        // Mengatur tanggal dan waktu
        transaksi.setTanggal(new Date());
        transaksi.setTanggalNotif7(calculateDate(7));
        transaksi.setTanggalNotif30(calculateDate(30));
        transaksi.setTanggalNotif90(calculateDate(90));
        transaksi.setTanggalNotif120(calculateDate(120));
        transaksi.setTanggalNotif365(calculateDate(365));

        // Menyimpan transaksi ke dalam repository
        Transaksi savedTransaksi = transaksiRepository.save(transaksi);
        return savedTransaksi;
    }

    // Metode untuk mendapatkan nomor nota transaksi penjualan excelcom
    private Long getNoNotaTransaksiPenjualanExcelcom() {
        // Implementasikan logika untuk mendapatkan nomor nota
        return null;
    }

    // Metode untuk menghitung tanggal di masa depan berdasarkan jumlah hari
    private Date calculateDate(int days) {
        long millisToAdd = days * 24 * 60 * 60 * 1000L; // konversi hari ke milidetik
        return new Date(System.currentTimeMillis() + millisToAdd);
    }

}
