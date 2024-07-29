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
import java.util.Optional;

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

    @Autowired
    private PersediaanBarangRepository persediaanBarangRepository;

    // Menambahkan transaksi penjualan baru berdasarkan data DTO transaksi penjualan
    public Transaksi addTransaksi(TransaksiPenjualanDTO transaksiDTO) {
        Date now = new Date();
        String not = getNoNotaTransaksi(); // method generateNotaNumber() menghasilkan nomor nota baru
        Customer customer = customerRepository.findById(transaksiDTO.getIdCustomer())
                .orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
        Salesman salesman = salesmanRepository.findById(transaksiDTO.getIdSalesman())
                .orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));

        Transaksi transaksi = new Transaksi();
        transaksi.setTotalBelanja(transaksiDTO.getTotalBelanja());
        transaksi.setPembayaran(Double.valueOf(transaksiDTO.getPembayaran()));
        transaksi.setPotongan(transaksiDTO.getPotongan());
        transaksi.setDiskon(transaksiDTO.getDiskon());
        transaksi.setTotalBayarBarang(transaksiDTO.getTotalBayarBarang());
        transaksi.setCustomer(customer);
        transaksi.setSalesman(salesman);
        transaksi.setNamaSalesman(salesman.getNamaSalesman());
        transaksi.setNamaCustomer(customer.getNama_customer());
        transaksi.setStatus("dinarpos");
        transaksi.setHari7(1);
        transaksi.setHari30(1);
        transaksi.setHari90(1);
        transaksi.setHari120(1);
        transaksi.setNota("1");
        transaksi.setDp("0");
        transaksi.setServiceBarang(null);
        transaksi.setHari365(1);
        transaksi.setKekurangan(transaksiDTO.getKekurangan());
        transaksi.setNoFaktur(not);
        transaksi.setKeterangan(transaksiDTO.getKeterangan());
        transaksi.setCashKredit(transaksiDTO.getCashKredit());
        transaksi.setSisa(transaksiDTO.getSisa());
        transaksi.setTtlBayarHemat(transaksiDTO.getTtlBayarHemat());
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
        List<BarangTransaksiDTO> listProduk = transaksiDTO.getProduk();
        for (BarangTransaksiDTO barangDTO : listProduk) {
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
            barangTransaksi.setHemat(String.valueOf(barangDTO.getHemat()));
            barangTransaksi.setStatus("dinarpos");
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

            // Insert/Update Persediaan Barang
            tabelPersediaanBarangStokKeluar(stokAwal);
        }

        // Update Kas Harian
        String cash = transaksiDTO.getCashKredit();
        int pembayaran =  transaksiDTO.getPembayaran();
        int kekurangan = Integer.parseInt(transaksiDTO.getKekurangan());
        int penjualan = pembayaran + kekurangan;

        KasHarian kasHarian = new KasHarian();
        kasHarian.setTransaksi(savedTransaksi);
        kasHarian.setPenjualan(String.valueOf(pembayaran));
        kasHarian.setTimestamp(new Date());

        if (cash.equals("Cash Uang")) {
            kasHarian.setPenjualan(String.valueOf(pembayaran));
        } else if (cash.equals("Cash Bank")) {
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
        omzet.setOmzet(transaksiDTO.getTotalBelanja());
        omzet.setTransaksi(transaksiRepository.findById(savedTransaksi.getIdTransaksi()).get());
        omzet.setNmCustomer(customer.getNama_customer());
        omzet.setSalesman(salesmanRepository.findById(transaksiDTO.getIdSalesman()).get());
        omzet.setTgl(now);
        omzetRepository.save(omzet);

        // Insert into Persediaan Awal
        PersediaanAwal persediaanAwal = new PersediaanAwal();
        persediaanAwal.setTransaksi(transaksiRepository.findById(savedTransaksi.getIdTransaksi()).get());
        persediaanAwal.setNominal(String.valueOf(transaksiDTO.getTotalBelanja()));
        persediaanAwal.setTanggal(new Date());

        persediaanAwalRepository.save(persediaanAwal);

        // Update Penjualan Tabel Persediaan
        updatePenjualanTabelPersediaan(now);

        return savedTransaksi;
    }

    private void tabelPersediaanBarangStokKeluar(StokAwal stokAwal) {
        Long id = stokAwal.getIdStokAwal();
        Optional<StokAwal> stokKeluarOpt = stokAwalrepository.findById(id);
        if (!stokKeluarOpt.isPresent()) {
            throw new NotFoundException("Stok Awal not found");
        }
        StokAwal stokKeluar = stokKeluarOpt.get();

        List<PersediaanBarang> persediaanBarangList = persediaanBarangRepository.findByTanggalAndBarangBarcode(
                stokKeluar.getTanggal(), stokKeluar.getBarcodeBarang());

        int stokAwalQty = persediaanAkhirToAwalBarang(stokKeluar.getTanggal(), stokKeluar.getBarcodeBarang());

        if (!persediaanBarangList.isEmpty()) {
            for (PersediaanBarang persediaan : persediaanBarangList) {
                int keluar = Integer.parseInt(persediaan.getKeluar() + Integer.parseInt(stokKeluar.getQty()));
                persediaan.setKeluar(String.valueOf(keluar));
                persediaan.setStok_akhir(String.valueOf(Integer.parseInt(persediaan.getStok_akhir()) - Integer.parseInt(stokKeluar.getQty())));
                persediaanBarangRepository.save(persediaan);
            }
        } else {
            PersediaanBarang persediaanBarang = new PersediaanBarang();
            persediaanBarang.setBarang(barangRepository.findByBarcodeBarang(stokKeluar.getBarcodeBarang()).get());
            persediaanBarang.setStok_awal(String.valueOf(stokAwalQty));
            persediaanBarang.setKeluar(stokKeluar.getQty());
            persediaanBarang.setStok_akhir(String.valueOf(stokAwalQty - Integer.parseInt(stokKeluar.getQty())));
            persediaanBarangRepository.save(persediaanBarang);
        }
    }

    private int persediaanAkhirToAwalBarang(Date date, String barcodeBarang) {
        Optional<PersediaanBarang> result = persediaanBarangRepository.findFirstByTanggalBeforeAndBarangBarcodeOrderByTanggalDesc(date, barcodeBarang);
        Optional<PersediaanBarang> res = persediaanBarangRepository.findFirstByTanggalAfterAndBarangBarcodeOrderByTanggalDesc(date, barcodeBarang);

        if (result.isPresent()) {
            return Integer.parseInt(result.get().getStok_akhir());
        } else if (res.isPresent()) {
            return Integer.parseInt(res.get().getStok_awal());
        } else {
            Optional<Barang> stokBarang = Optional.ofNullable(barangRepository.findByBarcode(barcodeBarang));
            if (stokBarang.isPresent()) {
                return stokBarang.get().getJumlahStok();
            }
        }
        return 0;
    }

    private void updatePenjualanTabelPersediaan(Date date) {
        Optional<Persediaan> persediaanOpt = persediaanRepository.findByDate(date);
        List<PersediaanAwal> totalPenjualanList = persediaanAwalRepository.findByTanggal(date);

        int totalPenjualan = totalPenjualanList.stream()
                .mapToInt(pa -> {
                    try {
                        return Integer.parseInt(pa.getNominal());
                    } catch (NumberFormatException e) {
                        // Handle the error, e.g., log it and return 0
                        System.err.println("Invalid nominal value: " + pa.getNominal());
                        return 0;
                    }
                })
                .sum();

        if (persediaanOpt.isPresent()) {
            Persediaan persediaan = persediaanOpt.get();
            persediaan.setPenjualan(String.valueOf(totalPenjualan));
            int barangSiapJual = Integer.parseInt(persediaan.getBarangSiapJual());
            int persediaanAkhir = barangSiapJual - totalPenjualan;
            persediaan.setPersediaanAkhir(String.valueOf(persediaanAkhir));
            persediaanRepository.save(persediaan);
        } else {
            int persediaanAwal = persediaanAkhirToAwal(date);

            Persediaan newPersediaan = new Persediaan();
            newPersediaan.setPersediaanAwal(String.valueOf(persediaanAwal));
            newPersediaan.setBarangSiapJual(String.valueOf(persediaanAwal));
            newPersediaan.setPenjualan(String.valueOf(totalPenjualan));
            int akhir = persediaanAwal - totalPenjualan;
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
            return (int) Double.parseDouble(persediaan.getPersediaanAkhir());
        } else {
            return 0;
        }
    }

    // Menghasilkan nomor nota transaksi baru berdasarkan waktu
    public String getNoNotaTransaksi() {
        try {
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

    // Mengambil daftar barang transaksi berdasarkan ID transaksi dan status
    public List<BarangTransaksi> getDinarposByIdTransaksi(Long idTransaksi){
        String status = "dinarpos";
        return barangTransaksiRepository.findBarangTransaksiByIdTransaksi(idTransaksi ,status);
    }

    // Mengambil daftar semua transaksi penjualan yang tercatat dalam sistem
    public List<Transaksi> getAll(){
        return transaksiRepository.findAll();
    }

    // Mengambil daftar transaksi penjualan berdasarkan bulan dan tahun tertentu
    public List<Transaksi> getDinarposBYMonthAndYear(int bulan , int tahun){
        return transaksiRepository.findTransaksiByMonthAndYear(bulan,tahun , "dinarpos");
    }
}
