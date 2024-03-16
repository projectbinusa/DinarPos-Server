package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.BarangTransaksiBeli;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.service.eazypos.ReturnPembelianBarangService;
import com.template.eazypos.service.eazypos.ReturnPembelianService;
import com.template.eazypos.service.eazypos.ReturnPenjualanBarangService;
import com.template.eazypos.service.eazypos.ReturnPenjualanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/return/excelcom")
@CrossOrigin(origins = "*")
public class ReturnExcelcomController {
    @Autowired
    private ReturnPenjualanService returnPenjualanService;

    @Autowired
    private ReturnPembelianService returnPembelianService;

    @Autowired
    private ReturnPembelianBarangService returnPembelianBarangService;

    @Autowired
    private ReturnPenjualanBarangService returnPenjualanBarangService;

    @GetMapping("/penjualan/{id}")
    public CommonResponse<Transaksi> getPenjualan(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanService.get(id));
    }

    @GetMapping("/pembelian/{id}")
    public CommonResponse<TransaksiBeli> getPembelian(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianService.get(id));
    }

    @GetMapping("/barang_pembelian/{id}")
    public CommonResponse<BarangTransaksiBeli> getBarangPembelian(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianBarangService.get(id));
    }

    @GetMapping("/barang_penjualan/{id}")
    public CommonResponse<BarangTransaksi> getBarangPenjualan(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanBarangService.get(id));
    }

    @GetMapping("/penjualan")
    public CommonResponse<List<Transaksi>> getAllPenjualanExcelcom() {
        return ResponseHelper.ok(returnPenjualanService.getAllExcelcom());
    }

    @GetMapping("/pembelian")
    public CommonResponse<List<TransaksiBeli>> getAllPembelianExcelcom() {
        return ResponseHelper.ok(returnPembelianService.getAllExcelcom());
    }

    @GetMapping("/barang_penjualan")
    public CommonResponse<List<BarangTransaksi>> getAllExcelcom() {
        return ResponseHelper.ok(returnPenjualanBarangService.getAllExcelcom());
    }

    @GetMapping("/barang_pembelian")
    public CommonResponse<List<BarangTransaksiBeli>> getAllBarangPembelianExcelcom() {
        return ResponseHelper.ok(returnPembelianBarangService.getAllExcelcom());
    }

    @DeleteMapping("/penjualan/{id}")
    public CommonResponse<?> deletePenjualan(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanService.delete(id));
    }
    @DeleteMapping("/pembelian/{id}")
    public CommonResponse<?> deletePembelian(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianService.delete(id));
    }
    @DeleteMapping("/barang_pembelian/{id}")
    public CommonResponse<?> deleteBarangPembelian(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianBarangService.delete(id));
    }

    @DeleteMapping("/barang_penjualan/{id}")
    public CommonResponse<?> deleteBarangPenjualan(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanBarangService.delete(id));
    }
}
