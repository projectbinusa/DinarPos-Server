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
@RequestMapping("api/return/dinarpos")
@CrossOrigin(origins = "*")
public class ReturnDinarposController {
    @Autowired
    private ReturnPenjualanService returnPenjualanService;

    @Autowired
    private ReturnPembelianService returnPembelianService;

    @Autowired
    private ReturnPembelianBarangService returnPembelianBarangService;

    @Autowired
    private ReturnPenjualanBarangService returnPenjualanBarangService;

    // Get Penjualan Transaction Berdasarkan ID
    @GetMapping("/penjualan/{id}")
    public CommonResponse<Transaksi> getPenjualan(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanService.get(id));
    }

    // Get Pembelian Transaction Berdasarkan ID
    @GetMapping("/pembelian/{id}")
    public CommonResponse<TransaksiBeli> getPembelian(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianService.get(id));
    }

    // Get Barang Pembelian Transaction Berdasarkan ID
    @GetMapping("/barang_pembelian/{id}")
    public CommonResponse<BarangTransaksiBeli> getBarangPembelian(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianBarangService.get(id));
    }

    // Get Barang Penjualan Transaction Berdasarkan ID
    @GetMapping("/barang_penjualan/{id}")
    public CommonResponse<BarangTransaksi> getBarangPenjualan(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanBarangService.get(id));
    }

    // Mendapatkan Semua Data Penjualan Transaction
    @GetMapping("/penjualan")
    public CommonResponse<List<Transaksi>> getAllPenjualanDinarpos() {
        return ResponseHelper.ok(returnPenjualanService.getAllDinarpos());
    }

    // Update Retur Penjualan Transaction
    @PutMapping("/retur_penjualan/{id}")
    public CommonResponse<Transaksi> returTransaksi(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanService.returnHistoriTransaksi(id));
    }

    // Update Retur Pembelian Transaction
    @PutMapping("/retur_pembelian/{id}")
    public CommonResponse<TransaksiBeli> returTransaksiBeli(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianService.returnHistoriTransaksiPembelian(id));
    }

    // Update Retur Barang Pembelian Transaction
    @PutMapping("/retur_barang_pembelian/{id}")
    public CommonResponse<BarangTransaksiBeli> returBarangTransaksiBeli(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianBarangService.retur(id));
    }

    // Update Retur Barang Penjualan Transaction
    @PutMapping("/retur_barang_penjualan/{id}")
    public CommonResponse<BarangTransaksi> returBarangTransaksi(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanBarangService.retur(id));
    }

    // Mendapatkan Semua Data Pembelian Transaction
    @GetMapping("/pembelian")
    public CommonResponse<List<TransaksiBeli>> getAllPembelianDinarpos() {
        return ResponseHelper.ok(returnPembelianService.getAllDinarpos());
    }

    // Get all Semua Barang Penjualan Transactions
    @GetMapping("/barang_penjualan")
    public CommonResponse<List<BarangTransaksi>> getAllDinarpos() {
        return ResponseHelper.ok(returnPenjualanBarangService.getAllDinarpos());
    }

    // Get all Semua Barang Pembelian Transactions
    @GetMapping("/barang_pembelian")
    public CommonResponse<List<BarangTransaksiBeli>> getAllBarangPembelianDinarpos() {
        return ResponseHelper.ok(returnPembelianBarangService.getAllDinarpos());
    }

    // Delete Penjualan Transaction Berdasarkan ID
    @DeleteMapping("/penjualan/{id}")
    public CommonResponse<?> deletePenjualan(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanService.delete(id));
    }

    // Delete Pembelian Transaction Berdasarkan ID
    @DeleteMapping("/pembelian/{id}")
    public CommonResponse<?> deletePembelian(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianService.delete(id));
    }

    // Delete Barang Pembelian Transaction Berdasarkan ID
    @DeleteMapping("/barang_pembelian/{id}")
    public CommonResponse<?> deleteBarangPembelian(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPembelianBarangService.delete(id));
    }

    // Delete Barang Penjualan Transaction Berdasarkan ID
    @DeleteMapping("/barang_penjualan/{id}")
    public CommonResponse<?> deleteBarangPenjualan(@PathVariable("id") Long id) {
        return ResponseHelper.ok(returnPenjualanBarangService.delete(id));
    }
}
