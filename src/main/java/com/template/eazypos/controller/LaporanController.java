package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.BarangTransaksiBeli;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.service.eazypos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/laporan")
@CrossOrigin(origins = "http://localhost:3000")
public class LaporanController {
    @Autowired
    private LaporanSalesmanService laporanSalesmanService;

    @Autowired
    private LaporanBarangService laporanBarangService;

    @Autowired
    private LaporanCustomerService laporanCustomerService;

    @Autowired
    private LaporanSuplierService laporanSuplierService;

    @Autowired
    private LaporanTransaksiBeliService laporanTransaksiBeliService;


    @GetMapping("/salesman/{id}")
    public CommonResponse<Transaksi> getSalesman(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanSalesmanService.get(id));
    }

    @GetMapping("/salesman/excelcom")
    public CommonResponse<List<Transaksi>> getAllSalesmanExcelcom(int bulan) {
        return ResponseHelper.ok(laporanSalesmanService.getAllExelcom(bulan));
    }

    @GetMapping("/salesman/dinarpos")
    public CommonResponse<List<Transaksi>> getAllSalesmanDinarpos(int bulan) {
        return ResponseHelper.ok(laporanSalesmanService.getAllDinarpos(bulan));
    }

    @PutMapping("/salesman/{id}")
    public CommonResponse<Transaksi> putSalesman(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanSalesmanService.edit(id));
    }

    @DeleteMapping("/salesman/{id}")
    public CommonResponse<?> deleteSalesman(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanSalesmanService.delete(id));
    }

    @GetMapping("/barang/{id}")
    public CommonResponse<BarangTransaksi> getBarang(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanBarangService.get(id));
    }

    @GetMapping("/barang/excelcom")
    public CommonResponse<List<BarangTransaksi>> getAllBarangExcelcom(int bulan) {
        return ResponseHelper.ok(laporanBarangService.getAllExcelcom(bulan));
    }

    @GetMapping("/barang/dinarpos")
    public CommonResponse<List<BarangTransaksi>> getAllBarangDinarpos(int bulan) {
        return ResponseHelper.ok(laporanBarangService.getAllDinarpos(bulan));
    }

    @PutMapping("/barang/{id}")
    public CommonResponse<BarangTransaksi> putBarang(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanBarangService.edit(id));
    }

    @DeleteMapping("/barang/{id}")
    public CommonResponse<?> deleteBarang(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanSalesmanService.delete(id));
    }

    @GetMapping("/customer/{id}")
    public CommonResponse<Transaksi> getCustomer(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanCustomerService.get(id));
    }

    @GetMapping("/customer/excelcom")
    public CommonResponse<List<Transaksi>> getAllCustomerExcelcom(int bulan) {
        return ResponseHelper.ok(laporanCustomerService.getAllExelcom(bulan));
    }

    @GetMapping("/customer/dinarpos")
    public CommonResponse<List<Transaksi>> getAllCustomerDinarpos(int bulan) {
        return ResponseHelper.ok(laporanCustomerService.getAllDinarpos(bulan));
    }

    @DeleteMapping("/customer/{id}")
    public CommonResponse<?> deleteCustomer(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanCustomerService.delete(id));
    }
    @GetMapping("/suplier/{id}")
    public CommonResponse<BarangTransaksiBeli> getSuplier(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanSuplierService.get(id));
    }

    @GetMapping("/suplier/excelcom")
    public CommonResponse<List<BarangTransaksiBeli>> getAllSuplierxcelcom(int bulan) {
        return ResponseHelper.ok(laporanSuplierService.getAllExcelcom(bulan));
    }

    @GetMapping("/suplier/dinarpos")
    public CommonResponse<List<BarangTransaksiBeli>> getAllSuplierDinarpos(int bulan) {
        return ResponseHelper.ok(laporanSuplierService.getAllDinarpos(bulan));
    }


    @DeleteMapping("/suplier/{id}")
    public CommonResponse<?> deleteSuplier(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanSuplierService.delete(id));
    }
    @GetMapping("/transaksi_beli/{id}")
    public CommonResponse<TransaksiBeli> getTransaksiBeli(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanTransaksiBeliService.get(id));
    }

    @GetMapping("/transaksi_beli/excelcom")
    public CommonResponse<List<TransaksiBeli>> getAllTransaksiBeliExcelcom(int bulan) {
        return ResponseHelper.ok(laporanTransaksiBeliService.getAllExelcom(bulan));
    }

    @GetMapping("/transaksi_beli/dinarpos")
    public CommonResponse<List<TransaksiBeli>> getAllTransaksiBeliDinarpos(int bulan) {
        return ResponseHelper.ok(laporanTransaksiBeliService.getAllDinarpos(bulan));
    }

    @PutMapping("/transaksi_beli/{id}")
    public CommonResponse<TransaksiBeli> putTransaksiBeli(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanTransaksiBeliService.edit(id));
    }

    @DeleteMapping("/transaksi_beli/{id}")
    public CommonResponse<?> deleteTransaksiBeli(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanTransaksiBeliService.delete(id));
    }
}
