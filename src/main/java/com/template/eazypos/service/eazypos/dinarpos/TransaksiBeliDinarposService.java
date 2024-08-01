package com.template.eazypos.service.eazypos.dinarpos;

import com.template.eazypos.dto.BarangTransaksiDTO;
import com.template.eazypos.dto.TransaksiBeliDTO;
import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.*;
import com.template.eazypos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransaksiBeliDinarposService {
    @Autowired
    private TransaksiBeliRepository transaksiBeliRepository;

    @Autowired
    private BarangRepository barangRepository;

    @Autowired
    private BarangTransaksiBeliRepository barangTransaksiBeliRepository;

    @Autowired
    private SuplierRepository suplierRepository;


    @Autowired
    private HutangRepository hutangRepository;

    @Autowired
    private PersediaanAkhirRepository persediaanAkhirRepository;

    @Autowired
    private StokAkhirRepository stokAkhirRepository;

    @Autowired
    private PersediaanRepository persediaanRepository;

    @Autowired
    private PersediaanBarangRepository persediaanBarangRepository;

    //     Main method for adding a new transaction
    public TransaksiBeli addTransaksi(TransaksiBeliDTO transaksiDTO) {
        Date now = new Date();
        String not = getNoNotaTransaksi(); // Method to generate new Nota Number

        Suplier suplier = suplierRepository.findById(transaksiDTO.getIdSuplier())
                .orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));

        TransaksiBeli transaksi = new TransaksiBeli();
        transaksi.setTotalPpn(transaksiDTO.getPpn());
        transaksi.setTotalPpnDua(transaksiDTO.getPpn2());
        transaksi.setTotalDpp(transaksiDTO.getDpp());
        transaksi.setTotalDppDua(transaksiDTO.getDpp2());
        transaksi.setTotalBelanja(transaksiDTO.getTotalBelanja());
        transaksi.setPembayaran(transaksiDTO.getPembayaran());
        transaksi.setHutang(transaksiDTO.getHutang());
        transaksi.setNominalHutang(Integer.valueOf(transaksiDTO.getHutang()));
        transaksi.setTotalBelanjaDua(String.valueOf(transaksiDTO.getTotalBayar2()));
        transaksi.setPembayaran(transaksiDTO.getPembayaran());
        transaksi.setPotongan(transaksiDTO.getPotongan());
        transaksi.setDiskon(transaksiDTO.getDiskon());
        transaksi.setTotalBayarBarang(transaksiDTO.getTotalBayarBarang());
        transaksi.setSuplier(suplier);
        transaksi.setNoFaktur(not);
        transaksi.setKeterangan(transaksiDTO.getKeterangan());
        transaksi.setCashCredit(transaksiDTO.getCashCredit());
        transaksi.setSisa(transaksiDTO.getSisa());
        transaksi.setTtlBayarHemat(transaksiDTO.getTtlBayarHemat());
        transaksi.setTanggal(now);
        transaksi.setDelFlag(1);
        transaksi.setStatus("dinarpos");

        TransaksiBeli savedTransaksi = transaksiBeliRepository.save(transaksi);

        // Insert into Persediaan Akhir
        PersediaanAkhir persediaanAkhir = new PersediaanAkhir();
        persediaanAkhir.setTransaksi(savedTransaksi);
        persediaanAkhir.setNominal(String.valueOf(transaksiDTO.getTotal3()));
        persediaanAkhir.setTanggal(now);
        persediaanAkhirRepository.save(persediaanAkhir);

        // Handle Produk details
        List<BarangTransaksiDTO> listProduk = transaksiDTO.getProduk();
        for (BarangTransaksiDTO barangDTO : listProduk) {
            Barang barang = barangRepository.findByBarcode(barangDTO.getBarcodeBarang());
            if (barang == null) {
                throw new BadRequestException("Barang Tidak Ada");
            }

            BarangTransaksiBeli barangTransaksi = new BarangTransaksiBeli();
            barangTransaksi.setTransaksiBeli(savedTransaksi);
            barangTransaksi.setBarcodeBarang(barangDTO.getBarcodeBarang());
            barangTransaksi.setQty(barangDTO.getQty());
            barangTransaksi.setDiskon(barangDTO.getDiskon());
            barangTransaksi.setHargaBrng(barangDTO.getHargaBrng());
            barangTransaksi.setTotalHarga(barangDTO.getTotalHarga());
            barangTransaksi.setTotalHargaBarang(barangDTO.getTotalHargaBarang());
            barangTransaksi.setTanggal(now);
            barangTransaksi.setDelFlag(1);
            barangTransaksi.setStatus("dinarpos");
            barangTransaksiBeliRepository.save(barangTransaksi);

            // Update stock
            int sisaStok = barang.getJumlahStok() + barangDTO.getQty();
            barang.setJumlahStok(sisaStok);
            barangRepository.save(barang);

            // Insert into Stok Akhir
            StokAkhir stokAkhir = new StokAkhir();
            stokAkhir.setBarcodeBarang(barangDTO.getBarcodeBarang());
            stokAkhir.setQty(String.valueOf(barangDTO.getQty()));
            stokAkhir.setTanggal(now);
            stokAkhirRepository.save(stokAkhir);

            // Insert or Update Persediaan Barang
            persediaanBarangStokMasuk(now, barangDTO.getBarcodeBarang(), barangDTO.getQty());
        }

        // Handle Hutang if Kredit
        if ("Kredit".equals(transaksiDTO.getCashCredit())) {
            Hutang hutang = new Hutang();
            hutang.setTransaksiBeli(savedTransaksi);
            hutang.setDate(now);
            hutang.setHutang(savedTransaksi.getHutang());
            hutangRepository.save(hutang);
        }

        // Update Penjualan Tabel Persediaan
        updatePembelianTabelPersediaan(now);

        return savedTransaksi;
    }

    // Persediaan Akhir Helper Methods
    private void persediaanBarangStokMasuk(Date date, String barcodeBarang, int qty) {
        List<PersediaanBarang> persediaanList = persediaanBarangRepository.findByTanggalAndBarangBarcode(date, barcodeBarang);
        int stokAwal = persediaanAkhirToAwalBarang(date, barcodeBarang);
        if (!persediaanList.isEmpty()) {
            PersediaanBarang persediaan = persediaanList.get(0);
            persediaan.setMasuk(persediaan.getMasuk() + qty);
            persediaan.setStok_akhir(persediaan.getStok_akhir() + qty);
            persediaanBarangRepository.save(persediaan);
        } else {
            PersediaanBarang newPersediaan = new PersediaanBarang();
            newPersediaan.setBarang(barangRepository.findByBarcodeBarang(barcodeBarang).get());
            newPersediaan.setStok_awal(String.valueOf(stokAwal));
            newPersediaan.setMasuk(String.valueOf(qty));
            newPersediaan.setStok_akhir(String.valueOf(stokAwal + qty));
            persediaanBarangRepository.save(newPersediaan);
        }
    }

    public int persediaanAkhirToAwalBarang(Date date, String barcodeBarang) {
        List<PersediaanBarang> result = persediaanBarangRepository.findLastBeforeDate(date, barcodeBarang);
        List<PersediaanBarang> res = persediaanBarangRepository.findFirstAfterDate(date, barcodeBarang);

        if (!result.isEmpty()) {
            return Integer.parseInt(result.get(0).getStok_akhir());
        } else if (!res.isEmpty()) {
            return Integer.parseInt(res.get(0).getStok_awal());
        } else {
            Barang barang = barangRepository.findByBarcode(barcodeBarang);
            return barang != null ? barang.getJumlahStok() : 0;
        }
    }

    private void updatePembelianTabelPersediaan(Date date) {
        Persediaan persediaan = persediaanRepository.findByDate(date).orElse(null);
        int persediaanAwal = persediaanAkhirToAwal(date);

        List<PersediaanAkhir> totalPembelianList = persediaanAkhirRepository.findByTanggal(date);
        int totalPembelian = totalPembelianList.stream()
                .mapToInt(pa -> Integer.parseInt(pa.getNominal()))
                .sum();

        if (persediaan != null) {
            int barangSiapJual = Integer.parseInt(persediaan.getPersediaanAwal()) + totalPembelian;
            int persediaanAkhir = barangSiapJual - Integer.parseInt(persediaan.getPenjualan());
            persediaan.setPembelian(String.valueOf(totalPembelian));
            persediaan.setBarangSiapJual(String.valueOf(barangSiapJual));
            persediaan.setPersediaanAkhir(String.valueOf(persediaanAkhir));
            persediaanRepository.save(persediaan);
        } else {
            int barangSiapJual = persediaanAwal + totalPembelian;
            Persediaan newPersediaan = new Persediaan();
            newPersediaan.setPersediaanAwal(String.valueOf(persediaanAwal));
            newPersediaan.setPembelian(String.valueOf(totalPembelian));
            newPersediaan.setBarangSiapJual(String.valueOf(barangSiapJual));
            newPersediaan.setPersediaanAkhir(String.valueOf(barangSiapJual));
            newPersediaan.setDate(date);
            persediaanRepository.save(newPersediaan);
        }
    }

    public int persediaanAkhirToAwal(Date date) {
        // Fetch the last record before the given date
        List<Persediaan> result = persediaanRepository.findLastBeforeDate(date);

        // If a result is found, return the persediaan_akhir value
        if (!result.isEmpty()) {
            Persediaan persediaan = result.get(0);
            try {
                return Integer.parseInt(persediaan.getPersediaanAkhir());
            } catch (NumberFormatException e) {
                // Log or handle error if needed
                e.printStackTrace();
            }
        }

        // Return 0 if no result found or if parsing fails
        return 0;
    }

    // Method untuk menghasilkan nomor nota transaksi baru
    public String getNoNotaTransaksi() {
        try {
            String kd = "";
            LocalDateTime now = LocalDateTime.now();
            int dateNow = Integer.parseInt(now.format(DateTimeFormatter.ofPattern("MM")));

            Integer kdMax = transaksiBeliRepository.findMaxKd();
            int tmp = (kdMax != null) ? kdMax + 1 : 1;

            String fullLastDate = transaksiBeliRepository.findLastDate();
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

    // Mengembalikan transaksi beli berdasarkan ID
    public TransaksiBeli getById(Long id){
        return transaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

    // Mengembalikan daftar barang transaksi beli berdasarkan ID transaksi
    public List<BarangTransaksiBeli> getByIdTransaksi (Long idTransaksi){
        String status = "dinarpos";
        return barangTransaksiBeliRepository.findBarangTransaksiDinarposByIdTransaksi(status , idTransaksi);
    }

    // Method untuk mendapatkan semua transaksi beli
    public  List<TransaksiBeli> getAll(){
        return transaksiBeliRepository.findAll();
    }

    // Method untuk mendapatkan transaksi beli berdasarkan bulan dan tahun
    public List<TransaksiBeli> getDinarposBYMonthAndYear(int bulan , int tahun){
        return transaksiBeliRepository.findTransaksiByMonthAndYear(bulan,tahun , "dinarpos");
    }

}
