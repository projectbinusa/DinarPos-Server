package com.template.eazypos.controller;

import com.template.eazypos.dto.PasswordDTO;
import com.template.eazypos.dto.TeknisiDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Pengguna;
import com.template.eazypos.model.Teknisi;
import com.template.eazypos.service.itc.admin.UbahPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/ubah_password")
@CrossOrigin(origins = "*")
public class UbahPasswordController {
    @Autowired
    private UbahPasswordService ubahPasswordService;

    @PutMapping("/{id}")
    public CommonResponse<Pengguna> put(@PathVariable("id") Long id , @RequestBody PasswordDTO passwordDTO){
        return ResponseHelper.ok( ubahPasswordService.ubahPass(passwordDTO , id));
    }
}
