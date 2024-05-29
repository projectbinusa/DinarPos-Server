package com.template.eazypos.service.eazypos.excelcom;

import com.template.eazypos.dto.BarangTransaksiDTO;
import com.template.eazypos.dto.PembayaranDTO;
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
import java.util.Optional;

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

    @Autowired
    OmzetRepository omzetRepository;

    @Autowired
    private KasHarianRepository kasHarianRepository;

    @Autowired
    private PiutangRepository piutangRepository;

    @Autowired
    private PersediaanAwalRepository persediaanAwalRepository;

    @Autowired
    private StokAwalrepository stokAwalrepository;

    @Autowired
    private PersediaanRepository persediaanRepository;

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
    public Transaksi checklist(Long id, PembayaranDTO pembayaranDTO) {
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
        int dp = Integer.parseInt(transaksiIndent.getPembayaran()) + pembayaranDTO.getPrembayaran();
        transaksi.setTotalBelanja(parseDouble(transaksiIndent.getTotalBelanja()));
        transaksi.setPembayaran((double) dp);
        transaksi.setPotongan(parseDouble(transaksiIndent.getPotongan()));
        transaksi.setDiskon(parseDouble(transaksiIndent.getDiskon()));
        transaksi.setTotalBayarBarang(parseDouble(transaksiIndent.getTotalBayarBarang()));
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
        transaksi.setDp(transaksiIndent.getPembayaran());
        transaksi.setKekurangan("0");
        transaksi.setServiceBarang(null);
        transaksi.setNoFaktur(transaksiIndent.getNoFaktur());
        transaksi.setKeterangan(transaksiIndent.getKeterangan());
        transaksi.setCashKredit(transaksiIndent.getCashKredit());
        transaksi.setSisa(parseDouble(transaksiIndent.getSisa()));
        transaksi.setTtlBayarHemat(parseDouble(transaksiIndent.getTtlBayarHemat()));
        transaksi.setTanggal(now);
        transaksi.setDelFlag(1);

        // Set notification dates
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);

        // Add 7 days
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        transaksi.setTanggalNotif7(calendar.getTime());

        // Add 30 days
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, 1);
        transaksi.setTanggalNotif30(calendar.getTime());

        // Add 90 days
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, 3);
        transaksi.setTanggalNotif90(calendar.getTime());

        // Add 120 days
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, 4);
        transaksi.setTanggalNotif120(calendar.getTime());

        // Add 365 days
        calendar.setTime(now);
        calendar.add(Calendar.YEAR, 1);
        transaksi.setTanggalNotif365(calendar.getTime());

        Transaksi savedTransaksi = transaksiRepository.save(transaksi);

        // Handle Produk details
        List<BarangTransaksiIndent> listProduk = barangTransaksiIndentRepository.findByTransaksiIndentId(id);
        for (BarangTransaksiIndent barangDTO : listProduk) {
            BarangTransaksi barangTransaksi = new BarangTransaksi();
            barangTransaksi.setTransaksi(savedTransaksi);
            barangTransaksi.setBarcodeBarang(barangDTO.getBarcodeBarang());
            barangTransaksi.setQty(barangDTO.getQty());
            barangTransaksi.setDiskon(barangDTO.getDiskon());
            barangTransaksi.setHargaBrng(barangDTO.getHargaBrng());
            barangTransaksi.setTotalHarga(barangDTO.getTotalHarga());
            Barang barang = barangRepository.findByBarcode(barangDTO.getBarcodeBarang());
            if (barang == null) {
                throw new BadRequestException("Barang Tidak Ada");
            }
            barangTransaksi.setUnit(barang.getUnit());
            barangTransaksi.setTotalHargaBarang(barangDTO.getTotalHargaBarang());
            barangTransaksi.setNamaBarang(barang.getNamaBarang());
            barangTransaksi.setTanggal(now);
            barangTransaksi.setHari7(1);
            barangTransaksi.setHari30(1);
            barangTransaksi.setHari90(1);
            barangTransaksi.setHari120(1);
            barangTransaksi.setHari367(1);
            barangTransaksi.setDelFlag(1);
            barangTransaksi.setHemat(parseDoubleString(barangDTO.getHemat()));
            barangTransaksi.setStatus(transaksiIndent.getStatus());
            barangTransaksiRepository.save(barangTransaksi);

            // Update stock
            int sisaStok = barang.getJumlahStok() - barangDTO.getQty();
            if (sisaStok < 0) {
                throw new BadRequestException("Stok Barang Habis");
            }
            StokAwal stokAwal = new StokAwal();
            stokAwal.setQty(String.valueOf(barangDTO.getQty()));
            stokAwal.setBarcodeBarang(barangDTO.getBarcodeBarang());
            stokAwal.setTanggal(new Date());
            barang.setJumlahStok(sisaStok);
            barangRepository.save(barang);
            stokAwalrepository.save(stokAwal);
        }

        // Update Kas Harian
        String cash = transaksiIndent.getCashKredit();
        double pembayaran = parseDouble(transaksiIndent.getPembayaran());
        double kekurangan = parseDouble(transaksiIndent.getKekurangan());
        double penjualan = pembayaran + kekurangan;

        KasHarian kasHarian = new KasHarian();
        kasHarian.setTransaksi(savedTransaksi);
        kasHarian.setPenjualan(String.valueOf(pembayaran));
        kasHarian.setTimestamp(new Date());

        if ("Cash Uang".equals(cash)) {
            kasHarian.setPenjualan(String.valueOf(pembayaran));
        } else if ("Cash Bank".equals(cash)) {
            kasHarian.setPenjualan(String.valueOf(pembayaran));
            kasHarian.setBank(String.valueOf(pembayaran));
        } else {
            kasHarian.setPenjualan(String.valueOf(penjualan));
            kasHarian.setPiutang(String.valueOf(kekurangan));

            Piutang piutang = new Piutang();
            piutang.setTransaksi(savedTransaksi);
            piutang.setDate(now);
            piutang.setKekurangan(String.valueOf(kekurangan));
            piutangRepository.save(piutang);
        }
        kasHarianRepository.save(kasHarian);

        // Update Omzet
        Omzet omzet = new Omzet();
        omzet.setOmzet(parseDouble(transaksiIndent.getTotalBelanja()));
        omzet.setTransaksi(transaksiRepository.findById(savedTransaksi.getIdTransaksi()).get());
        omzet.setNmCustomer(customer.getNama_customer());
        omzet.setSalesman(transaksiIndent.getSalesman());
        omzet.setTgl(now);
        omzetRepository.save(omzet);

        // Insert into Persediaan Awal
        PersediaanAwal persediaanAwal = new PersediaanAwal();
        persediaanAwal.setTransaksi(transaksiRepository.findById(savedTransaksi.getIdTransaksi()).get());
        persediaanAwal.setNominal(String.valueOf(transaksiIndent.getTotalBelanja()));
        persediaanAwal.setTanggal(new Date());

        persediaanAwalRepository.save(persediaanAwal);

        // Update Penjualan Tabel Persediaan
        updatePenjualanTabelPersediaan(now);

        return savedTransaksi;
    }

    // Method to parse double safely
    private double parseDouble(String value) {
        try {
            return value != null ? Double.parseDouble(value) : 0.0;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    // Method to parse double to string safely
    private String parseDoubleString(String value) {
        try {
            return value != null ? String.valueOf(Double.parseDouble(value)) : "0";
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    // Method to update Penjualan Tabel Persediaan
    private void updatePenjualanTabelPersediaan(Date date) {
        // Retrieve the persediaan entry for the given date
        List<Persediaan> persediaanOpt = persediaanRepository.findByDate(date);

        // Calculate the total penjualan
        List<PersediaanAwal> totalPenjualanList = persediaanAwalRepository.findByTanggal(date);
        double totalPenjualan = totalPenjualanList.stream()
                .mapToDouble(pa -> {
                    try {
                        return Double.parseDouble(pa.getNominal());
                    } catch (NumberFormatException e) {
                        // Handle the error, e.g., log it and return 0
                        System.err.println("Invalid nominal value: " + pa.getNominal());
                        return 0;
                    }
                })
                .sum();

        if (!persediaanOpt.isEmpty()) {
            Persediaan persediaan = persediaanOpt.get(0);
            persediaan.setPenjualan(String.valueOf(totalPenjualan));
            int barangSiapJual = Integer.parseInt(persediaan.getBarangSiapJual());
            int persediaanAkhir = barangSiapJual - (int) totalPenjualan;
            persediaan.setPersediaanAkhir(String.valueOf(persediaanAkhir));

            persediaanRepository.save(persediaan);
        } else {
            // Assuming persediaanService is autowired
            int persediaanAwal = persediaanAkhirToAwal(date);

            Persediaan newPersediaan = new Persediaan();
            newPersediaan.setPersediaanAwal(String.valueOf(persediaanAwal));
            newPersediaan.setBarangSiapJual(String.valueOf(persediaanAwal));
            newPersediaan.setPenjualan(String.valueOf(totalPenjualan));
            int akhir = persediaanAwal - (int) totalPenjualan;
            newPersediaan.setPersediaanAkhir(String.valueOf(akhir));
            newPersediaan.setDate(new Date());

            persediaanRepository.save(newPersediaan);
        }
    }

    public int persediaanAkhirToAwal(Date date) {
        List<Persediaan> persediaanList = persediaanRepository.findLastBeforeDate(date);

        if (!persediaanList.isEmpty()) {
            // Choose the first record if multiple exist
            Persediaan persediaan = persediaanList.get(0);
            return (int) parseDouble(persediaan.getPersediaanAkhir());
        } else {
            return 0;
        }
    }



    public List<TransaksiIndent> getTransaksiIndentExcelcom(){
        return transaksiIndentRepository.findTransaksiExcelcom();
    }
    public List<BarangTransaksiIndent> getBarangTransaksiIndent(Long id){
        return barangTransaksiIndentRepository.findByTransaksiIndentId(id);
    }
    public TransaksiIndent getById(Long id){
        return transaksiIndentRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
}
