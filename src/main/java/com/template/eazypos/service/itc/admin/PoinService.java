package com.template.eazypos.service.itc.admin;

import com.template.eazypos.dto.PoinHistoryDTO;
import com.template.eazypos.dto.PoinHistoryMonthDTO;
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
import java.util.stream.Collectors;

@Service
public class PoinService {
    @Autowired
    private PoinHistoryRepository poinHistoryRepository;
    private TeknisiRepository teknisiRepository;

    @Autowired
    private PoinRepository poinRepository;

    // Mengambil riwayat poin berdasarkan bulan
//    public List<PoinHistoryMonthDTO> getPoinByMonth(LocalDate month) {
//        List<PoinHistoryMonthDTO> data = poinHistoryRepository.findByMonth(month);
//        return data.stream()
//                .sorted((d1, d2) -> Long.compare(d2.getTeknisiId(), d1.getTeknisiId()))
//                .collect(Collectors.toList());
//    }

    // Mengambil riwayat poin berdasarkan rentang tanggal dan ID teknisi
    public List<PoinHistory> getByTanggal(Date tanggalAwal , Date tanggalAkhir){
        return poinHistoryRepository.findByTanggal(tanggalAwal , tanggalAkhir);
    }

    // Menghitung total poin untuk teknisi pada bulan tertentu
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

    // Mengambil riwayat poin berdasarkan rentang tanggal dan ID teknisi
    public List<PoinHistory> getPoinHistory(Date tanggalAwal, Date tanggalAkhir, Long idTeknisi) {
        return poinHistoryRepository.findByDateRangeAndTeknisi(tanggalAwal, tanggalAkhir, idTeknisi);
    }

    // Mengambil riwayat poin berdasarkan ID teknisi
    public List<PoinHistory> getPoinHistoryByIdTeknisi(String id){
        return poinHistoryRepository.findByIdTeknisi(id);
    }

    // Mengambil semua riwayat poin dengan keterangan tertentu
    public List<PoinHistory> getAllByKeterangan(String keterangan) {
        return poinHistoryRepository.findAllByKeterangan(keterangan);
    }

    // Mengambil semua poin dengan nama teknisi
    public List<Poin> getPoin() {
        return poinRepository.findAllPoinWithTeknisiName();
    }

    // Mengambil total poin dari semua riwayat poin
    public double getTotalPoin() {
        Double totalPoin = poinHistoryRepository.getTotalPoin();
        return totalPoin != null ? totalPoin : 0.0;
    }

    // Mengambil total poin berdasarkan bulan
    public int getTotalPoinByMonth(int month) {
        Integer totalPoin = poinHistoryRepository.findTotalPoinByMonth(month);
        return totalPoin != null ? totalPoin : 0;
    }

    // Mengambil riwayat poin berdasarkan bulan
    public List<PoinHistory> getPoinByMonth(String month) {
        return poinHistoryRepository.findPoinByMonth(month);
    }

    // Menghitung total poin untuk seorang teknisi pada bulan dan tahun tertentu
    public double getTotalPoinByMonthAndYear(Long idTeknisi, int year, int month) {
        Double totalPoin = poinHistoryRepository.findTotalPoinByMonthAndYear(idTeknisi, year, month);
        return totalPoin != null ? totalPoin : 0;
    }

    // Menambahkan entri baru ke riwayat poin_history dan juga ke tabel Poin
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

    // Mengambil semua riwayat poin
    public List<PoinHistory> getAllPoinHistory() {
        return poinHistoryRepository.findAll();
    }
}
