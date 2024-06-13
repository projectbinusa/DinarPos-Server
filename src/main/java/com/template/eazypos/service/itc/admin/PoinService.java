package com.template.eazypos.service.itc.admin;

import com.template.eazypos.dto.PoinHistoryDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Poin;
import com.template.eazypos.model.PoinHistory;
import com.template.eazypos.model.Teknisi;
import com.template.eazypos.repository.PoinHistoryRepository;
import com.template.eazypos.repository.PoinRepository;
import com.template.eazypos.repository.TeknisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PoinService {
    @Autowired
    private PoinHistoryRepository poinHistoryRepository;

    @Autowired
    private TeknisiRepository teknisiRepository;

    @Autowired
    private PoinRepository poinRepository;

    public List<PoinHistory> getPoinByMonth(LocalDate month) {
        return poinHistoryRepository.findByMonth(month);
    }
    public List<PoinHistory> getByTanggal(Date tanggalAwal , Date tanggalAkhir){
        return poinHistoryRepository.findByTanggal(tanggalAwal , tanggalAkhir);
    }
    public Map<Object, Object> getPoinForMonth(Long idTeknisi, int month) {
        List<PoinHistory> poinHistories = poinHistoryRepository.findPoinByTeknisiForMonth(idTeknisi, month);

        int totalPoin = 0;
        for (PoinHistory poinHistory : poinHistories) {
            totalPoin += poinHistory.getPoin();
        }
        Map<Object, Object> response = new HashMap<>();
        response.put("total_poin" , totalPoin);
        return response;
    }

    public List<PoinHistory> getPoinHistory(Date tanggalAwal, Date tanggalAkhir, Long idTeknisi) {
        return poinHistoryRepository.findByDateRangeAndTeknisi(tanggalAwal, tanggalAkhir, idTeknisi);
    }
    public List<PoinHistory> getPoinHistoryByIdTeknisi(String id){
        return poinHistoryRepository.findByIdTeknisi(id);
    }

    public List<PoinHistory> getAllByKeterangan(String keterangan) {
        return poinHistoryRepository.findAllByKeterangan(keterangan);
    }

    @Transactional
    public PoinHistory add(PoinHistoryDTO poinHistoryDTO) {
        // Mencari teknisi berdasarkan id_teknisi
        Teknisi teknisi = teknisiRepository.findById(poinHistoryDTO.getId_teknisi())
                .orElseThrow(() -> new RuntimeException("Teknisi not found"));

        // Membuat instance PoinHistory baru dan mengisi properti dari DTO
        PoinHistory poinHistory = new PoinHistory();
        poinHistory.setTeknisi(teknisi);
        poinHistory.setPoin(poinHistoryDTO.getPoin());
        poinHistory.setTanggal(poinHistoryDTO.getTanggal());
        poinHistory.setKeterangan(poinHistoryDTO.getKeterangan());
        poinHistory.setNominal(poinHistoryDTO.getNominal());

        // Menyimpan PoinHistory ke database
        poinHistory = poinHistoryRepository.save(poinHistory);

        // Menambah data ke tabel Poin
        Poin poin = new Poin();
        poin.setTeknisi(teknisi);
        poin.setPoin(poinHistoryDTO.getPoin());
        poinRepository.save(poin);

        return poinHistory;
    }

    public List<PoinHistory> getAllPoinHistory() {
        return poinHistoryRepository.findAll();
    }

}
