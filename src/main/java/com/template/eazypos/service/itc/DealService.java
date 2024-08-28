package com.template.eazypos.service.itc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public  Deal add(DealPODTO dealPODTO , MultipartFile foto ,MultipartFile file) throws IOException {
        String image = uploadFoto(foto);
        String file_po = uploadFoto(file);
        Deal deal =new Deal();
        deal.setKet(dealPODTO.getKet());
        deal.setAdministrasi(dealPODTO.getAdministrasi());
        deal.setTanggal_input(dealPODTO.getTgl_input());
        deal.setKunjungan(kunjunganRepository.findById(dealPODTO.getId_kunjungan()).orElseThrow(() -> new NotFoundException("Id Kunjungan Not Found")));
        deal.setFoto(image);
        deal.setFile_po(file_po);
        return dealRepository.save(deal);
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
