package com.template.eazypos.controller;

import com.template.eazypos.dto.BonBarangDTO;
import com.template.eazypos.dto.UpdateBonBarangDTO;
import com.template.eazypos.dto.UpdateDataBonBarangDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.BonBarang;
import com.template.eazypos.service.itc.BonBarangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/bon_barang")
@CrossOrigin(origins = "*")
public class BonBarangController {
    @Autowired
    private BonBarangService bonBarangService;

    // Menambahkan Bon Barang Baru
    @PostMapping("/add")
    public CommonResponse<BonBarang> add(@RequestBody BonBarangDTO bonBarangDTO){
        return ResponseHelper.ok( bonBarangService.add(bonBarangDTO));
    }

    // Mendapatkan Bon Barang Berdasarkan ID
    @GetMapping("/{id}")
    public CommonResponse <BonBarang> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( bonBarangService.getById(id));
    }

    // Mendapatkan Semua Bon Barang
    @GetMapping
    public CommonResponse<List<BonBarang>> getAll(){
        return ResponseHelper.ok( bonBarangService.getAll());
    }

    // Mengedit Bon Barang Berdasarkan ID
    @PutMapping("/{id}")
    public CommonResponse<BonBarang> put(@PathVariable("id") Long id , @RequestBody UpdateBonBarangDTO bonBarangDTO){
        return ResponseHelper.ok( bonBarangService.edit(bonBarangDTO , id));
    }

    // Menghapus Bon Barang Berdasarkan ID
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( bonBarangService.delete(id));
    }

    // Menghapus Data Bon Barang Berdasarkan ID
    @PutMapping("/update/{id}")
    public CommonResponse<BonBarang> put(@PathVariable("id") Long id, @RequestBody UpdateDataBonBarangDTO dataBonBarangDTO) {
        if (dataBonBarangDTO == null) {
            throw new IllegalArgumentException("Update data cannot be null");
        }
        BonBarang updatedBonBarang = bonBarangService.updateBonBarang(dataBonBarangDTO, id);
        return ResponseHelper.ok(updatedBonBarang);
    }
}
