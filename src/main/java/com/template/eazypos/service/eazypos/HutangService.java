package com.template.eazypos.service.eazypos;

import com.template.eazypos.dto.PelunasanDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Hutang;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.repository.HutangRepository;
import com.template.eazypos.repository.TransaksiBeliRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HutangService {
    @Autowired
    private HutangRepository hutangRepository;

    @Autowired
    private TransaksiBeliRepository transaksiBeliRepository;

    // Melakukan pelunasan hutang untuk transaksi pembelian
    public Hutang pelunasan(PelunasanDTO pelunasanDTO) {
        TransaksiBeli transaksiBeli = transaksiBeliRepository.findById(pelunasanDTO.getId_transaksi()).orElseThrow(() -> new NotFoundException("Id Not Found"));
        int kekurangan = Integer.parseInt(transaksiBeli.getKekurangan());
        int pelunasan = Integer.parseInt(pelunasanDTO.getPelunasan());
        int sisaKekurangan = kekurangan - pelunasan;

        transaksiBeli.setKekurangan(String.valueOf(sisaKekurangan));
        transaksiBeliRepository.save(transaksiBeli);

        Hutang hutang = new Hutang();
        hutang.setPelunasan(pelunasanDTO.getPelunasan());
        hutang.setHutang(String.valueOf(sisaKekurangan));
        hutang.setTransaksiBeli(transaksiBeli);
        hutang.setDate(new Date());

        return hutangRepository.save(hutang);
    }

    // Mendapatkan semua transaksi pembelian yang memiliki hutang
    public List<TransaksiBeli> getAll(){
        return transaksiBeliRepository.findAllHutang();
    }

    // Mendapatkan entitas Hutang berdasarkan ID
    public Hutang getById(Long id){
        return hutangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
}
