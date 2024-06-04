package com.template.eazypos.controller;

import com.template.eazypos.dto.StokMasukDTO;
import com.template.eazypos.dto.TeknisiDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.StokMasuk;
import com.template.eazypos.model.Teknisi;
import com.template.eazypos.repository.TeknisiRepository;
import com.template.eazypos.service.itc.TeknisiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/teknisi")
@CrossOrigin(origins = "*")
public class TeknisiController {
    @Autowired
    private TeknisiService teknisiService;

    @PostMapping("/add")
    public CommonResponse<Teknisi> add(@RequestBody TeknisiDTO teknisiDTO){
        return ResponseHelper.ok( teknisiService.add(teknisiDTO));
    }
    @GetMapping("/{id}")
    public CommonResponse <Teknisi> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( teknisiService.getById(id));
    }
    @GetMapping("/username")
    public CommonResponse <Teknisi> getByUsername(@RequestParam("username") String username){
        return ResponseHelper.ok( teknisiService.getByNama(username));
    }
    @GetMapping
    public CommonResponse<List<Teknisi>> getAll(){
        return ResponseHelper.ok( teknisiService.getAll());
    }
    @PutMapping("/{id}")
    public CommonResponse<Teknisi> put(@PathVariable("id") Long id , @RequestBody TeknisiDTO teknisiDTO){
        return ResponseHelper.ok( teknisiService.put(teknisiDTO , id));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( teknisiService.delete(id));
    }


}
