package com.template.eazypos.service.eazypos;

import com.template.eazypos.dto.KonfirmasiDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotifikasiService {
    @Autowired
    private TransaksiRepository transaksiRepository;

    public List<Transaksi> getAll7HariExelcom() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi7Excelcom(tanggal);
    }

    public List<Transaksi> getAll30HariExelcom() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi30Excelcom(tanggal);
    }

    public List<Transaksi> getAll90HariExelcom() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi90Excelcom(tanggal);
    }

    public List<Transaksi> getAll120HariExelcom() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi120Excelcom(tanggal);
    }

    public List<Transaksi> getAll365HariExelcom() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi365Excelcom(tanggal);
    }

    public List<Transaksi> getAll7HariDinarpos() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi7Dinarpos(tanggal);
    }

    public List<Transaksi> getAll30HariDinarpos() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi30Dinarpos(tanggal);
    }

    public List<Transaksi> getAll90HariDinarpos() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi90Dinarpos(tanggal);
    }

    public List<Transaksi> getAll120HariDinarpos() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi120Dinarpos(tanggal);
    }

    public List<Transaksi> getAll365HariDinarpos() {
        Date tanggal = new Date();
        return transaksiRepository.findAllTransaksi365Dinarpos(tanggal);
    }

    public Transaksi konfirmasi7Hari(KonfirmasiDTO konfirmasiDTO, Long id) {
        Transaksi update = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setKet7Hari(konfirmasiDTO.getKet());
        update.setSalesman7Hari(konfirmasiDTO.getSalesman());
        update.setHari7(0);
        update.setTanggalKonfirmasi7(new Date());
        return transaksiRepository.save(update);
    }

    public Transaksi konfirmasi30Hari(KonfirmasiDTO konfirmasiDTO, Long id) {
        Transaksi update = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setKet30Hari(konfirmasiDTO.getKet());
        update.setSalesman30Hari(konfirmasiDTO.getSalesman());
        update.setHari30(0);
        update.setTanggalKonfirmasi30(new Date());
        return transaksiRepository.save(update);
    }

    public Transaksi konfirmasi90Hari(KonfirmasiDTO konfirmasiDTO, Long id) {
        Transaksi update = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setKet90Hari(konfirmasiDTO.getKet());
        update.setSalesman90Hari(konfirmasiDTO.getSalesman());
        update.setHari90(0);
        update.setTanggalKonfirmasi90(new Date());
        return transaksiRepository.save(update);
    }

    public Transaksi konfirmasi120Hari(KonfirmasiDTO konfirmasiDTO, Long id) {
        Transaksi update = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setKet120Hari(konfirmasiDTO.getKet());
        update.setSalesman120Hari(konfirmasiDTO.getSalesman());
        update.setHari120(0);
        update.setTanggalKonfirmasi120(new Date());
        return transaksiRepository.save(update);
    }

    public Transaksi konfirmasi365Hari(KonfirmasiDTO konfirmasiDTO, Long id) {
        Transaksi update = transaksiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setKet365Hari(konfirmasiDTO.getKet());
        update.setSalesman365Hari(konfirmasiDTO.getSalesman());
        update.setHari365(0);
        update.setTanggalKonfirmasi365(new Date());
        return transaksiRepository.save(update);
    }
}
