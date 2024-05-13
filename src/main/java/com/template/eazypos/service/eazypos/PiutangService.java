package com.template.eazypos.service.eazypos;

import com.template.eazypos.dto.PelunasanDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Piutang;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.repository.PiutangRepository;
import com.template.eazypos.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PiutangService {
    @Autowired
    private PiutangRepository piutangRepository;

    @Autowired
    private TransaksiRepository transaksiRepository;

    public Piutang pelunasan(PelunasanDTO piutang, Long id){
        Piutang pelunasan = piutangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        pelunasan.setPelunasan(piutang.getPelunasan());
        return piutangRepository.save(pelunasan);
    }
    public List<Transaksi> getAll() {
        return transaksiRepository.findAllPiutang();
    }
    public Piutang getById(Long id){
        return piutangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not found"));
    }
}
