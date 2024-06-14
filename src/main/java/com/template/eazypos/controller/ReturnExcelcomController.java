package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.*;
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

    // Mendapatkan Penjualan Transaksi Berdasarkan ID
    @GetMapping("/penjualan/{id}")
    public CommonResponse<Transaksi> getPenjualan(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanService.get(id));
    }

    // Mendapatkan Pembelian Transaksi Berdasarkan ID
    @GetMapping("/pembelian/{id}")
    public CommonResponse<TransaksiBeli> getPembelian(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianService.get(id));
    }

    // Mendapatkan Barang Penjualan Untuk Transaksi Tertentu
    @GetMapping("/penjualan/barang")
    public CommonResponse<List<BarangTransaksi>> getReturnBarangPenjualan(@RequestParam("id_transaksi") Long  id_transaksi) {
        return ResponseHelper.ok(returnPenjualanBarangService.getAllBarangReturn(id_transaksi));
    }

    // Mendapatkan Barang Pembelian Untuk Transaksi Tertentu
    @GetMapping("/pembelian/barang")
    public CommonResponse<List<BarangTransaksiBeli>> getReturnBarangPembelian(@RequestParam("id_transaksi_beli") Long  id_transaksi) {
        return ResponseHelper.ok(returnPembelianBarangService.getAllBarangReturn(id_transaksi));
    }

    // Mendapatkan Barang Pembelian Untuk Transaksi Berdasarkan ID
    @GetMapping("/barang_pembelian/{id}")
    public CommonResponse<BarangTransaksiBeli> getBarangPembelian(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianBarangService.get(id));
    }

    // Mendapatkan Barang Penjualan Untuk Transaksi Berdasarkan ID
    @GetMapping("/barang_penjualan/{id}")
    public CommonResponse<BarangTransaksi> getBarangPenjualan(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanBarangService.get(id));
    }

    // Mendapatkan Semua Data Penjualan
    @GetMapping("/penjualan")
    public CommonResponse<List<Transaksi>> getAllPenjualanExcelcom() {
        return ResponseHelper.ok(returnPenjualanService.getAllExcelcom());
    }

    // Mendapatkan Semua Data Pembelian
    @GetMapping("/pembelian")
    public CommonResponse<List<TransaksiBeli>> getAllPembelianExcelcom() {
        return ResponseHelper.ok(returnPembelianService.getAllExcelcom());
    }

    // Mendapatkan Semua Data Barang Penjualan
    @GetMapping("/barang_penjualan")
    public CommonResponse<List<BarangTransaksi>> getAllExcelcom() {
        return ResponseHelper.ok(returnPenjualanBarangService.getAllExcelcom());
    }

    // Update Retur Transaksi Penjualan Berdasarkan ID
    @PutMapping("/retur_penjualan/{id}")
    public CommonResponse<Transaksi> returTransaksi(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanService.put(id));
    }

    // Update Retur Transaksi Pembelian Berdasarkan ID
    @PutMapping("/retur_pembelian/{id}")
    public CommonResponse<TransaksiBeli> returTransaksiBeli(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianService.put(id));
    }

    // Update Retur Transaksi Barang Pembelian Berdasarkan ID
    @PutMapping("/retur_barang_pembelian/{id}")
    public CommonResponse<BarangTransaksiBeli> returBarangTransaksiBeli(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianBarangService.retur(id));
    }

    // Update Retur Transaksi Barang Penjualan Berdasarkan ID
    @PutMapping("/retur_barang_penjualan/{id}")
    public CommonResponse<BarangTransaksi> returBarangTransaksi(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanBarangService.retur(id));
    }

    // Mendapatkan Semua Data Barang Pembelian
    @GetMapping("/barang_pembelian")
    public CommonResponse<List<BarangTransaksiBeli>> getAllBarangPembelianExcelcom() {
        return ResponseHelper.ok(returnPembelianBarangService.getAllExcelcom());
    }

    // Hapus Transaksi Penjualan Berdasarkan ID
    @DeleteMapping("/penjualan/{id}")
    public CommonResponse<?> deletePenjualan(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanService.delete(id));
    }

    // Hapus Transaksi Pembelian Berdasarkan ID
    @DeleteMapping("/pembelian/{id}")
    public CommonResponse<?> deletePembelian(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianService.delete(id));
    }

    // Hapus Transaksi Barang Pembelian Berdasarkan ID
    @DeleteMapping("/barang_pembelian/{id}")
    public CommonResponse<?> deleteBarangPembelian(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianBarangService.delete(id));
    }
    // Hapus Transaksi Barang Penjualan Berdasarkan ID
    @DeleteMapping("/barang_penjualan/{id}")
    public CommonResponse<?> deleteBarangPenjualan(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanBarangService.delete(id));
    }
}
