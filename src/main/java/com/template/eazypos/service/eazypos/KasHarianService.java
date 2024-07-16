package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.KasHarian;
import com.template.eazypos.model.SaldoAwalShift;
import com.template.eazypos.repository.KasHarianRepository;
import com.template.eazypos.repository.SaldoAwalShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KasHarianService {
    @Autowired
    private SaldoAwalShiftRepository saldoAwalShiftRepository;
    @Autowired
    private KasHarianRepository kasHarianRepository;

    public SaldoAwalShift add(SaldoAwalShift shift){
        SaldoAwalShift saldoAwalShift = new SaldoAwalShift();
        saldoAwalShift.setSaldoAwal(shift.getSaldoAwal());
        saldoAwalShift.setShift(shift.getShift());
        saldoAwalShift.setDate(new Date());
        saldoAwalShift.setSetorKas("0");
        return saldoAwalShiftRepository.save(saldoAwalShift);
    }
    public SaldoAwalShift getById(Long id){
        return saldoAwalShiftRepository.findById(id).orElseThrow(() -> new NotFoundException("Id not found"));
    }
    public List<SaldoAwalShift> getAll(){
        return saldoAwalShiftRepository.findAll();
    }
    public SaldoAwalShift edit(SaldoAwalShift shift,Long id){
        SaldoAwalShift saldoAwalShift = saldoAwalShiftRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        saldoAwalShift.setSetorKas(shift.getSetorKas());
        saldoAwalShift.setShift(shift.getShift());
        saldoAwalShift.setSaldoAwal(shift.getSaldoAwal());
        return saldoAwalShiftRepository.save(saldoAwalShift);
    }
    public Map<String, Boolean> delete(Long id ) {
        try {
            saldoAwalShiftRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
    public SaldoAwalShift findByDateAndShift(Date date, String shift) {
        return saldoAwalShiftRepository.findByDateAndShift(date, shift);
    }
    public List<KasHarian> findByDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Date startOfDay = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).minusNanos(1).toInstant());

        return kasHarianRepository.findByTimestampBetween(startOfDay, endOfDay);
    }
}
