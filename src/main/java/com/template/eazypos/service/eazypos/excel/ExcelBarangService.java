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

    // Mengambil daftar barang dari repository dan mengonversinya menjadi file Excel
    public ByteArrayInputStream loadBarang() throws IOException {
        List<Barang> barangs = barangRepository.findAllBarang();
        ByteArrayInputStream in = ExcelBarang.barangToExcel(barangs);
        return in;
    }

    // Menghasilkan template Excel untuk barang berdasarkan data dari repository
    public ByteArrayInputStream templateBarang() throws IOException {
        List<Barang> barangs = barangRepository.findAllBarang();
        ByteArrayInputStream in = ExcelBarang.templateToExcel(barangs);
        return in;
    }

    // Menerima file Excel dan menyimpan data barang yang terkandung di dalamnya ke dalam repository

}
