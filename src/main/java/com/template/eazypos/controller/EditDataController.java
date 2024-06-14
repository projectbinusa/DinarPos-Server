package com.template.eazypos.controller;

import com.template.eazypos.dto.*;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.PoinHistory;
import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.service.itc.admin.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/edit_data")
@CrossOrigin(origins = "*")
public class EditDataController {

    @Autowired
    private DataService dataService;

    // Mengedit Biaya Service Berdasarkan ID
    @PutMapping("/update_biaya_service/{id}")
    public CommonResponse<ServiceBarang> editBiayaService(@RequestBody EditBiayaServiceDTO editBiayaServiceDTO, @PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.editBiayaService(editBiayaServiceDTO, id));
    }

    // Mengedit Poin History Berdasarkan ID
    @PutMapping("/update_poin_history/{id}")
    public CommonResponse<PoinHistory> editPoinHistory(@RequestBody EditPoinDTO editPoinDTO, @PathVariable("id") String id) {
        return ResponseHelper.ok(dataService.editPoinHistory(editPoinDTO, id));
    }

    // Mengedit Tanda Terima Service Berdasarkan ID
    @PutMapping("/update_tt_service/{id}")
    public CommonResponse<ServiceBarang> editTandaTerima(@RequestBody EditIdTtDTO editIdTtDTO, @PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.editTandaTerima(editIdTtDTO, id));
    }

    // Mengedit Status Tanda Terima Service Berdasarkan ID
    @PutMapping("/update_status_tt_service/{id}")
    public CommonResponse<ServiceBarang> editStatusTandaTerima(@RequestBody EditStatusTtDTO editStatusTtDTO, @PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.editStatusTandaTerima(editStatusTtDTO, id));
    }

    // Menghapus Status Berdasarkan ID
    @DeleteMapping("/delete/{id}")
    public CommonResponse<?> deleteStatus(@PathVariable("id") Long id) {
        return ResponseHelper.ok(dataService.deleteStatus(id));
    }
}
