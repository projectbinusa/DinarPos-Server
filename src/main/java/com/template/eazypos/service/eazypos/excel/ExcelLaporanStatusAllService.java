package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Status;
import com.template.eazypos.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelLaporanStatusAllService {


    @Autowired
    private StatusRepository statusRepository;

    public ByteArrayInputStream loadStatus() throws IOException {
        List<Status> statuses = statusRepository.findAll();
        return ExcelLaporanStatusAll.statusToExcel(statuses);
    }

    public void saveStatus(MultipartFile file) {
        try {
            List<Status> statusList = ExcelLaporanStatusAll.excelToStatus(file.getInputStream());
            statusRepository.saveAll(statusList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

    public ByteArrayInputStream templateToExcel() throws IOException {
        return ExcelLaporanStatusAll.templateToExcel();
    }

    public List<Status> getAllStatus() {
        return statusRepository.findAll();
    }
}
