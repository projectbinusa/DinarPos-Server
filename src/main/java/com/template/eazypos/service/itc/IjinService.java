package com.template.eazypos.service.itc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.eazypos.dto.IjinDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Ijin;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.repository.IjinRepository;
import com.template.eazypos.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IjinService {
    @Autowired
    private IjinRepository ijinRepository;
    @Autowired
    private SalesmanRepository salesmanRepository;
    private static final String BASE_URL = "https://s3.lynk2.co/api/s3/pos/marketting";

    public Ijin add(IjinDTO ijinDTO , MultipartFile multipartFile) throws IOException {
        Ijin ijin = new Ijin();
        String foto = uploadFoto(multipartFile);
        ijin.setSalesman(salesmanRepository.findById(ijinDTO.getId_salesman()).orElseThrow(() -> new NotFoundException("Id Salesman Not Found")));
        ijin.setJenis(ijinDTO.getJenis());
        ijin.setKet(ijinDTO.getKet());
        ijin.setTgl_a(ijinDTO.getTgl());
        ijin.setTgl_b(ijinDTO.getS_d_tgl());
        ijin.setFoto(foto);
        return ijinRepository.save(ijin);
    }
    public List<Ijin> getAll(){
        return ijinRepository.findAll();
    }
    public Ijin getById(Long id){
        return ijinRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
    public Ijin edit(IjinDTO ijinDTO, Long id , MultipartFile multipartFile) throws IOException {
        Ijin ijin = ijinRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        String foto = uploadFoto(multipartFile);
        ijin.setSalesman(salesmanRepository.findById(ijinDTO.getId_salesman()).orElseThrow(() -> new NotFoundException("Id Salesman Not Found")));
        ijin.setJenis(ijinDTO.getJenis());
        ijin.setKet(ijinDTO.getKet());
        ijin.setTgl_a(ijinDTO.getTgl());
        ijin.setTgl_b(ijinDTO.getS_d_tgl());
        ijin.setFoto(foto);
        return ijinRepository.save(ijin);
    }

    public Map<String, Boolean> delete(Long id) {
        try {
            ijinRepository.deleteById(id);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
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
