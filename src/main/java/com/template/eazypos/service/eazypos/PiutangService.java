package com.template.eazypos.service.eazypos;

import com.template.eazypos.dto.PelunasanDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Piutang;
import com.template.eazypos.model.Piutang;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.repository.PiutangRepository;
import com.template.eazypos.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PiutangService {
    @Autowired
    private PiutangRepository piutangRepository;

    @Autowired
    private TransaksiRepository transaksiRepository;

    // Melakukan pelunasan piutang pada transaksi yang diberikan
    public Piutang pelunasan(PelunasanDTO pelunasanDTO) {
        Transaksi transaksi = transaksiRepository.findById(pelunasanDTO.getId_transaksi()).orElseThrow((() -> new NotFoundException("Id Not Found")));
        int kekurangan = Integer.parseInt(transaksi.getKekurangan());
        int pelunasan = Integer.parseInt(pelunasanDTO.getPelunasan());
        int sisaKekurangan = kekurangan - pelunasan;
        transaksi.setKekurangan(String.valueOf(sisaKekurangan));
        transaksiRepository.save(transaksi);

        Piutang piutang = new Piutang();
        piutang.setPelunasan(pelunasanDTO.getPelunasan());
        piutang.setKekurangan(String.valueOf(sisaKekurangan));
        piutang.setTransaksi(transaksiRepository.findById(transaksi.getIdTransaksi()).get());
        piutang.setDate(new Date());
        return piutangRepository.save(piutang);
    }

    // Mengambil daftar semua transaksi yang memiliki piutang
    public List<Transaksi> getAll() {
        return transaksiRepository.findAllPiutang();
    }

    // Mengambil data piutang berdasarkan ID
    public Piutang getById(Long id){
        return piutangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not found"));
    }
}
