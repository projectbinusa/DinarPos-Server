package com.template.eazypos.service.itc.admin;

import com.template.eazypos.model.PoinHistory;
import com.template.eazypos.repository.PoinHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
public class PoinService {
    @Autowired
    private PoinHistoryRepository poinHistoryRepository;

    public List<PoinHistory> getPoinByMonth(LocalDate month) {
        return poinHistoryRepository.findByMonth(month);
    }
}
