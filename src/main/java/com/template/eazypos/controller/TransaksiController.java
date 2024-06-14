package com.template.eazypos.controller;

import com.template.eazypos.dto.TransaksiBeliDTO;
import com.template.eazypos.dto.TransaksiPenjualanDTO;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.*;
import com.template.eazypos.service.eazypos.dinarpos.TransaksiBeliDinarposService;
import com.template.eazypos.service.eazypos.dinarpos.TransaksiPenjualanDinarposService;
import com.template.eazypos.service.eazypos.excelcom.TransaksiBeliExcelcomService;
import com.template.eazypos.service.eazypos.excelcom.TransaksiPenjualanExcelcomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/transaksi")
@CrossOrigin(origins = "*")
public class TransaksiController {
    @Autowired
    private TransaksiPenjualanExcelcomService transaksiPenjualanService;
    @Autowired
    private TransaksiPenjualanDinarposService transaksiPenjualanDinarposService;

    @Autowired
    private TransaksiBeliExcelcomService transaksiBeliExcelcomService;

    @Autowired
    private TransaksiBeliDinarposService transaksiBeliDinarposService;

    // Endpoint untuk menambahkan transaksi penjualan dari Excelcom
    @PostMapping("/penjualan/excelcom")
    public CommonResponse<Transaksi> addExcelcom(@RequestBody TransaksiPenjualanDTO transaksiPenjualanDTO){
        return ResponseHelper.ok( transaksiPenjualanService.addTransaksi(transaksiPenjualanDTO));
    }

    // Endpoint untuk menambahkan transaksi penjualan dari Dinarpos
    @PostMapping("/penjualan/dinarpos")
    public CommonResponse<Transaksi> addDinarpos(@RequestBody TransaksiPenjualanDTO transaksiPenjualanDTO){
        return ResponseHelper.ok( transaksiPenjualanDinarposService.addTransaksi(transaksiPenjualanDTO));
    }

    // Endpoint untuk menambahkan transaksi pembelian dari Excelcom
    @PostMapping("/pembelian/excelcom")
    public CommonResponse<TransaksiBeli> addExcelcom(@RequestBody TransaksiBeliDTO transaksiBeliDTO){
        return ResponseHelper.ok( transaksiBeliExcelcomService.addTransaksi(transaksiBeliDTO));
    }

    // Endpoint untuk menambahkan transaksi pembelian dari Dinarpos
    @PostMapping("/pembelian/dinarpos")
    public CommonResponse<TransaksiBeli> addDinarpos(@RequestBody TransaksiBeliDTO transaksiBeliDTO){
        return ResponseHelper.ok( transaksiBeliDinarposService.addTransaksi(transaksiBeliDTO));
    }

    // Endpoint untuk mendapatkan transaksi pembelian berdasarkan ID
    @GetMapping("/pembelian/{id}")
    public CommonResponse <TransaksiBeli> get(@PathVariable("id") Long id){
        return ResponseHelper.ok( transaksiBeliDinarposService.getById(id));
    }

    // Endpoint untuk mendapatkan transaksi penjualan berdasarkan ID
    @GetMapping("/penjualan/{id}")
    public CommonResponse <Transaksi> getPenjualan(@PathVariable("id") Long id){
        return ResponseHelper.ok( transaksiPenjualanService.getById(id));
    }

    // Endpoint untuk mendapatkan daftar barang transaksi pembelian dari Dinarpos
    @GetMapping("/barang/pembelian/dinarpos")
    public CommonResponse<List<BarangTransaksiBeli>> getByIdTransaksiDinarpos(@RequestParam("id_transaksi") Long idTransaksi) {
        return ResponseHelper.ok(transaksiBeliDinarposService.getByIdTransaksi(idTransaksi));
    }

    // Endpoint untuk mendapatkan daftar barang transaksi penjualan dari Excelcom
    @GetMapping("/barang/penjualan/excelcom")
    public CommonResponse<List<BarangTransaksi>> getBarangByIdTransaksiExcelcom(@RequestParam("id_transaksi") Long idTransaksi) {
        return ResponseHelper.ok(transaksiPenjualanService.getExcelcomByIdTransaksi(idTransaksi));
    }

