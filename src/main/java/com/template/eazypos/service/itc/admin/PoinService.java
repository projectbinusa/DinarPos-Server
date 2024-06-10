package com.template.eazypos.service.itc.admin;

import com.template.eazypos.model.PoinHistory;
import com.template.eazypos.repository.PoinHistoryRepository;
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
    public List<PoinHistory> getPoinHistoryByIdTeknisi(Long id){
        return poinHistoryRepository.findByIdTeknisi(id);
    }

}
