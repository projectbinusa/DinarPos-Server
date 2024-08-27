package com.template.eazypos.controller;

import com.template.eazypos.dto.IjinDTO;
import com.template.eazypos.dto.KunjunganDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Ijin;
import com.template.eazypos.model.Kunjungan;
import com.template.eazypos.service.itc.IjinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/ijin")
@CrossOrigin(origins = "*")
public class IjinController {
    @Autowired
    IjinService ijinService;

    @GetMapping
    public CommonResponse<List<Ijin>> getAll() {
        return ResponseHelper.ok(ijinService.getAll());
    }
    @GetMapping("/{id}")
    public CommonResponse <Ijin> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( ijinService.getById(id));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( ijinService.delete(id));
    }
    @PostMapping(path = "/add" , consumes = "multipart/form-data")
    public CommonResponse<Ijin> add(IjinDTO ijinDTO , @RequestPart("foto") MultipartFile multipartFile) throws IOException {
        return ResponseHelper.ok( ijinService.add(ijinDTO, multipartFile));
    }
    @PutMapping(path = "/{id}" , consumes = "multipart/form-data")
    public CommonResponse<Ijin> edit(IjinDTO ijinDTO , @PathVariable("id") Long id , @RequestPart("foto")MultipartFile multipartFile) throws IOException {
        return ResponseHelper.ok( ijinService.edit(ijinDTO, id, multipartFile));
    }
}
