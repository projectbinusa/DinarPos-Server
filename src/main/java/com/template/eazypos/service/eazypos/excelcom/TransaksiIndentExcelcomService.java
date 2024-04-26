package com.template.eazypos.service.eazypos.excelcom;

import com.template.eazypos.dto.BarangTransaksiDTO;
import com.template.eazypos.dto.TransaksiPenjualanDTO;
import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.*;
import com.template.eazypos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TransaksiIndentExcelcomService {
    @Autowired
    private TransaksiIndentRepository transaksiIndentRepository;

    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private BarangTransaksiRepository barangTransaksiRepository;

    @Autowired
    private BarangTransaksiIndentRepository barangTransaksiIndentRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalesmanRepository markettingRepository;
    @Autowired
    private BarangRepository barangRepository;

    public TransaksiIndent addTransaksi(TransaksiPenjualanDTO transaksiDTO) {
        Date now = new Date();
        String not = getNoNotaTransaksi() ; // method generateNotaNumber() menghasilkan nomor nota baru
        Customer customer = customerRepository.findById(transaksiDTO.getIdCustomer()).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        Salesman marketting = markettingRepository.findById(transaksiDTO.getIdSalesman()).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));


        TransaksiIndent transaksi = new TransaksiIndent();
        transaksi.setTotalBelanja(String.valueOf(transaksiDTO.getTotalBelanja()));
        transaksi.setPembayaran(String.valueOf(transaksiDTO.getPembayaran()));
        transaksi.setPotongan(String.valueOf(transaksiDTO.getPotongan()));
        transaksi.setDiskon(String.valueOf(transaksiDTO.getDiskon()));
        transaksi.setTotalBayarBarang(String.valueOf(transaksiDTO.getTotalBayarBarang()));
        transaksi.setCustomer(customerRepository.findById(transaksiDTO.getIdCustomer()).orElse(null));
        transaksi.setSalesman(markettingRepository.findById(transaksiDTO.getIdSalesman()).orElse(null));
        transaksi.setStatus("excelcom");
        transaksi.setHari7(1);
        transaksi.setHari30(1);
        transaksi.setHari90(1);
        transaksi.setHari120(1);
        transaksi.setNota("1");
        transaksi.setHari365(1);
        transaksi.setKekurangan(transaksiDTO.getKekurangan());

        transaksi.setNoFaktur(not);
        transaksi.setKeterangan(transaksiDTO.getKeterangan());
        transaksi.setCashKredit(transaksiDTO.getCashKredit());
        transaksi.setSisa(String.valueOf(transaksiDTO.getSisa()));
        transaksi.setTtlBayarHemat(String.valueOf(transaksiDTO.getTtlBayarHemat()));
        transaksi.setTanggal(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

// Tambahkan 7 hari
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date tanggalNotif7 = calendar.getTime();
        transaksi.setTanggalNotif7(tanggalNotif7);

// Tambahkan 30 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.MONTH, 1);
        Date tanggalNotif30 = calendar.getTime();
        transaksi.setTanggalNotif30(tanggalNotif30);

// Tambahkan 90 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.MONTH, 3);
        Date tanggalNotif90 = calendar.getTime();
        transaksi.setTanggalNotif90(tanggalNotif90);

// Tambahkan 120 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.MONTH, 4);
        Date tanggalNotif120 = calendar.getTime();
        transaksi.setTanggalNotif120(tanggalNotif120);

// Tambahkan 365 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.YEAR, 1);
        Date tanggalNotif365 = calendar.getTime();
        transaksi.setTanggalNotif365(tanggalNotif365);
        transaksi.setDelFlag(1);

        TransaksiIndent savedTransaksi = transaksiIndentRepository.save(transaksi);

        List<BarangTransaksiDTO> listProduk = transaksiDTO.getProduk();
        for (BarangTransaksiDTO barangDTO : listProduk) {
            BarangTransaksiIndent barangTransaksi = new BarangTransaksiIndent();
            barangTransaksi.setTransaksiIndent(transaksiIndentRepository.findById(savedTransaksi.getId()).get());
            barangTransaksi.setBarcodeBarang(barangDTO.getBarcodeBarang());
            barangTransaksi.setQty(barangDTO.getQty());
            barangTransaksi.setDiskon(barangDTO.getDiskon());
            barangTransaksi.setHargaBrng(barangDTO.getHargaBrng());
            barangTransaksi.setTotalHarga(barangDTO.getTotalHarga());
            barangTransaksi.setTotalHargaBarang(barangDTO.getTotalHargaBarang());
            barangTransaksi.setTanggal(now);
            barangTransaksi.setHari7(1);
            barangTransaksi.setHari30(1);
            barangTransaksi.setHari90(1);
            barangTransaksi.setHari120(1);
            barangTransaksi.setHari367(1);
            barangTransaksi.setDelFlag(1);
            barangTransaksi.setHemat(String.valueOf(barangDTO.getHemat()));
            barangTransaksi.setStatus("excelcom");
            barangTransaksiIndentRepository.save(barangTransaksi);

        }
        return savedTransaksi;
    }
    public String getNoNotaTransaksi() {
        try {
            String kd = "";
            LocalDateTime now = LocalDateTime.now();
            int dateNow = Integer.parseInt(now.format(DateTimeFormatter.ofPattern("MM")));

            Integer kdMax = transaksiIndentRepository.findMaxKd();
            int tmp = (kdMax != null) ? kdMax + 1 : 1;

            String fullLastDate = transaksiIndentRepository.findLastDate();
            int lastDate = Integer.parseInt(fullLastDate.substring(5, 7)); // Extract month from the full date

            // Check if it's a new month
            if (lastDate != dateNow) {
                tmp = 1;
            }

            kd = String.format("%04d", tmp);

            // Format nota
            String nomor = now.format(DateTimeFormatter.ofPattern("MMyy")); // Format bulan dan tahun
            String nota = nomor + "-PST-PJN-" + kd;

            return nota;
        } catch (Exception e) {
            LocalDateTime now = LocalDateTime.now();
            String nomor = now.format(DateTimeFormatter.ofPattern("MMyy")); // Format bulan dan tahun
            String nota = nomor + "-PST-PJN-0001" ;
            e.printStackTrace(); // Cetak stack trace untuk mengetahui sumber NullPointerException
            return nota;
        }
    }
    public Transaksi checklist(Long id) {
        TransaksiIndent transaksiIndent = transaksiIndentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id Not Found"));
        transaksiIndent.setDelFlag(0);
        transaksiIndentRepository.save(transaksiIndent);
        Date now = new Date();
        Customer customer = customerRepository.findById(transaksiIndent.getCustomer().getId())
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        Salesman salesman = markettingRepository.findById(transaksiIndent.getSalesman().getIdSalesman())
                .orElseThrow(() -> new NotFoundException("Salesman not found"));

        Transaksi transaksi = new Transaksi();
        transaksi.setTotalBelanja(Double.valueOf(transaksiIndent.getTotalBelanja()));
        transaksi.setPembayaran(Double.valueOf(transaksiIndent.getPembayaran()));
        transaksi.setPotongan(Double.valueOf(transaksiIndent.getPotongan()));
        transaksi.setDiskon(Double.valueOf(transaksiIndent.getDiskon()));
        transaksi.setTotalBayarBarang(Double.valueOf(transaksiIndent.getTotalBayarBarang()));
        transaksi.setCustomer(customer);
        transaksi.setSalesman(salesman);
        transaksi.setNamaSalesman(salesman.getNamaSalesman());
        transaksi.setNamaCustomer(customer.getNama_customer());
        transaksi.setStatus(transaksiIndent.getStatus());
        transaksi.setHari7(1);
        transaksi.setHari30(1);
        transaksi.setHari90(1);
        transaksi.setHari120(1);
        transaksi.setNota("1");
        transaksi.setHari365(1);
        transaksi.setKekurangan(transaksiIndent.getKekurangan());
        transaksi.setNoFaktur(transaksiIndent.getNoFaktur());
        transaksi.setKeterangan(transaksiIndent.getKeterangan());
        transaksi.setCashKredit(transaksiIndent.getCashKredit());
        transaksi.setSisa(Double.valueOf(transaksiIndent.getSisa()));
        transaksi.setTtlBayarHemat(Double.valueOf(transaksiIndent.getTtlBayarHemat()));
        transaksi.setTanggal(now);

        // Set tanggal notifikasi
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        // Tambahkan 7 hari
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        transaksi.setTanggalNotif7(calendar.getTime());
        // Tambahkan 30 hari
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, 1);
        transaksi.setTanggalNotif30(calendar.getTime());
        // Tambahkan 90 hari
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, 3);
        transaksi.setTanggalNotif90(calendar.getTime());
        // Tambahkan 120 hari
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, 4);
        transaksi.setTanggalNotif120(calendar.getTime());
        // Tambahkan 365 hari
        calendar.setTime(now);
        calendar.add(Calendar.YEAR, 1);
        transaksi.setTanggalNotif365(calendar.getTime());
        transaksi.setDelFlag(1);
        Transaksi savedTransaksi = transaksiRepository.save(transaksi);

        // Mengambil daftar barang dari BarangTransaksiIndent berdasarkan ID transaksi indent
        List<BarangTransaksiIndent> listProduk = barangTransaksiIndentRepository.findByTransaksiIndentId(id);
        for (BarangTransaksiIndent barangIndent : listProduk) {
            Barang barang = barangRepository.findByBarcode(barangIndent.getBarcodeBarang());
            if (barang == null) {
                throw new NotFoundException("Barang dengan barcode '" + barangIndent.getBarcodeBarang() + "' tidak ditemukan");
            }

            int sisaStok = barang.getJumlahStok() - barangIndent.getQty();
            if (sisaStok < 0) {
                throw new BadRequestException("Stok Barang '" + barang.getNamaBarang() + "' Habis");
            }

            // Membuat barang transaksi
            BarangTransaksi barangTransaksi = new BarangTransaksi();
            barangTransaksi.setTransaksi(savedTransaksi);
            barangTransaksi.setBarcodeBarang(barangIndent.getBarcodeBarang());
            barangTransaksi.setQty(barangIndent.getQty());
            barangTransaksi.setDiskon(barangIndent.getDiskon());
            barangTransaksi.setHargaBrng(barangIndent.getHargaBrng());
            barangTransaksi.setTotalHarga(barangIndent.getTotalHarga());
            barangTransaksi.setUnit(barang.getUnit());
            barangTransaksi.setTotalHargaBarang(barangIndent.getTotalHargaBarang());
            barangTransaksi.setNamaBarang(barang.getNamaBarang());
            barangTransaksi.setTanggal(now);
            barangTransaksi.setHari7(1);
            barangTransaksi.setHari30(1);
            barangTransaksi.setHari90(1);
            barangTransaksi.setHari120(1);
            barangTransaksi.setHari367(1);
            barangTransaksi.setDelFlag(1);
            barangTransaksi.setHemat(String.valueOf(barangIndent.getHemat()));
            barangTransaksi.setStatus(barangIndent.getStatus());
            barangTransaksiRepository.save(barangTransaksi);

            // Mengurangi stok barang
            barang.setJumlahStok(sisaStok);
            barangRepository.save(barang);
        }
        return transaksi;
    }
    public List<TransaksiIndent> getTransaksiIndentExcelcom(){
        return transaksiIndentRepository.findTransaksiExcelcom();
    }
}
