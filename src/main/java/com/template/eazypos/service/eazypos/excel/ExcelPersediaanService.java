package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Persediaan;
import com.template.eazypos.repository.PersediaanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelPersediaanService {

    @Autowired
    private PersediaanRepository persediaanRepository;

    public ByteArrayInputStream loadPersediaan() throws IOException {
        List<Persediaan> persediaans = persediaanRepository.findAll();
        return ExcelPersediaan.persediaanToExcel(persediaans);
    }

    public ByteArrayInputStream templatePersediaan() throws IOException {
        return ExcelPersediaan.templateToExcel();
    }

    public void savePersediaan(MultipartFile file) {
        try {
            List<Persediaan> persediaanList = ExcelPersediaan.excelToPersediaan(file.getInputStream());
            persediaanRepository.saveAll(persediaanList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }
}