    // Endpoint untuk mendapatkan daftar barang transaksi penjualan dari Dinarpos
    @GetMapping("/barang/penjualan/dinarpos")
    public CommonResponse<List<BarangTransaksi>> getBarangByIdTransaksiDinarpos(@RequestParam("id_transaksi") Long idTransaksi) {
        return ResponseHelper.ok(transaksiPenjualanDinarposService.getDinarposByIdTransaksi(idTransaksi));
    }

    // Endpoint untuk mendapatkan daftar barang transaksi pembelian dari Excelcom
    @GetMapping("/barang/pembelian/excelcom")
    public CommonResponse<List<BarangTransaksiBeli>> getByIdTransaksiExcelcom(@RequestParam("id_transaksi") Long idTransaksi) {
        return ResponseHelper.ok(transaksiBeliExcelcomService.getByIdTransaksi(idTransaksi));
    }

    // Endpoint untuk mendapatkan semua transaksi penjualan dari Dinarpos
    @GetMapping("/penjualan/all")
    public CommonResponse<List<Transaksi>> getAllPenjualan() {
        return ResponseHelper.ok(transaksiPenjualanDinarposService.getAll());
    }

    // Endpoint untuk mendapatkan semua transaksi pembelian dari Dinarpos
    @GetMapping("/pembelian/all")
    public CommonResponse<List<TransaksiBeli>> getAllPembelian() {
        return ResponseHelper.ok(transaksiBeliDinarposService.getAll());
    }

    // Endpoint untuk mendapatkan semua transaksi pembelian dari Excelcom berdasarkan bulan dan tahun
    @GetMapping("/pembelian/excelcom/bulan")
    public CommonResponse<List<TransaksiBeli>> getAllPembelianBYMonthAndYearExcelcom(@RequestParam int bulan, @RequestParam int tahun) {
        return ResponseHelper.ok(transaksiBeliExcelcomService.getExcelcomBYMonthAndYear(bulan, tahun));
    }

    // Endpoint untuk mendapatkan semua transaksi pembelian dari Dinarpos berdasarkan bulan dan tahun
    @GetMapping("/pembelian/dinarpos/bulan")
    public CommonResponse<List<TransaksiBeli>> getAllPembelianBYMonthAndYearDinapos(@RequestParam int bulan, @RequestParam int tahun) {
        return ResponseHelper.ok(transaksiBeliDinarposService.getDinarposBYMonthAndYear(bulan, tahun));
    }

    // Endpoint untuk mendapatkan semua transaksi penjualan dari Dinarpos berdasarkan bulan dan tahun
    @GetMapping("/penjualan/dinarpos/bulan")
    public CommonResponse<List<Transaksi>> getAllPenjualanBYMonthAndYearDinapos(@RequestParam int bulan, @RequestParam int tahun) {
        return ResponseHelper.ok(transaksiPenjualanDinarposService.getDinarposBYMonthAndYear(bulan, tahun));
    }

    // Endpoint untuk mendapatkan semua transaksi penjualan dari Excelcom berdasarkan bulan dan tahun
    @GetMapping("/penjualan/excelcom/bulan")
    public CommonResponse<List<Transaksi>> getAllPenjualanBYMonthAndYearExcelcom(@RequestParam int bulan, @RequestParam int tahun) {
        return ResponseHelper.ok(transaksiPenjualanService.getExcelcomBYMonthAndYear(bulan, tahun));
    }

    // Endpoint untuk mendapatkan nomor nota terakhir berdasarkan bulan dan tahun dari transaksi penjualan
    @GetMapping("/last-nota")
    public String getLastNotaByMonthAndYear(@RequestParam int bulan, @RequestParam int tahun) {
        return transaksiPenjualanService.getLastNotaByMonthAndYear(bulan, tahun);
    }

    @GetMapping("/get-transaksi-by-id-tt/{idTt}")
    public List<Transaksi> getTransaksiByIdTt(@PathVariable Long idTt) {
        return transaksiPenjualanService.getTransaksiByIdTt(idTt);
    }

    @GetMapping("/get-barang-transaksi-by-id-transaksi/{idTransaksi}")
    public List<BarangTransaksi> getBarangTransaksiByIdTransaksi(@PathVariable Long idTransaksi) {
        return transaksiPenjualanService.getBarangTransaksiByIdTransaksi(idTransaksi);
    }

}
