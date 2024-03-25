package com.template.eazypos.controller;

import com.template.eazypos.dto.AddServiceDTO;
import com.template.eazypos.dto.StokKeluarDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.model.StokKeluar;
import com.template.eazypos.service.itc.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/service")
@CrossOrigin(origins = "*")
public class ServiceController {
    @Autowired
    private DataService dataService;

    @PostMapping("/add")
    public CommonResponse<ServiceBarang> add(@RequestBody AddServiceDTO addServiceDTO) {
        return ResponseHelper.ok(dataService.addService(addServiceDTO));
    }
}
