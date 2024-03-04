package com.template.eazypos.service.eazypos.excel;

import com.template.eazypos.model.Barang;
import com.template.eazypos.repository.BarangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelBarangService {
    @Autowired
    private BarangRepository barangRepository;

    public ByteArrayInputStream loadBarang() throws IOException {
        List<Barang> barangs = barangRepository.findAllBarang();
        ByteArrayInputStream in = ExcelBarang.barangToExcel(barangs);
        return in;
    }
    public ByteArrayInputStream templateBarang() throws IOException {
        List<Barang> barangs = barangRepository.findAllBarang();
        ByteArrayInputStream in = ExcelBarang.templateToExcel(barangs);
        return in;
    }
    public void saveBarang(MultipartFile file) {
        try {
            List<Barang> barangList = ExcelBarang.excelToBarang(file.getInputStream());
            barangRepository.saveAll(barangList);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }


}
