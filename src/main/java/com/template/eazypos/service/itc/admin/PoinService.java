package com.template.eazypos.service.itc.admin;

import com.template.eazypos.dto.PoinHistoryDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.PoinHistory;
import com.template.eazypos.repository.PoinHistoryRepository;
import com.template.eazypos.repository.TeknisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public PoinHistory add(PoinHistoryDTO poinHistoryDTO){
        PoinHistory poinHistory = new PoinHistory();
        poinHistory.setTeknisi(teknisiRepository.findById(poinHistoryDTO.getId_teknisi()).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found")));
        poinHistory.setPoin(poinHistoryDTO.getPoin());
        poinHistory.setTanggal(poinHistoryDTO.getTanggal());
        poinHistory.setKeterangan(poinHistoryDTO.getKeterangan());
        poinHistory.setNominal(poinHistoryDTO.getNominal());
        return poinHistoryRepository.save(poinHistory);
    }

}
