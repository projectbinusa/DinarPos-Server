package com.template.eazypos.controller;

import com.template.eazypos.dto.BonBarangDTO;
import com.template.eazypos.dto.CustomerCPDTO;
import com.template.eazypos.dto.UpdateBonBarangDTO;
import com.template.eazypos.dto.UpdateDataBonBarangDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.BonBarang;
import com.template.eazypos.model.CustomerCP;
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

    @PostMapping("/add")
    public CommonResponse<BonBarang> add(@RequestBody BonBarangDTO bonBarangDTO){
        return ResponseHelper.ok( bonBarangService.add(bonBarangDTO));
    }
    @GetMapping("/{id}")
    public CommonResponse <BonBarang> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( bonBarangService.getById(id));
    }
    @GetMapping
    public CommonResponse<List<BonBarang>> getAll(){
        return ResponseHelper.ok( bonBarangService.getAll());
    }
    @PutMapping("/{id}")
    public CommonResponse<BonBarang> put(@PathVariable("id") Long id , @RequestBody UpdateBonBarangDTO bonBarangDTO){
        return ResponseHelper.ok( bonBarangService.edit(bonBarangDTO , id));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( bonBarangService.delete(id));
    }

    @PutMapping("/update/{id}")
    public CommonResponse<BonBarang> put(@PathVariable("id") Long id, @RequestBody UpdateDataBonBarangDTO dataBonBarangDTO) {
        return ResponseHelper.ok(bonBarangService.updateBonBarang(dataBonBarangDTO, id));
    }
}
