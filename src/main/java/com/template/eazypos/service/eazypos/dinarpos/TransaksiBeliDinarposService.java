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
import java.util.Calendar;
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

    public TransaksiBeli addTransaksi(TransaksiBeliDTO transaksiDTO) {
        Date now = new Date();
        String not = getNoNotaTransaksi(); // method generateNotaNumber() menghasilkan nomor nota baru
        Suplier suplier = suplierRepository.findById(transaksiDTO.getIdSuplier())
                .orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));

        TransaksiBeli transaksi = new TransaksiBeli();
        transaksi.setTotalBelanja(transaksiDTO.getTotalBelanja());
        transaksi.setPembayaran(transaksiDTO.getPembayaran());
        transaksi.setPotongan(transaksiDTO.getPotongan());
        transaksi.setDiskon(transaksiDTO.getDiskon());
        transaksi.setTotalBayarBarang(transaksiDTO.getTotalBayarBarang());
        transaksi.setSuplier(suplier);
        transaksi.setNamaSuplier(suplier.getNamaSuplier());
        transaksi.setStatus("dinarpos");
        transaksi.setKekurangan(transaksiDTO.getKekurangan());
        transaksi.setNoFaktur(not);
        transaksi.setKeterangan(transaksiDTO.getKeterangan());
        transaksi.setCashCredit(transaksiDTO.getCashCredit());
        transaksi.setSisa(transaksiDTO.getSisa());
        transaksi.setTtlBayarHemat(transaksiDTO.getTtlBayarHemat());
        transaksi.setTanggal(now);
        transaksi.setDelFlag(1);

        // Set notification dates

        TransaksiBeli savedTransaksi = transaksiBeliRepository.save(transaksi);


        // Handle Produk details
        List<BarangTransaksiDTO> listProduk = transaksiDTO.getProduk();
        for (BarangTransaksiDTO barangDTO : listProduk) {
            BarangTransaksiBeli barangTransaksi = new BarangTransaksiBeli();
            barangTransaksi.setTransaksiBeli(savedTransaksi);
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
            barangTransaksi.setDelFlag(1);
            barangTransaksi.setHemat(String.valueOf(barangDTO.getHemat()));
            barangTransaksi.setStatus("dinarpos");
            barangTransaksiBeliRepository.save(barangTransaksi);

            // Update stock
            int sisaStok = barang.getJumlahStok() + barangDTO.getQty();
            StokAkhir stokAkhir = new StokAkhir();
            stokAkhir.setQty(String.valueOf(barangDTO.getQty()));
            stokAkhir.setBarcodeBarang(barangDTO.getBarcodeBarang());
            stokAkhir.setTanggal(new Date());
            barang.setJumlahStok(sisaStok);
            barangRepository.save(barang);
            stokAkhirRepository.save(stokAkhir);
        }

        // Update Hutang

        if(transaksiDTO.getCashCredit().equals("Kredit")){
            Hutang hutang = new Hutang();
            hutang.setTransaksiBeli(transaksiBeliRepository.findById(savedTransaksi.getIdTransaksiBeli()).get());
            hutang.setDate(new Date());
            hutang.setHutang(savedTransaksi.getKekurangan());
            hutangRepository.save(hutang);
        }


        // Insert into Persediaan Akhir
        PersediaanAkhir persediaanAkhir = new PersediaanAkhir();
        persediaanAkhir.setTransaksi(transaksiBeliRepository.findById(savedTransaksi.getIdTransaksiBeli()).get());
        persediaanAkhir.setNominal(String.valueOf(transaksiDTO.getTotalBelanja()));
        persediaanAkhir.setTanggal(new Date());

        persediaanAkhirRepository.save(persediaanAkhir);

        // Update Penjualan Tabel Persediaan
        updatePenjualanTabelPersediaan(now);

        return savedTransaksi;
    }

    private void updatePenjualanTabelPersediaan(Date date) {

        // Retrieve the persediaan entry for the given date
        Optional<Persediaan> persediaanOpt = persediaanRepository.findByDate(date);

        // Calculate the total penjualan
        List<PersediaanAkhir> totalPenjualanList = persediaanAkhirRepository.findByTanggal(date);
        int totalPenjualan = totalPenjualanList.stream()
                .mapToInt(pa -> {
                    try {
                        return Integer.parseInt((pa.getNominal()));
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
            int persediaanAkhir = barangSiapJual + totalPenjualan;
            persediaan.setPersediaanAkhir(String.valueOf(persediaanAkhir));

            persediaanRepository.save(persediaan);
        } else {
            // Assuming persediaanService is autowired
            int persediaanAwal =  persediaanAkhirToAwal(date);

            Persediaan newPersediaan = new Persediaan();
            newPersediaan.setPersediaanAwal(String.valueOf(persediaanAwal));
            newPersediaan.setBarangSiapJual(String.valueOf(persediaanAwal));
            newPersediaan.setPenjualan(String.valueOf(totalPenjualan));
            int akhir = persediaanAwal + totalPenjualan;
            newPersediaan.setPersediaanAkhir(String.valueOf(akhir));
            newPersediaan.setDate(new Date());

            persediaanRepository.save(newPersediaan);
        }
    }


    public int persediaanAkhirToAwal(Date date) {
        Optional<Persediaan> persediaanOpt = persediaanRepository.findLastBeforeDate(date);

        if (persediaanOpt.isPresent()) {
            Persediaan persediaan = persediaanOpt.get();
            return Integer.parseInt(persediaan.getPersediaanAkhir());
        } else {
            return 0;
        }
    }
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

    public TransaksiBeli getById(Long id){
        return transaksiBeliRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }
    public List<BarangTransaksiBeli> getByIdTransaksi (Long idTransaksi){
        String status = "dinarpos";
        return barangTransaksiBeliRepository.findBarangTransaksiDinarposByIdTransaksi(status , idTransaksi);
    }
    public  List<TransaksiBeli> getAll(){
        return transaksiBeliRepository.findAll();
    }

    public List<TransaksiBeli> getDinarposBYMonthAndYear(int bulan , int tahun){
        return transaksiBeliRepository.findTransaksiByMonthAndYear(bulan,tahun , "dinarpos");
    }

}
