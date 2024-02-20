package com.template.eazypos.controller;

import com.template.eazypos.dto.StokMasukDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.StokMasuk;
import com.template.eazypos.service.eazypos.StokMasukService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/stok_masuk")
@CrossOrigin(origins = "http://localhost:3000")
public class StokMasukController {
    @Autowired
    private StokMasukService stokMasukService;

    @PostMapping("/add")
    public CommonResponse<StokMasuk> add(@RequestBody StokMasukDTO stokMasukDTO){
        return ResponseHelper.ok( stokMasukService.add(stokMasukDTO));
    }
    @GetMapping("/{id}")
    public CommonResponse <StokMasuk> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( stokMasukService.get(id));
    }
    @GetMapping
    public CommonResponse<List<StokMasuk>> getAll(){
        return ResponseHelper.ok( stokMasukService.getAll());
    }
    @PutMapping("/{id}")
    public CommonResponse<StokMasuk> put(@PathVariable("id") Long id , @RequestBody StokMasukDTO stokMasukDTO){
        return ResponseHelper.ok( stokMasukService.edit(stokMasukDTO , id));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( stokMasukService.delete(id));
    }
}
