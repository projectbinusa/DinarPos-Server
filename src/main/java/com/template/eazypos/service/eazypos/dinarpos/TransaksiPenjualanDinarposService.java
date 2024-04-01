package com.template.eazypos.service.eazypos.dinarpos;

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
public class TransaksiPenjualanDinarposService {
    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private BarangTransaksiRepository barangTransaksiRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SalesmanRepository salesmanRepository;
    @Autowired
    private BarangRepository barangRepository;

    public Transaksi addTransaksi(TransaksiPenjualanDTO transaksiDTO) {
        Date now = new Date();
        String not = getNoNotaTransaksiPenjualanExcelcom(); // method generateNotaNumber() menghasilkan nomor nota baru
        Customer customer = customerRepository.findById(transaksiDTO.getIdCustomer()).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        Salesman salesman = salesmanRepository.findById(transaksiDTO.getIdSalesman()).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));

        Transaksi transaksi = new Transaksi();
        transaksi.setTotalBelanja(transaksiDTO.getTotalBelanja());
        transaksi.setPembayaran(transaksiDTO.getPembayaran());
        transaksi.setPotongan(transaksiDTO.getPotongan());
        transaksi.setDiskon(transaksiDTO.getDiskon());
        transaksi.setTotalBayarBarang(transaksiDTO.getTotalBayarBarang());
        transaksi.setCustomer(customerRepository.findById(transaksiDTO.getIdCustomer()).get());
        transaksi.setSalesman(salesmanRepository.findById(transaksiDTO.getIdSalesman()).get());
        transaksi.setDelFlag(1);
        transaksi.setNamaSalesman(salesman.getNamaSalesman());
        transaksi.setNamaCustomer(customer.getNama_customer());
        transaksi.setStatus("dinarpos");
        transaksi.setHari7(1);
        transaksi.setHari30(1);
        transaksi.setHari90(1);
        transaksi.setHari120(1);
        transaksi.setHari365(1);
        transaksi.setKekurangan(transaksiDTO.getKekurangan());

        transaksi.setNoFaktur(not);
        transaksi.setKeterangan(transaksiDTO.getKeterangan());
        transaksi.setCashKredit(transaksiDTO.getCashKredit());
        transaksi.setSisa(transaksiDTO.getSisa());
        transaksi.setTtlBayarHemat(transaksiDTO.getTtlBayarHemat());
        transaksi.setTanggal(now);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

// Tambahkan 7 hari
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        transaksi.setTanggalNotif7(calendar.getTime());

// Tambahkan 30 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.MONTH, 1);
        transaksi.setTanggalNotif30(calendar.getTime());

// Tambahkan 90 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.MONTH, 3);
        transaksi.setTanggalNotif90(calendar.getTime());

// Tambahkan 120 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.MONTH, 4);
        transaksi.setTanggalNotif120(calendar.getTime());

// Tambahkan 365 hari
        calendar.setTime(now); // Kembalikan waktu ke semula
        calendar.add(Calendar.YEAR, 1);
        transaksi.setTanggalNotif365(calendar.getTime());
        transaksi.setDelFlag(1);

        Transaksi savedTransaksi = transaksiRepository.save(transaksi);

        List<BarangTransaksiDTO> listProduk = transaksiDTO.getProduk();
        for (BarangTransaksiDTO barangDTO : listProduk) {
            BarangTransaksi barangTransaksi = new BarangTransaksi();
            barangTransaksi.setTransaksi(transaksiRepository.findById(savedTransaksi.getIdTransaksi()).get());
            barangTransaksi.setBarcodeBarang(barangDTO.getBarcodeBarang());
            barangTransaksi.setQty(barangDTO.getQty());
            barangTransaksi.setDiskon(barangDTO.getDiskon());
            barangTransaksi.setHargaBrng(barangDTO.getHargaBrng());
            barangTransaksi.setTotalHarga(barangDTO.getTotalHarga());
            barangTransaksi.setUnit(barangRepository.findByBarcode(barangDTO.getBarcodeBarang()).getUnit());
            barangTransaksi.setTotalHargaBarang(barangDTO.getTotalHargaBarang());
            barangTransaksi.setNamaBarang(barangRepository.findByBarcode(barangDTO.getBarcodeBarang()).getNamaBarang());
            barangTransaksi.setTanggal(now);
            barangTransaksi.setHari7(1);
            barangTransaksi.setHari30(1);
            barangTransaksi.setHari90(1);
            barangTransaksi.setHari120(1);
            barangTransaksi.setHari367(1);
            barangTransaksi.setDelFlag(1);
            barangTransaksi.setHemat(String.valueOf(barangDTO.getHemat()));
            barangTransaksi.setStatus("dinarpos");
            barangTransaksiRepository.save(barangTransaksi);

        }
        for (BarangTransaksiDTO barangDTO : listProduk) {
            // Ambil data barang dari database berdasarkan barcode
            Barang barang = barangRepository.findByBarcode(barangDTO.getBarcodeBarang());

            if (barang != null) {
                int sisaStok = barang.getJumlahStok() - barangDTO.getQty();

                if (sisaStok >= 0) {
                    barang.setJumlahStok(sisaStok);
                    barangRepository.save(barang);
                } else {
                    throw  new BadRequestException("Stok Barang Habis");
                }
            } else {
                throw  new BadRequestException(" Barang Tidak Ada ");
            }
        }
        return savedTransaksi;
    }

    public String getNoNotaTransaksiPenjualanExcelcom() {
        String kd = "";
        LocalDateTime now = LocalDateTime.now();
        int dateNow = Integer.parseInt(now.format(DateTimeFormatter.ofPattern("MM")));

        Integer kdMax = transaksiRepository.findMaxKd();
        int tmp = (kdMax != null) ? kdMax + 1 : 1;

        String fullLastDate = transaksiRepository.findLastDate();
        int lastDate = Integer.parseInt(fullLastDate.substring(5, 7)); // Extract month from the full date

        // Check if it's a new month
        if (lastDate != dateNow) {
            tmp = 1;
        }

        kd = String.format("%04d", tmp);

        // Format nota
        String nomor = now.format(DateTimeFormatter.ofPattern("MMyy")); // Format bulan dan tahun
        String nota = nomor + "-PST-PJN-0" + kd;

        return nota;
    }
    public List<BarangTransaksi> getDinarposByIdTransaksi(Long idTransaksi){
        String status = "dinarpos";
        return barangTransaksiRepository.findBarangTransaksiByIdTransaksi(idTransaksi ,status);
    }
    public List<Transaksi> getAll(){
        return transaksiRepository.findAll();
    }
    public List<Transaksi> getDinarposBYMonthAndYear(int bulan , int tahun){
        return transaksiRepository.findTransaksiByMonthAndYear(bulan,tahun , "dinarpos");
    }
}
