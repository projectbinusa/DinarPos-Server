package com.template.eazypos.controller;

import com.template.eazypos.dto.DealFinishDTO;
import com.template.eazypos.dto.DealGudangDTO;
import com.template.eazypos.dto.DealPODTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.*;
import com.template.eazypos.service.itc.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @GetMapping("/po/salesman")
    public CommonResponse<List<Deal>> getDealPOBySalesman(@RequestParam(name = "id_salesman") Long idSalesman){
        return ResponseHelper.ok(dealService.getDealsBySalesmanId(idSalesman));
    }
    @GetMapping("/finish/salesman")
    public CommonResponse<List<Finish>> getDealFinishBySalesman(@RequestParam(name = "id_salesman") Long idSalesman){
        return ResponseHelper.ok(dealService.getAllFinishBySalesmanId(idSalesman));
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
    @PostMapping(path = "/add/deal_po" , consumes = "multipart/form-data")
    public CommonResponse<Deal> addPO(DealPODTO dealpo , @RequestPart("foto") MultipartFile foto , @RequestPart("file") MultipartFile file) throws IOException {
        return ResponseHelper.ok( dealService.addDealPO(dealpo, foto ,file));
    }
    @PostMapping(path = "/add/deal_finish" , consumes = "multipart/form-data")
    public CommonResponse<Finish> addFinish(DealFinishDTO dealFinishDTO , @RequestPart("bast") MultipartFile bast , @RequestPart("baut") MultipartFile baut , @RequestPart("baso") MultipartFile baso ,  @RequestPart("spk") MultipartFile spk , @RequestPart("ev_dtg") MultipartFile dtg ,  @RequestPart("ev_pro") MultipartFile pro , @RequestPart("ev_fin") MultipartFile fin ,  @RequestPart("file_spk") MultipartFile file) throws IOException {
        return ResponseHelper.ok( dealService.addDealFinish( dealFinishDTO,bast,baut ,baso ,spk ,dtg , pro , fin ,file));
    }
}
