package com.template.eazypos.controller;

import com.template.eazypos.dto.DealGudangDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Deal;
import com.template.eazypos.model.Finish;
import com.template.eazypos.model.Pengguna;
import com.template.eazypos.model.Planning;
import com.template.eazypos.service.itc.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/deal")
@CrossOrigin(origins = "*")
public class DealController {
    @Autowired
    private DealService dealService;

    @GetMapping("/po")
    public CommonResponse<List<Deal>> getDealPO(){
        return ResponseHelper.ok(dealService.getDealPO());
    }
    @GetMapping("/finish")
    public CommonResponse<List<Finish>> getDealFinish(){
        return ResponseHelper.ok(dealService.getDealFinish());
    }
    @GetMapping("/po/customer")
    public CommonResponse<List<Deal>> getDealPOByCustomer(@RequestParam(name = "id_customer") Long idCustomer){
        return ResponseHelper.ok(dealService.getDealPOByCustomer(idCustomer));
    }
    @GetMapping("/finish/customer")
    public CommonResponse<List<Finish>> getDealFinishByCustomer(@RequestParam(name = "id_customer") Long idCustomer){
        return ResponseHelper.ok(dealService.getDealFinishByCustomer(idCustomer));
    }
    @PutMapping("/{id}")
    public CommonResponse<Deal> put(@PathVariable("id") Long id, @RequestBody DealGudangDTO dto) {
        return ResponseHelper.ok(dealService.edit(id, dto));
    }
}
