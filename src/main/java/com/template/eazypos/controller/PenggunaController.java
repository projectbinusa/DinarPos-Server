package com.template.eazypos.controller;

import com.template.eazypos.dto.LoginRequest;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Pengguna;
import com.template.eazypos.service.auth.PenggunaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/pengguna")
@CrossOrigin(origins = "*")
public class PenggunaController {

    @Autowired
    private PenggunaService penggunaService;

    @PostMapping("/login")
    public CommonResponse<?> authenticatePengguna(@RequestBody LoginRequest loginRequest) {
        return ResponseHelper.ok( penggunaService.login(loginRequest));
    }
    @PostMapping("/add")
    public CommonResponse<Pengguna> addPengguna(@RequestBody Pengguna user){
        return ResponseHelper.ok( penggunaService.addPengguna(user));
    }
    @GetMapping("/{id}")
    public CommonResponse <Pengguna> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( penggunaService.get(id ));
    }
    @GetMapping
    public CommonResponse<List<Pengguna>> getAll(){
        return ResponseHelper.ok( penggunaService.getAll());
    }
    @PutMapping("/{id}")
    public CommonResponse<Pengguna> put(@PathVariable("id") Long id , @RequestBody Pengguna user){
        return ResponseHelper.ok( penggunaService.edit(id, user));
    }
    @DeleteMapping("/{id}")
    public CommonResponse<?> delete(@PathVariable("id")  Long id ) {
        return ResponseHelper.ok( penggunaService.delete(id));
    }
}
