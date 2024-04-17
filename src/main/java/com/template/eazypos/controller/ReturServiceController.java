package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Retur;
import com.template.eazypos.service.itc.admin.ReturService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/return/service")
@CrossOrigin(origins = "*")
public class ReturServiceController {
    @Autowired
    ReturService returService;

    @PutMapping("/{id}")
    public CommonResponse<Retur> returService(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returService.retur(id));
    }
}
