package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Suplier;
import com.template.eazypos.repository.SuplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelSuplierService {
    @Autowired
    private SuplierRepository suplierRepository;

    public ByteArrayInputStream loadSuplier() throws IOException {
        List<Suplier> supliers = suplierRepository.findAllSuplier();
        return ExcelSuplier.suplierToExcel(supliers);
    }

    public ByteArrayInputStream templateSuplier() throws IOException {
        return ExcelSuplier.templateToExcel();
    }

    public void saveSuplier(MultipartFile file) {
        try {
            List<Suplier> supliersList = ExcelSuplier.excelToSuplier(file.getInputStream());
            suplierRepository.saveAll(supliersList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }

}
