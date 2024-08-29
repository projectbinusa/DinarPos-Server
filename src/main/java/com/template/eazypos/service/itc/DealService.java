package com.template.eazypos.service.itc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.eazypos.dto.DealFinishDTO;
import com.template.eazypos.dto.DealGudangDTO;
import com.template.eazypos.dto.DealPODTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Deal;
import com.template.eazypos.model.Finish;
import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.repository.DealRepository;
import com.template.eazypos.repository.FinishRepository;
import com.template.eazypos.repository.KunjunganRepository;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class DealService {
    @Autowired
    private DealRepository dealRepository;

    @Autowired
    private FinishRepository finishRepository;

    @Autowired
    private KunjunganRepository kunjunganRepository;

    private static final String BASE_URL = "https://s3.lynk2.co/api/s3/pos/marketting";
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB in bytes

    public  Deal addDealPO(DealPODTO dealPODTO , MultipartFile foto ,MultipartFile file) throws IOException {
        String image = uploadFoto(foto);
        String file_po = uploadFoto(file);
        Deal deal =new Deal();
        deal.setKet(dealPODTO.getKet());
        deal.setAdministrasi(dealPODTO.getAdministrasi());
        deal.setTanggal_input(new Date());
        deal.setKunjungan(kunjunganRepository.findById(dealPODTO.getId_kunjungan()).orElseThrow(() -> new NotFoundException("Id Kunjungan Not Found")));
        deal.setFoto(image);
        deal.setFile_po(file_po);
        return dealRepository.save(deal);
    }
    public Finish addDealFinish(DealFinishDTO dealFinishDTO , MultipartFile bast , MultipartFile baut , MultipartFile baso ,  MultipartFile spk , MultipartFile ev_dtg , MultipartFile ev_pro , MultipartFile ev_fin ,MultipartFile file_spk) throws IOException {
        Finish finish = new Finish();
        String bast1 = uploadFoto(bast);
        String baut1 = uploadFoto(baut);
        String baso1 = uploadFoto(baso);
        String spk1 = uploadFoto(spk);
        String ev_dtg1 = uploadFoto(ev_dtg);
        String ev_pro1 = uploadFoto(ev_pro);
        String ev_fin1 = uploadFoto(ev_fin);
        String file_spk1 = uploadFoto(file_spk);
        finish.setKunjungan(kunjunganRepository.findById(dealFinishDTO.getId_kunjungan()).orElseThrow(() -> new NotFoundException("Id Kunjungan Not Found")));
        finish.setBAST(bast1);
        finish.setBASP(baso1);
        finish.setBAUT(baut1);
        finish.setSPK(spk1);
        finish.setEv_dtg(ev_dtg1);
        finish.setEv_pro(ev_pro1);
        finish.setEv_fin(ev_fin1);
        finish.setFile_spk(file_spk1);
        return finishRepository.save(finish);

    }

    public List<Deal> getDealPO(){
        return dealRepository.findAll();
    }
    public List<Deal> getDealPOByCustomer(Long id){
        return dealRepository.findByCustomerId(id);
    }
    public List<Finish> getDealFinish(){
        return finishRepository.findAll();
    }
    public List<Finish> getDealFinishByCustomer(Long id){
        return finishRepository.findByCustomerId(id);
    }

    public Deal edit(Long id,DealGudangDTO gudangDTO){
        Deal deal = dealRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        deal.setStatus(gudangDTO.getStatus());
        deal.setKet_status(gudangDTO.getStatus_ket());
        return dealRepository.save(deal);
    }
    private String uploadFoto(MultipartFile multipartFile) throws IOException {
        if (multipartFile.getSize() > MAX_FILE_SIZE) {
            throw new IOException("File size exceeds the limit of 2MB");
        }

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
