package com.template.eazypos.service.itc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.eazypos.dto.KunjunganDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.repository.CustomerRepository;
import com.template.eazypos.repository.KunjunganRepository;
import com.template.eazypos.repository.PlanningRepository;
import com.template.eazypos.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class KunjunganService {
    private static final String BASE_URL = "https://s3.lynk2.co/api/s3/pos/marketting";
    @Autowired
    private KunjunganRepository kunjunganRepository;
    @Autowired
    private SalesmanRepository salesmanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PlanningRepository planningRepository;


    public List<Kunjungan> getAll(){
        return kunjunganRepository.findAll();
    }
    public List<Kunjungan> getDealPO(){
        return kunjunganRepository.findDealPo();
    }
    public List<Kunjungan> getDealFinish(){
        return kunjunganRepository.findDealFinish();
    }
    public List<Kunjungan> getDealPoAndSalesman(String nama){
        Salesman salesman = salesmanRepository.findByNama(nama).orElseThrow(() -> new NotFoundException("Nama Not Found"));
        return kunjunganRepository.findDealPoAndSalesman(salesman.getId());
    }
    public List<Kunjungan> getDealFinishAndSalesman(String nama){
        Salesman salesman = salesmanRepository.findByNama(nama).orElseThrow(() -> new NotFoundException("Nama Not Found"));
        return kunjunganRepository.findDealPoAndSalesman(salesman.getId());
    }
    public List<Kunjungan> getByDateBetween(Date tglAwal , Date tglAkhir){
        return kunjunganRepository.findByDate(tglAwal ,tglAkhir);
    }
    public List<Kunjungan> getByDate(Date date){
        return kunjunganRepository.findByTanggal(date);
    }

    public List<Kunjungan> getByCustomer(Long id){
        return kunjunganRepository.findByIdCustomer(id);
    }
    public List<Kunjungan> getByBulan(int bulan){
        return kunjunganRepository.findByBulan(bulan);
    }
    public Kunjungan getById(Long id) {
        return kunjunganRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            kunjunganRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }
    public Kunjungan add(KunjunganDTO kunjunganDTO , MultipartFile multipartFile) throws IOException {
        String image = uploadFoto(multipartFile);
        Kunjungan kunjungan = new Kunjungan();
        kunjungan.setAction(kunjunganDTO.getAction());
        kunjungan.setCp(kunjunganDTO.getCp());
        kunjungan.setCustomer(customerRepository.findById(kunjunganDTO.getId_customer()).orElseThrow(() -> new NotFoundException("Id Customer Not Found")));
        kunjungan.setDeal(kunjunganDTO.getDeal());
        kunjungan.setServiceTt(kunjunganDTO.getServiceTt());
        kunjungan.setFoto(image);
        kunjungan.setTanggalKunjungan(kunjunganDTO.getTgl());
        kunjungan.setWaktuPengadaan(kunjunganDTO.getWaktuPengadaan());
        kunjungan.setVisit(kunjunganDTO.getVisit());
        kunjungan.setTujuan(kunjunganDTO.getTujuan());
        kunjungan.setTimestamp(new Date());
        kunjungan.setTanggalDeal(kunjunganDTO.getTanggal_deal());
        kunjungan.setSalesman(salesmanRepository.findById(kunjunganDTO.getId_salesman()).orElseThrow(() -> new NotFoundException("Id Salesman Not Found")));
        kunjungan.setPlanning(planningRepository.findById(kunjunganDTO.getId_plan()).orElseThrow(() -> new NotFoundException("Id Planning Not Found")));
        kunjungan.setPembayaran(kunjunganDTO.getPembayaran());
        kunjungan.setPeluang(kunjunganDTO.getPeluang());
        kunjungan.setnVisit(kunjunganDTO.getnVisit());
        kunjungan.setLokasiLon(kunjunganDTO.getLokasiLon());
        kunjungan.setLokasiLat(kunjunganDTO.getLokasiLat());
        kunjungan.setInfoDpt(kunjunganDTO.getInfoDpt());
        kunjungan.setAction(kunjunganDTO.getAction());;
        return kunjunganRepository.save(kunjungan);
    }

    public Kunjungan edit(Long id ,KunjunganDTO kunjunganDTO , MultipartFile multipartFile) throws IOException {
        Kunjungan kunjungan = kunjunganRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        String image = uploadFoto(multipartFile);
        kunjungan.setAction(kunjunganDTO.getAction());
        kunjungan.setCp(kunjunganDTO.getCp());
        kunjungan.setCustomer(customerRepository.findById(kunjunganDTO.getId_customer()).orElseThrow(() -> new NotFoundException("Id Customer Not Found")));
        kunjungan.setDeal(kunjunganDTO.getDeal());
        kunjungan.setServiceTt(kunjunganDTO.getServiceTt());
        kunjungan.setFoto(image);
        kunjungan.setTanggalKunjungan(kunjunganDTO.getTgl());
        kunjungan.setWaktuPengadaan(kunjunganDTO.getWaktuPengadaan());
        kunjungan.setVisit(kunjunganDTO.getVisit());
        kunjungan.setTujuan(kunjunganDTO.getTujuan());
        kunjungan.setTimestamp(new Date());
        kunjungan.setTanggalDeal(kunjunganDTO.getTanggal_deal());
        kunjungan.setSalesman(salesmanRepository.findById(kunjunganDTO.getId_salesman()).orElseThrow(() -> new NotFoundException("Id Salesman Not Found")));
        kunjungan.setPlanning(planningRepository.findById(kunjunganDTO.getId_plan()).orElseThrow(() -> new NotFoundException("Id Planning Not Found")));
        kunjungan.setPembayaran(kunjunganDTO.getPembayaran());
        kunjungan.setPeluang(kunjunganDTO.getPeluang());
        kunjungan.setnVisit(kunjunganDTO.getnVisit());
        kunjungan.setLokasiLon(kunjunganDTO.getLokasiLon());
        kunjungan.setLokasiLat(kunjunganDTO.getLokasiLat());
        kunjungan.setInfoDpt(kunjunganDTO.getInfoDpt());
        kunjungan.setAction(kunjunganDTO.getAction());
        return kunjunganRepository.save(kunjungan);
    }

    private String uploadFoto(MultipartFile multipartFile) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", multipartFile.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, requestEntity, String.class);
        String fileUrl = extractFileUrlFromResponse(response.getBody());
        return fileUrl;
    }

    private String extractFileUrlFromResponse(String responseBody) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonResponse = mapper.readTree(responseBody);
        JsonNode dataNode = jsonResponse.path("data");
        String urlFile = dataNode.path("url_file").asText();

        return urlFile;
    }

}
