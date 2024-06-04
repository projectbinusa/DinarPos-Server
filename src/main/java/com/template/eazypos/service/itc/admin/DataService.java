package com.template.eazypos.service.itc.admin;

import com.template.eazypos.dto.*;
import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.*;
import com.template.eazypos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.api.client.util.Base64;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class DataService {
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private TeknisiRepository teknisiRepository;

    @Autowired
    private TakeRepository takeRepository;

    @Autowired
    private TglKonfRepository tglKonfRepository;
    @Autowired
    private TransaksiRepository transaksiRepository;

    @Autowired
    private BarangTransaksiRepository barangTransaksiRepository;


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
    private PoinHistoryRepository poinHistoryRepository;

    @Autowired
    private PoinRepository poinRepository;


    private String convertToBase64Url(MultipartFile file) {
        String url = "";
        try {
            byte[] byteData = Base64.encodeBase64(file.getBytes());
            String result = new String(byteData);
            url = "data:" + file.getContentType() + ";base64," + result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return url;
        }

    }

    public ServiceBarang addService(AddServiceDTO serviceDTO){
        ServiceBarang service = new ServiceBarang();
        Customer customer = customerRepository.findById(serviceDTO.getId_customer()).get();
        service.setCustomer(customerRepository.findById(serviceDTO.getId_customer()).orElseThrow(() -> new NotFoundException("Id Customer tidak dinemukan")));
        service.setKet(serviceDTO.getKet());
        service.setProduk(serviceDTO.getJenis_produk());
        service.setMerk(serviceDTO.getMerek());
        service.setType(serviceDTO.getType());
        service.setSn(serviceDTO.getNo_seri());
        service.setPerlengkapan(serviceDTO.getPerlengkapan());
        service.setKeluhan(serviceDTO.getKeluhan());
        service.setPenerima(serviceDTO.getPenerima());
        service.setTanggalMasuk(serviceDTO.getTanggal_masuk());
        service.setBmax(serviceDTO.getBiaya_maximal());
        service.setEstimasi(serviceDTO.getEstimasi_biaya());
        service.setChecker(serviceDTO.getChecker());
        service.setCp(customer.getTelp());
        service.setStatusEnd("N_A");
        service.setAlamat(customer.getAlamat());
        service.setNama(customer.getNama_customer());
        return serviceRepository.save(service);
    }
    public Status prosesService(TakenServiceDTO takenServiceDTO){
        ServiceBarang serviceBarang = serviceRepository.findByIdTeknisi(takenServiceDTO.getId_teknisi()).orElseThrow(() -> new NotFoundException("Id Service Not Found"));
        serviceBarang.setStatusEnd("PROSES");
        serviceBarang.setTeknisi(teknisiRepository.findById(takenServiceDTO.getId_teknisi()).orElseThrow((() -> new NotFoundException("Id Teknisi Not Found"))));
        Status status = new Status();
        status.setService(serviceRepository.findByIdTT(serviceBarang.getIdTT()).get());
        status.setStatus(takenServiceDTO.getStatus());
        status.setSolusi(takenServiceDTO.getSolusi());
        status.setTanggal(new Date());
        status.setKet(takenServiceDTO.getKet());
        status.setType(takenServiceDTO.getType());
        status.setValidasi("0");
        serviceRepository.save(serviceBarang);
        return statusRepository.save(status);
    }
    public Status prosesServiceTeknisi(TakenServiceDTO takenServiceDTO){
        ServiceBarang serviceBarang = serviceRepository.findByIdTeknisi(takenServiceDTO.getId_teknisi()).orElseThrow(() -> new NotFoundException("Id Service Not Found"));
        Status status = new Status();
        status.setService(serviceRepository.findByIdTT(serviceBarang.getIdTT()).get());
        status.setStatus(takenServiceDTO.getStatus());
        status.setSolusi(takenServiceDTO.getSolusi());
        status.setTanggal(new Date());
        status.setKet(takenServiceDTO.getKet());
        status.setType(takenServiceDTO.getType());
        status.setValidasi("0");
        return statusRepository.save(status);
    }

    public ServiceBarang fotoBefore(MultipartFile multipartFile , Long id){
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        serviceBarang.setFb(convertToBase64Url(multipartFile));
        return serviceRepository.save(serviceBarang);
    }
    public ServiceBarang fotoAfter(MultipartFile multipartFile , Long id){
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        serviceBarang.setFa(convertToBase64Url(multipartFile));
        return serviceRepository.save(serviceBarang);
    }
    public ServiceBarang takeOver(TakeOverDTO takeOverDTO) {
        ServiceBarang teknisi = serviceRepository.findByIdTT(takeOverDTO.getId_service()).orElseThrow(() -> new NotFoundException("Id Service Not Found"));
        teknisi.setTeknisi(teknisiRepository.findById(takeOverDTO.getId_teknisi()).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found")));
        Take take = new Take();
        take.setId_take(teknisiRepository.findById(takeOverDTO.getId_teknisi()).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found")));
        take.setId_tekinisi(teknisiRepository.findById(takeOverDTO.getId_teknisi()).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found")));
        take.setService(teknisi);
        takeRepository.save(take);
        return serviceRepository.save(teknisi);
    }

    public ServiceBarang serviceAdmin(ServiceAdminDTO serviceAdminDTO , Long id){
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Service Not Found"));
        serviceBarang.setTanggalJadi(new Date());
        serviceBarang.setTanggalAmbil(new Date());
        serviceBarang.setBiayaSparepart(serviceAdminDTO.getBiaya_sparepart());
        serviceBarang.setBiayaService(serviceAdminDTO.getBiaya_service());
        serviceBarang.setTotal(serviceAdminDTO.getTotal());
        serviceBarang.setStatusEnd(serviceAdminDTO.getStatus());
       return serviceRepository.save(serviceBarang);
    }
    public TglKonf konfirm(KonfirmDTO konfirmDTO){
        TglKonf tglKonf = new TglKonf();

        tglKonf.setService(serviceRepository.findByIdTT(konfirmDTO.getId_service()).orElseThrow(() -> new NotFoundException("Id Service Not Found")));
        tglKonf.setTglKonf(konfirmDTO.getDate());
        return tglKonfRepository.save(tglKonf);
    }
    public ServiceBarang updateNote(NoteDTO noteDTO , Long id){
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Service Not Found"));
        serviceBarang.setCatatan(noteDTO.getNote());
        return serviceRepository.save(serviceBarang);
    }

    public ServiceBarang updateCustomer(UpdateCustomerDTO customerDTO , Long id){
        ServiceBarang serviceBarang = serviceRepository.findByIdTT( id).orElseThrow(() -> new NotFoundException("Id Service Not Found"));
        serviceBarang.setNama(customerDTO.getNama());
        serviceBarang.setAlamat(customerDTO.getAlamat());
        serviceBarang.setCp(customerDTO.getCp());
        serviceBarang.setKet(customerDTO.getKet());

        Customer customer = customerRepository.findByTelp(serviceBarang.getCp()).orElseThrow(() -> new NotFoundException("No Telp not found"));
        customer.setNama_customer(customer.getNama_customer());
        customer.setAlamat(customerDTO.getAlamat());
        customer.setTelp(customerDTO.getCp());
        customerRepository.save(customer);
        return serviceRepository.save(serviceBarang);
    }

    public List<Take> getTakeByIdTT(Long id) {
        return takeRepository.findByIdTT(id);
    }
    public List<Status> getStatusByIdTT(Long id) {
        return statusRepository.findByIdTT(id);
    }

    public Transaksi takenServiceCustomer(TransaksiPenjualanDTO transaksiDTO, Long id) {
        Date now = new Date();
        String not = getNoNotaTransaksi();
        Long idCustomer = transaksiDTO.getIdCustomer();
        Long idSalesman = transaksiDTO.getIdSalesman();

        Customer customer = customerRepository.findById(idCustomer)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        Salesman salesman = salesmanRepository.findById(idSalesman)
                .orElseThrow(() -> new NotFoundException("Salesman not found"));
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id)
                .orElseThrow(() -> new NotFoundException("Service not found"));

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
        transaksi.setNota(not);
        transaksi.setServiceBarang(serviceBarang);
        transaksi.setKekurangan(transaksiDTO.getKekurangan());
        transaksi.setKeterangan(transaksiDTO.getKeterangan());
        transaksi.setCashKredit(transaksiDTO.getCashKredit());
        transaksi.setSisa(transaksiDTO.getSisa());
        transaksi.setTtlBayarHemat(transaksiDTO.getTtlBayarHemat());
        transaksi.setTanggal(now);
        transaksi.setDelFlag(1);

        // Set notification dates
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        transaksi.setTanggalNotif7(calendar.getTime());
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, 1);
        transaksi.setTanggalNotif30(calendar.getTime());
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, 3);
        transaksi.setTanggalNotif90(calendar.getTime());
        calendar.setTime(now);
        calendar.add(Calendar.MONTH, 4);
        transaksi.setTanggalNotif120(calendar.getTime());
        calendar.setTime(now);
        calendar.add(Calendar.YEAR, 1);
        transaksi.setTanggalNotif365(calendar.getTime());

        Transaksi savedTransaksi = transaksiRepository.save(transaksi);

        List<BarangTransaksiDTO> produkList = transaksiDTO.getProduk();
        for (BarangTransaksiDTO barangDTO : produkList) {
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
            barangTransaksi.setDelFlag(1);
            barangTransaksiRepository.save(barangTransaksi);

            // Update stock
            int sisaStok = barang.getJumlahStok() - barangDTO.getQty();
            if (sisaStok < 0) {
                throw new BadRequestException("Stok Barang Habis");
            }
            StokAwal stokAwal = new StokAwal();
            stokAwal.setQty(String.valueOf(barangDTO.getQty()));
            stokAwal.setBarcodeBarang(barangDTO.getBarcodeBarang());
            stokAwal.setTanggal(now);
            barang.setJumlahStok(sisaStok);
            barangRepository.save(barang);
            stokAwalrepository.save(stokAwal);
        }

        // Update Kas Harian
        String cash = transaksiDTO.getCashKredit();
        int pembayaran = transaksiDTO.getPembayaran();
        int kekurangan = Integer.parseInt(transaksiDTO.getKekurangan());
        int penjualan = pembayaran + kekurangan;

        KasHarian kasHarian = new KasHarian();
        kasHarian.setTransaksi(savedTransaksi);
        kasHarian.setPenjualan(String.valueOf(pembayaran));
        kasHarian.setTimestamp(now);

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
        omzet.setTransaksi(savedTransaksi);
        omzet.setNmCustomer(customer.getNama_customer());
        omzet.setSalesman(salesman);
        omzet.setTgl(now);
        omzetRepository.save(omzet);

        // Insert into Persediaan Awal
        PersediaanAwal persediaanAwal = new PersediaanAwal();
        persediaanAwal.setTransaksi(savedTransaksi);
        persediaanAwal.setNominal(String.valueOf(transaksiDTO.getTotalBelanja()));
        persediaanAwal.setTanggal(now);
        persediaanAwalRepository.save(persediaanAwal);

        // Update Penjualan Tabel Persediaan
        updatePenjualanTabelPersediaan(now);

        serviceBarang.setTaken("Y");
        serviceBarang.setTanggalAmbil(now);
        serviceRepository.save(serviceBarang);

        List<Take> takeList = takeRepository.findByIdTT(id);
        int rtake = takeList.size();
        double service = serviceBarang.getBiayaService();
        double bagi = service / 90000;
        double hasil = Math.round(bagi * 10) / 10.0;
        if (rtake > 0) {
            double join = hasil / (rtake + 1);
            double nominal = service / (rtake + 1);
            for (Take take : takeList) {
                PoinHistory poinHistory = new PoinHistory();
                Teknisi teknisi = teknisiRepository.findById(take.getId_tekinisi().getId()).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found"));
                Poin poin = poinRepository.findByIdTeknisi(teknisi.getId()).get();
                double poinAwal = poin.getPoin();
                double hasilPoin = join + poinAwal;
                poin.setPoin(hasilPoin);
                poinHistory.setTeknisi(take.getId_tekinisi());
                poinHistory.setTanggal(now);
                poinHistory.setPoin(join);
                poinHistory.setKeterangan(String.valueOf(id));
                poinHistory.setNominal(Double.hashCode(nominal));
                poinRepository.save(poin);
                poinHistoryRepository.save(poinHistory);
            }
            PoinHistory poinHistory = new PoinHistory();
            Poin poin = poinRepository.findByIdTeknisi(serviceBarang.getTeknisi().getId()).get();
            double poinAwal = poin.getPoin();
            double hasilPoin = join + poinAwal;
            poin.setPoin(hasilPoin);
            poinHistory.setTeknisi(serviceBarang.getTeknisi());
            poinHistory.setTanggal(now);
            poinHistory.setPoin(join);
            poinHistory.setKeterangan(String.valueOf(id));
            poinHistory.setNominal(Double.hashCode(nominal));
            poinRepository.save(poin);
            poinHistoryRepository.save(poinHistory);
        } else {
            PoinHistory poinHistory = new PoinHistory();
            Poin poin = poinRepository.findByIdTeknisi(serviceBarang.getTeknisi().getId()).orElseThrow(() -> new NotFoundException("ID Not Found"));
            double poinAwal = poin.getPoin();
            double hasilPoin = hasil + poinAwal;
            poin.setPoin(hasilPoin);
            poinHistory.setTeknisi(serviceBarang.getTeknisi());
            poinHistory.setTanggal(now);
            poinHistory.setPoin(hasil);
            poinHistory.setKeterangan(String.valueOf(id));
            poinHistory.setNominal(Double.hashCode(service));
            poinRepository.save(poin);
            poinHistoryRepository.save(poinHistory);
        }

        return savedTransaksi;
    }

    // Method to update Penjualan Tabel Persediaan
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
    public List<ServiceBarang> getAll(){
        return serviceRepository.findAll();
    }
    public List<TglKonf> getAllKonfirm(Long id){
        return tglKonfRepository.findByIdTT(id);
    }
    public Map<String , Boolean> deleteTglKonf(Long id){
        try {
            tglKonfRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
    public ServiceBarang getById(Long id){
        return serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }
    public Map<String, Boolean> delete(Long id ) {
        try {
            serviceRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
    public List<ServiceBarang> getByTanggalAndStatus(Date tanggalAwal , Date tanggalAkhir , String status){
        return serviceRepository.findByTanggalAndStatus(tanggalAwal , tanggalAkhir , status);
    }
    public List<ServiceBarang> getByTaken(){
        return serviceRepository.findByTaken();
    }

    public ServiceBarang editBiayaService(EditBiayaServiceDTO editDataDTO, Long id) {
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        serviceBarang.setBiayaService(editDataDTO.getBiaya_service());
        serviceBarang.setBiayaSparepart(editDataDTO.getBiaya_sparepart());
        serviceBarang.setTotal(editDataDTO.getTotal());
        return serviceRepository.save(serviceBarang);
    }

    public PoinHistory editPoinHistory(EditPoinDTO editPoinDTO , String id) {
        PoinHistory poinHistory = poinHistoryRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Service Not Found"));
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(Long.valueOf(id)).orElseThrow(() -> new NotFoundException("Id Not Found"));
        Poin poin = poinRepository.findByIdTeknisi(serviceBarang.getIdTT()).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found"));
        double poinlama = poinHistory.getPoin();
        double totalPoinLama = poin.getPoin() - poinlama;
        double totalPoinBaru = totalPoinLama + editPoinDTO.getPoin();
        poin.setPoin(totalPoinBaru);
        poinRepository.save(poin);
        poinHistory.setPoin(editPoinDTO.getPoin());
        return poinHistoryRepository.save(poinHistory);
    }

    public ServiceBarang editTandaTerima(EditIdTtDTO editIdTtDTO, Long id) {
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id)
                .orElseThrow(() -> new NotFoundException("Id Not Found"));
        if (serviceBarang.getIdTT().equals(editIdTtDTO.getId_tt())){
            throw new  NotFoundException("Id Already Exists");
        }
        serviceBarang.setIdTT(editIdTtDTO.getId_tt());
        return serviceRepository.save(serviceBarang);
    }

    public ServiceBarang editStatusTandaTerima(EditStatusTtDTO editStatusTtDTO, Long id) {
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id)
                .orElseThrow(() -> new NotFoundException("Id Not Found"));
        serviceBarang.setStatusEnd(editStatusTtDTO.getStatusEnd());
        return serviceRepository.save(serviceBarang);
    }

    public Map<String , Boolean> deleteStatus(Long id) {
        try {
            statusRepository.deleteById(id);
            Map<String , Boolean> res = new HashMap<>();
            res.put("Delete", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }

}

