package com.template.eazypos.controller;

import com.template.eazypos.dto.TransaksiBeliDTO;
import com.template.eazypos.dto.TransaksiPenjualanDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Barang;
import com.template.eazypos.model.BarangTransaksiBeli;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.service.eazypos.dinarpos.TransaksiBeliDinarposService;
import com.template.eazypos.service.eazypos.dinarpos.TransaksiPenjualanDinarposService;
import com.template.eazypos.service.eazypos.excelcom.TransaksiBeliExcelcomService;
import com.template.eazypos.service.eazypos.excelcom.TransaksiPenjualanExcelcomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transaksi")
@CrossOrigin(origins = "http://localhost:3000")
public class TransaksiController {
    @Autowired
    private TransaksiPenjualanExcelcomService transaksiPenjualanService;
    @Autowired
    private TransaksiPenjualanDinarposService transaksiPenjualanDinarposService;

    @Autowired
    private TransaksiBeliExcelcomService transaksiBeliExcelcomService;

    @Autowired
    private TransaksiBeliDinarposService transaksiBeliDinarposService;

    @PostMapping("/penjualan/excelcom")
    public CommonResponse<Transaksi> addExcelcom(@RequestBody TransaksiPenjualanDTO transaksiPenjualanDTO){
        return ResponseHelper.ok( transaksiPenjualanService.addTransaksi(transaksiPenjualanDTO));
    }
    @PostMapping("/penjualan/dinarpos")
    public CommonResponse<Transaksi> addDinarpos(@RequestBody TransaksiPenjualanDTO transaksiPenjualanDTO){
        return ResponseHelper.ok( transaksiPenjualanDinarposService.addTransaksi(transaksiPenjualanDTO));
    }
    @PostMapping("/pembelian/excelcom")
    public CommonResponse<TransaksiBeli> addExcelcom(@RequestBody TransaksiBeliDTO transaksiBeliDTO){
        return ResponseHelper.ok( transaksiBeliExcelcomService.addTransaksiBeli(transaksiBeliDTO));
    }
    @PostMapping("/pembelian/dinarpos")
    public CommonResponse<TransaksiBeli> addDinarpos(@RequestBody TransaksiBeliDTO transaksiBeliDTO){
        return ResponseHelper.ok( transaksiBeliDinarposService.addTransaksiBeli(transaksiBeliDTO));
    }
    @GetMapping("/pembelian/{id}")
    public CommonResponse <TransaksiBeli> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( transaksiBeliDinarposService.getById(id));
    }
    @GetMapping("barang/pembelian/dinarpos")
    public CommonResponse <List<BarangTransaksiBeli>> getByIdTransaksiDinarpos(@RequestParam("id_transaksi") Long  idTransaksi){
        return ResponseHelper.ok( transaksiBeliDinarposService.getByIdTransaksi(idTransaksi));
    }
    @GetMapping("barang/pembelian/excelcom")
    public CommonResponse <List<BarangTransaksiBeli>> getByIdTransaksiExcelcom(@RequestParam("id_transaksi") Long  idTransaksi){
        return ResponseHelper.ok( transaksiBeliExcelcomService.getByIdTransaksi(idTransaksi));
    }
    @GetMapping("/penjualan/all")
    public CommonResponse <List<Transaksi>> getAllPenjualan(){
        return ResponseHelper.ok( transaksiPenjualanDinarposService.getAll());
    }
    @GetMapping("/pembelian/all")
    public CommonResponse <List<TransaksiBeli>> getAllPembelian(){
        return ResponseHelper.ok( transaksiBeliDinarposService.getAll());
    }
    @GetMapping("/pembelian/excelcom/bulan")
    public CommonResponse <List<TransaksiBeli>> getAllPembelianBYMonthAndYearExcelcom(int bulan , int tahun){
        return ResponseHelper.ok( transaksiBeliExcelcomService.getExcelcomBYMonthAndYear(bulan, tahun));
    }
    @GetMapping("/pembelian/dinarpos/bulan")
    public CommonResponse <List<TransaksiBeli>> getAllPembelianBYMonthAndYearDinapos(int bulan , int tahun){
        return ResponseHelper.ok( transaksiBeliDinarposService.getDinarposBYMonthAndYear(bulan, tahun));
    }
    @GetMapping("/penjualan/dinarpos/bulan")
    public CommonResponse <List<Transaksi>> getAllPenjualanBYMonthAndYearDinapos(int bulan , int tahun){
        return ResponseHelper.ok( transaksiPenjualanDinarposService.getDinarposBYMonthAndYear(bulan, tahun));
    }
    @GetMapping("/penjualan/excelcom/bulan")
    public CommonResponse <List<Transaksi>> getAllPenjualanBYMonthAndYearExcelcom(int bulan , int tahun){
        return ResponseHelper.ok( transaksiPenjualanService.getExcelcomBYMonthAndYear(bulan, tahun));
    }



}
