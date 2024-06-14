package com.template.eazypos.controller;

import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.BarangTransaksiBeli;
import com.template.eazypos.model.Transaksi;
import com.template.eazypos.model.TransaksiBeli;
import com.template.eazypos.service.eazypos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/laporan")
@CrossOrigin(origins = "*")
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

    // Mendapatkan Laporan Transaksi Salesman Berdasarkan ID
    @GetMapping("/salesman/{id}")
    public CommonResponse<Transaksi> getSalesman(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanSalesmanService.get(id));
    }

    // Mendapatkan Semua Laporan Transaksi Salesman Untuk Excelcom
    @GetMapping("/salesman/excelcom")
    public CommonResponse<List<Transaksi>> getAllSalesmanExcelcom() {
        return ResponseHelper.ok(laporanSalesmanService.getAllExelcom());
    }

    // Mendapatkan Laporan Transaksi Salesman Berdasarkan Tanggal Untuk Excelcom
    @GetMapping("/salesman/tanggal/excelcom")
    public CommonResponse<List<Transaksi>> getAllByTanggalExcelcom(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            @RequestParam("id_salesman") Long idSalesman) {
        return ResponseHelper.ok(laporanSalesmanService.getByTanggalExcelcom(tanggalAwal, tanggalAkhir, idSalesman));
    }

    // Mendapatkan Semua Laporan Transaksi Salesman Untuk Dinarpos
    @GetMapping("/salesman/dinarpos")
    public CommonResponse<List<Transaksi>> getAllSalesmanDinarpos() {
        return ResponseHelper.ok(laporanSalesmanService.getAllDinarpos());
    }

    // Mendapatkan Laporan Transaksi Salesman Berdasarkan Tanggal Untuk Dinarpos
    @GetMapping("/salesman/tanggal/dinarpos")
    public CommonResponse<List<Transaksi>> getAllByTanggalDinarpos(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            @RequestParam("id_salesman") Long idSalesman) {
        return ResponseHelper.ok(laporanSalesmanService.getByTanggalDinarpos(tanggalAwal, tanggalAkhir, idSalesman));
    }

    // Mengedit Laporan Transaksi Salesman
    @PutMapping("/salesman/{id}")
    public CommonResponse<Transaksi> putSalesman(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanSalesmanService.edit(id));
    }

    // Menghapus Laporan Transaksi Salesman Berdasarkan ID
    @DeleteMapping("/salesman/{id}")
    public CommonResponse<?> deleteSalesman(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanSalesmanService.delete(id));
    }

    // Mendapatkan Laporan Barang Transaksi Berdasarkan ID
    @GetMapping("/barang/{id}")
    public CommonResponse<BarangTransaksi> getBarang(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanBarangService.get(id));
    }

    // Mendapatkan Semua Laporan Barang Transaksi Untuk Excelcom
    @GetMapping("/barang/excelcom")
    public CommonResponse<List<BarangTransaksi>> getAllBarangExcelcom() {
        return ResponseHelper.ok(laporanBarangService.getAllExcelcom());
    }

    // Mendapatkan Laporan Barang Transaksi Berdasarkan Tanggal Untuk Excelcom
    @GetMapping("/barang/tanggal/excelcom")
    public CommonResponse<List<BarangTransaksi>> getAllBarangByTanggalExcelcom(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            @RequestParam("barcode_barang") String barcode) {
        return ResponseHelper.ok(laporanBarangService.getByTanggalExcelcom(tanggalAwal, tanggalAkhir, barcode));
    }

    // Mendapatkan Semua Laporan Barang Transaksi Untuk Dinarpos
    @GetMapping("/barang/dinarpos")
    public CommonResponse<List<BarangTransaksi>> getAllBarangDinarpos() {
        return ResponseHelper.ok(laporanBarangService.getAllDinarpos());
    }

    // Mendapatkan Laporan Barang Transaksi Berdasarkan Tanggal Untuk Dinarpos
    @GetMapping("/barang/tanggal/dinarpos")
    public CommonResponse<List<BarangTransaksi>> getAllBarangByTanggalDinarpos(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            @RequestParam("barcode_barang") String barcode) {
        return ResponseHelper.ok(laporanBarangService.getByTanggalDinarpos(tanggalAwal, tanggalAkhir, barcode));
    }

    // Mengedit Laporan Barang Transaksi
    @PutMapping("/barang/{id}")
    public CommonResponse<BarangTransaksi> putBarang(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanBarangService.edit(id));
    }

    // Menghapus Laporan Barang Transaksi Berdasarkan ID
    @DeleteMapping("/barang/{id}")
    public CommonResponse<?> deleteBarang(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanBarangService.delete(id));
    }

    // Mendapatkan Laporan Transaksi Customer Berdasarkan ID
    @GetMapping("/customer/{id}")
    public CommonResponse<Transaksi> getCustomer(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanCustomerService.get(id));
    }

    // Mendapatkan Semua Laporan Transaksi Customer Untuk Excelcom
    @GetMapping("/customer/excelcom")
    public CommonResponse<List<Transaksi>> getAllCustomerExcelcom() {
        return ResponseHelper.ok(laporanCustomerService.getAllExelcom());
    }

    // Mendapatkan Laporan Transaksi Customer Berdasarkan Tanggal Untuk Excelcom
    @GetMapping("/customer/tanggal/excelcom")
    public CommonResponse<List<Transaksi>> getAllCustomerByTanggalExcelcom(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            @RequestParam("id_customer") Long idCustomer) {
        return ResponseHelper.ok(laporanCustomerService.getByTanggalExcelcom(tanggalAwal, tanggalAkhir, idCustomer));
    }

    // Mendapatkan Semua Laporan Transaksi Customer Untuk Dinarpos
    @GetMapping("/customer/dinarpos")
    public CommonResponse<List<Transaksi>> getAllCustomerDinarpos() {
        return ResponseHelper.ok(laporanCustomerService.getAllDinarpos());
    }

    // Mendapatkan Laporan Transaksi Customer Berdasarkan Tanggal Untuk Dinarpos
    @GetMapping("/customer/tanggal/dinarpos")
    public CommonResponse<List<Transaksi>> getAllCustomerByTanggalDinarpos(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            @RequestParam("id_customer") Long idCustomer) {
        return ResponseHelper.ok(laporanCustomerService.getByTanggalDinarpos(tanggalAwal, tanggalAkhir, idCustomer));
    }

    // Menghapus Laporan Transaksi Customer Berdasarkan ID
    @DeleteMapping("/customer/{id}")
    public CommonResponse<?> deleteCustomer(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanCustomerService.delete(id));
    }

    // Mendapatkan Laporan Transaksi Suplier Berdasarkan ID
    @GetMapping("/suplier/{id}")
    public CommonResponse<BarangTransaksiBeli> getSuplier(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanSuplierService.get(id));
    }

    // Mendapatkan Semua Laporan Transaksi Suplier Untuk Excelcom
    @GetMapping("/suplier/excelcom")
    public CommonResponse<List<BarangTransaksiBeli>> getAllSuplierExcelcom() {
        return ResponseHelper.ok(laporanSuplierService.getAllExcelcom());
    }

    // Mendapatkan Laporan Transaksi Suplier Berdasarkan Tanggal Untuk Excelcom
    @GetMapping("/suplier/tanggal/excelcom")
    public CommonResponse<List<TransaksiBeli>> getAllSuplierByTanggalExcelcom(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            @RequestParam("id_suplier") Long idSuplier) {
        return ResponseHelper.ok(laporanSuplierService.getByTanggalExcelcom(tanggalAwal, tanggalAkhir, idSuplier));
    }

    // Mendapatkan Semua Laporan Transaksi Suplier Untuk Dinarpos
    @GetMapping("/suplier/dinarpos")
    public CommonResponse<List<BarangTransaksiBeli>> getAllSuplierDinarpos() {
        return ResponseHelper.ok(laporanSuplierService.getAllDinarpos());
    }

    // Mendapatkan Laporan Transaksi Suplier Berdasarkan Tanggal Untuk Dinarpos
    @GetMapping("/suplier/tanggal/dinarpos")
    public CommonResponse<List<TransaksiBeli>> getAllSuplgetByTanggalDinarpos(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            @RequestParam("id_suplier") Long idSuplier) {
        return ResponseHelper.ok(laporanSuplierService.getByTanggalDinarpos(tanggalAwal, tanggalAkhir, idSuplier));
    }

    // Menghapus Laporan Transaksi Suplier Berdasarkan ID
    @DeleteMapping("/suplier/{id}")
    public CommonResponse<?> deleteSuplier(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanSuplierService.delete(id));
    }

    // Mendapatkan Laporan Transaksi Pembelian Berdasarkan ID
    @GetMapping("/transaksi_beli/{id}")
    public CommonResponse<TransaksiBeli> getTransaksiBeli(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanTransaksiBeliService.get(id));
    }

    // Mendapatkan Semua Laporan Transaksi Pembelian Untuk Excelcom
    @GetMapping("/transaksi_beli/excelcom")
    public CommonResponse<List<TransaksiBeli>> getAllTransaksiBeliExcelcom() {
        return ResponseHelper.ok(laporanTransaksiBeliService.getAllExelcom());
    }

    // Mendapatkan Laporan Transaksi Pembelian Berdasarkan Tanggal Nntuk Excelcom
    @GetMapping("/transaksi_beli/tanggal/excelcom")
    public CommonResponse<List<TransaksiBeli>> getAllTransaksiBeliByTanggalExcelcom(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            @RequestParam("id_suplier") Long idSuplier) {
        return ResponseHelper.ok(laporanTransaksiBeliService.getByTanggalExcelcom(tanggalAwal, tanggalAkhir, idSuplier));
    }

    // Mendapatkan Semua Laporan Transaksi Pembelian Untuk Dinarpos
    @GetMapping("/transaksi_beli/dinarpos")
    public CommonResponse<List<TransaksiBeli>> getAllTransaksiBeliDinarpos() {
        return ResponseHelper.ok(laporanTransaksiBeliService.getAllDinarpos());
    }

    // Mendapatkan Laporan Transaksi Pembelian Berdasarkan Tanggal Untuk Dinarpos
    @GetMapping("/transaksi_beli/tanggal/dinarpos")
    public CommonResponse<List<TransaksiBeli>> getAllTransaksiBeliByTanggalDinarpos(
            @RequestParam(name = "tanggal_awal") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAwal,
            @RequestParam(name = "tanggal_akhir") @DateTimeFormat(pattern = "yyyy-MM-dd") Date tanggalAkhir,
            @RequestParam("id_suplier") Long idSuplier) {
        return ResponseHelper.ok(laporanTransaksiBeliService.getByTanggalDinarpos(tanggalAwal, tanggalAkhir, idSuplier));
    }

    // Mengedit Laporan Transaksi Pembelian Berdasarkan ID
    @PutMapping("/transaksi_beli/{id}")
    public CommonResponse<TransaksiBeli> putTransaksiBeli(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanTransaksiBeliService.edit(id));
    }

    // Menghapus Laporan Transaksi Pembelian Berdasarkan ID
    @DeleteMapping("/transaksi_beli/{id}")
    public CommonResponse<?> deleteTransaksiBeli(@PathVariable("id") Long id) {
        return ResponseHelper.ok(laporanTransaksiBeliService.delete(id));
    }
}
