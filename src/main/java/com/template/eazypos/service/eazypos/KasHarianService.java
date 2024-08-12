package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.KasHarian;
import com.template.eazypos.model.PersediaanBarang;
import com.template.eazypos.model.SaldoAwalShift;
import com.template.eazypos.repository.KasHarianRepository;
import com.template.eazypos.repository.PersediaanBarangRepository;
import com.template.eazypos.repository.SaldoAwalShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class KasHarianService {
    @Autowired
    private SaldoAwalShiftRepository saldoAwalShiftRepository;
    @Autowired
    private KasHarianRepository kasHarianRepository;

    @Autowired
    private PersediaanBarangRepository persediaanBarangRepository;

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
    public List<SaldoAwalShift> getSaldoShiftAwal(Date date, String shift) {
        return saldoAwalShiftRepository.findByDateAndShift(date , shift);
    }

    public SaldoAwalShift getSaldoAwalShiftById(Long id) {
        return saldoAwalShiftRepository.findById(id).orElse(null);
    }

    public SaldoAwalShift createSaldoAwalShift(SaldoAwalShift saldoAwalShift) {
        return saldoAwalShiftRepository.save(saldoAwalShift);
    }

    public SaldoAwalShift updateSaldoAwalShift(Long id, SaldoAwalShift newSaldoAwalShift) {
        SaldoAwalShift existingSaldoAwalShift = saldoAwalShiftRepository.findById(id).orElse(null);
        if (existingSaldoAwalShift != null) {
            existingSaldoAwalShift.setShift(newSaldoAwalShift.getShift());
            existingSaldoAwalShift.setDate(newSaldoAwalShift.getDate());
            existingSaldoAwalShift.setSaldoAwal(newSaldoAwalShift.getSaldoAwal());
            existingSaldoAwalShift.setSetorKas(newSaldoAwalShift.getSetorKas());
            return saldoAwalShiftRepository.save(existingSaldoAwalShift);
        }
        return null;
    }

    public void deleteSaldoAwalShift(Long id) {
        saldoAwalShiftRepository.deleteById(id);
    }
    public List<KasHarian> getDataKasHarianShiftPagi(Date date) {
        // Set waktu mulai dan akhir untuk shift pagi
        Date startTime = getStartTime(date, 7, 0);
        Date endTime = getEndTime(date, 14, 0);
        return kasHarianRepository.findByTimestampBetween(startTime, endTime);
    }

    public List<KasHarian> getDataKasHarianShiftSiang(Date date) {
        // Set waktu mulai dan akhir untuk shift siang
        Date startTime = getStartTime(date, 14, 0);
        Date endTime = getEndTime(date, 21, 0);
        return kasHarianRepository.findByTimestampBetween(startTime, endTime);
    }

    private Date getStartTime(Date date, int hour, int minute) {
        // Buat instance dari Calendar dan set waktunya ke tanggal yang diberikan
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Set jam dan menit ke waktu yang diinginkan
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Kembalikan waktu yang sudah di-set
        return calendar.getTime();
    }

    private Date getEndTime(Date date, int hour, int minute) {
        // Buat instance dari Calendar dan set waktunya ke tanggal yang diberikan
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Set jam dan menit ke waktu yang diinginkan
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Kembalikan waktu yang sudah di-set
        return calendar.getTime();
    }

    public List<PersediaanBarang> barangJual(Long idTransaksi) {
        return persediaanBarangRepository.findByIdTransaksi(idTransaksi);
    }


}
