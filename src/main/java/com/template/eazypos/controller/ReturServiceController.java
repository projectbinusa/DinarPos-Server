package com.template.eazypos.controller;

import com.template.eazypos.dto.AddServiceDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Retur;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.service.itc.admin.ReturService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/return/service")
@CrossOrigin(origins = "*")
public class ReturServiceController {
    @Autowired
    ReturService returService;

    @PostMapping("/{id}")
    public CommonResponse<ServiceBarang> returService(@PathVariable("id") Long id , @RequestBody AddServiceDTO addServiceDTO) {
        return ResponseHelper.ok(returService.retur(id , addServiceDTO));
    }
    @GetMapping
    public CommonResponse<List<Retur>> getAll(){
        return ResponseHelper.ok( returService.getAll());
    }
}
