package com.template.eazypos.service.eazypos;

import com.template.eazypos.dto.KonfirmasiDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.repository.SalesmanRepository;
import com.template.eazypos.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NotifikasiService {
    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private SalesmanRepository salesmanRepository;

    // Mengambil semua transaksi Excelcom dalam rentang waktu 7 hari terakhir
    public List<Transaksi> getAll7HariExelcom() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi7Excelcom(tanggal);
    }

    // Mengambil semua transaksi Excelcom dalam rentang waktu 30 hari terakhir
    public List<Transaksi> getAll30HariExelcom() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi30Excelcom(tanggal);
    }

    // Mengambil semua transaksi Excelcom dalam rentang waktu 90 hari terakhir
    public List<Transaksi> getAll90HariExelcom() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi90Excelcom(tanggal);
    }

    // Mengambil semua transaksi Excelcom dalam rentang waktu 120 hari terakhir
    public List<Transaksi> getAll120HariExelcom() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi120Excelcom(tanggal);
    }

    // Mengambil semua transaksi Excelcom dalam rentang waktu 365 hari terakhir
    public List<Transaksi> getAll365HariExelcom() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi365Excelcom(tanggal);
    }

    // Mengambil semua transaksi Dinarpos dalam rentang waktu 7 hari terakhir
    public List<Transaksi> getAll7HariDinarpos() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi7Dinarpos(tanggal);
    }

    // Mengambil semua transaksi Dinarpos dalam rentang waktu 30 hari terakhir
    public List<Transaksi> getAll30HariDinarpos() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi30Dinarpos(tanggal);
    }

    // Mengambil semua transaksi Dinarpos dalam rentang waktu 90 hari terakhir
    public List<Transaksi> getAll90HariDinarpos() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi90Dinarpos(tanggal);
    }

    // Mengambil semua transaksi Dinarpos dalam rentang waktu 120 hari terakhir
    public List<Transaksi> getAll120HariDinarpos() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi120Dinarpos(tanggal);
    }

    // Mengambil semua transaksi Dinarpos dalam rentang waktu 365 hari terakhir
    public List<Transaksi> getAll365HariDinarpos() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi365Dinarpos(tanggal);
    }

    // Mengambil semua transaksi Excelcom yang memerlukan konfirmasi dalam rentang waktu 7 hari terakhir
    public List<Transaksi> getNotifikasi7HariExcelcom() {
        String status = "excelcom";
        return transaksiRepository.findAllKonfrimasi7Hari(status);
    }

    // Mengambil semua transaksi Excelcom yang memerlukan konfirmasi dalam rentang waktu 30 hari terakhir
    public List<Transaksi> getNotifikasi30HariExcelcom() {
        String status = "excelcom";
        return transaksiRepository.findAllKonfrimasi30Hari(status);
    }

    // Mengambil semua transaksi Excelcom yang memerlukan konfirmasi dalam rentang waktu 90 hari terakhir
    public List<Transaksi> getNotifikasi90HariExcelcom() {
        String status = "excelcom";
        return transaksiRepository.findAllKonfrimasi90Hari(status);
    }

    // Mengambil semua transaksi Excelcom yang memerlukan konfirmasi dalam rentang waktu 120 hari terakhir
    public List<Transaksi> getNotifikasi120HariExcelcom() {
        String status = "excelcom";
        return transaksiRepository.findAllKonfrimasi120Hari(status);
    }

    // Mengambil semua transaksi Dinarpos yang memerlukan konfirmasi dalam rentang waktu 365 hari terakhir
    public List<Transaksi> getNotifikasi365HariExcelcom() {
        String status = "dinarpos";
        return transaksiRepository.findAllKonfrimasi365Hari(status);
    }

    // Mengambil semua transaksi Dinarpos yang memerlukan konfirmasi dalam rentang waktu 7 hari terakhir
    public List<Transaksi> getNotifikasi7HariDinarpos() {
        String status = "dinarpos";
        return transaksiRepository.findAllKonfrimasi7Hari(status);
    }

    // Mengambil semua transaksi Dinarpos yang memerlukan konfirmasi dalam rentang waktu 30 hari terakhir
    public List<Transaksi> getNotifikasi30HariDinarpos() {
        String status = "dinarpos";
        return transaksiRepository.findAllKonfrimasi30Hari(status);
    }

    // Mengambil semua transaksi Dinarpos yang memerlukan konfirmasi dalam rentang waktu 90 hari terakhir
    public List<Transaksi> getNotifikasi90HariDinarpos() {
        String status = "dinarpos";
        return transaksiRepository.findAllKonfrimasi90Hari(status);
    }

    // Mengambil semua transaksi Dinarpos yang memerlukan konfirmasi dalam rentang waktu 120 hari terakhir
    public List<Transaksi> getNotifikasi120HariDinarpos() {
        String status = "dinarpos";
        return transaksiRepository.findAllKonfrimasi120Hari(status);
    }

    // Mengambil semua transaksi Dinarpos yang memerlukan konfirmasi dalam rentang waktu 365 hari terakhir
    public List<Transaksi> getNotifikasi365HariDinarpos() {
        String status = "dinarpos";
        return transaksiRepository.findAllKonfrimasi365Hari(status);
    }

    // Melakukan konfirmasi transaksi untuk rentang waktu 7 hari terakhir untuk transaksi Excelcom
    public Transaksi konfirmasi7Hari(KonfirmasiDTO konfirmasiDTO, Long id) {
        Transaksi update = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
        update.setKet7Hari(konfirmasiDTO.getKet());
        update.setSalesman7Hari(salesmanRepository.findById(konfirmasiDTO.getId_salesman()).orElse(null));
        update.setHari7(0);
        update.setTanggalKonfirmasi7(new Date());
        return transaksiRepository.save(update);
    }

    // Melakukan konfirmasi transaksi untuk rentang waktu 30 hari terakhir untuk transaksi Excelcom
    public Transaksi konfirmasi30Hari(KonfirmasiDTO konfirmasiDTO, Long id) {
        Transaksi update = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
        update.setKet30Hari(konfirmasiDTO.getKet());
        update.setSalesman30Hari(salesmanRepository.findById(konfirmasiDTO.getId_salesman()).orElse(null));
        update.setHari30(0);
        update.setTanggalKonfirmasi30(new Date());
        return transaksiRepository.save(update);
    }

    // Melakukan konfirmasi transaksi untuk rentang waktu 90 hari terakhir untuk transaksi Excelcom
    public Transaksi konfirmasi90Hari(KonfirmasiDTO konfirmasiDTO, Long id) {
        Transaksi update = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
        update.setKet90Hari(konfirmasiDTO.getKet());
        update.setSalesman90Hari(salesmanRepository.findById(konfirmasiDTO.getId_salesman()).orElse(null));
        update.setHari90(0);
        update.setTanggalKonfirmasi90(new Date());
        return transaksiRepository.save(update);
    }

    // Melakukan konfirmasi transaksi untuk rentang waktu 120 hari terakhir untuk transaksi Excelcom
    public Transaksi konfirmasi120Hari(KonfirmasiDTO konfirmasiDTO, Long id) {
        Transaksi update = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
        update.setKet120Hari(konfirmasiDTO.getKet());
        update.setSalesman120Hari(salesmanRepository.findById(konfirmasiDTO.getId_salesman()).orElse(null));
        update.setHari120(0);
        update.setTanggalKonfirmasi120(new Date());
        return transaksiRepository.save(update);
    }

    // Melakukan konfirmasi transaksi untuk rentang waktu 365 hari terakhir
    public Transaksi konfirmasi365Hari(KonfirmasiDTO konfirmasiDTO, Long id) {
        Transaksi update = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setKet365Hari(konfirmasiDTO.getKet());
        update.setSalesman365Hari(salesmanRepository.findById(konfirmasiDTO.getId_salesman()).orElse(null));
        update.setHari365(0);
        update.setTanggalKonfirmasi365(new Date());
        return transaksiRepository.save(update);
    }
}
