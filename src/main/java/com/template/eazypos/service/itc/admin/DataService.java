package com.template.eazypos.service.itc.admin;

import com.template.eazypos.dto.*;
import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.*;
import com.template.eazypos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.google.api.client.util.Base64;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private EntityManager entityManager;

    // Method untuk mengonversi file menjadi URL dengan format Base64
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

    // Method untuk menambahkan layanan baru berdasarkan DTO yang diterima
    public ServiceBarang addService(AddServiceDTO serviceDTO){
        ServiceBarang service = new ServiceBarang();
        Long total_service = serviceRepository.countTotalService();
        for (Long i = 1L; i > total_service; i++) {
           if (!serviceRepository.existsByIdTT(i)){
               service.setIdTT(i);
           }
        }
        Customer customer = customerRepository.findById(serviceDTO.getId_customer()).orElseThrow(() -> new NotFoundException("Id Customer tidak dinemukan"));
        service.setCustomer(customer);
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
        service.setTaken("N");
        return serviceRepository.save(service);
    }

    // Method untuk memproses layanan yang telah diambil berdasarkan DTO yang diterima
    public Status prosesService(TakenServiceDTO takenServiceDTO , Long id){
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Service Not Found"));
        serviceBarang.setStatusEnd("PROSES");
        serviceBarang.setTeknisi(teknisiRepository.findById(takenServiceDTO.getId_teknisi()).orElseThrow((() -> new NotFoundException("Id Teknisi Not Found"))));
        Status status = new Status();
        status.setService(serviceRepository.findByIdTT(serviceBarang.getIdTT()).get());
        status.setStatus(takenServiceDTO.getStatus());
        status.setSolusi(takenServiceDTO.getSolusi());
        status.setTeknisi(teknisiRepository.findById(takenServiceDTO.getId_teknisi()).orElseThrow((() -> new NotFoundException("Id Teknisi Not Found"))));
        status.setTanggal(new Date());
        status.setKet(takenServiceDTO.getKet());
        status.setType(takenServiceDTO.getType());
        status.setValidasi("0");
        serviceRepository.save(serviceBarang);
        return statusRepository.save(status);
    }

    // Method untuk memproses layanan oleh teknisi
    public Status prosesServiceTeknisi(TakenServiceDTO takenServiceDTO , Long id){
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Service Not Found"));
        Status status = new Status();
        status.setTeknisi(serviceBarang.getTeknisi());
        status.setService(serviceRepository.findByIdTT(serviceBarang.getIdTT()).get());
        status.setStatus(takenServiceDTO.getStatus());
        status.setSolusi(takenServiceDTO.getSolusi());
        status.setTanggal(new Date());
        status.setKet(takenServiceDTO.getKet());
        status.setType(takenServiceDTO.getType());
        status.setValidasi("0");
        return statusRepository.save(status);
    }

    // Method untuk menyimpan foto "before" dari layanan
    public ServiceBarang fotoBefore(MultipartFile multipartFile , Long id){
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        serviceBarang.setFb(convertToBase64Url(multipartFile));
        return serviceRepository.save(serviceBarang);
    }

    // Method untuk menyimpan foto "after" dari layanan
    public ServiceBarang fotoAfter(MultipartFile multipartFile , Long id){
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        serviceBarang.setFa(convertToBase64Url(multipartFile));
        return serviceRepository.save(serviceBarang);
    }

    // Method untuk menangani pengambilan alih layanan oleh teknisi
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

    // Method untuk menangani administrasi layanan oleh admin
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

    // Method untuk mengkonfirmasi tanggal tertentu terkait layanan
    public TglKonf konfirm(KonfirmDTO konfirmDTO){
        TglKonf tglKonf = new TglKonf();

        tglKonf.setService(serviceRepository.findByIdTT(konfirmDTO.getId_service()).orElseThrow(() -> new NotFoundException("Id Service Not Found")));
        tglKonf.setTglKonf(konfirmDTO.getDate());
        return tglKonfRepository.save(tglKonf);
    }

    // Metode untuk memperbarui catatan
    public ServiceBarang updateNote(NoteDTO noteDTO , Long id){
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Service Not Found"));
        serviceBarang.setCatatan(noteDTO.getNote());
        return serviceRepository.save(serviceBarang);
    }

    // Metode untuk memperbarui data pelanggan
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

    // Metode untuk mendapatkan daftar Take berdasarkan id
    public List<Take> getTakeByIdTT(Long id) {
        return takeRepository.findByIdTT(id);
    }

    // Metode untuk mendapatkan daftar Status berdasarkan id
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
        int service = serviceBarang.getBiayaService();
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
            poinHistory.setNominal(service);
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

    // Mengambil persediaan terakhir sebelum tanggal tertentu dan mengembalikan nilai persediaan akhir sebagai integer
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

    // Menghasilkan nomor nota transaksi berdasarkan bulan dan tahun saat ini
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

    // Mengambil semua data dari repository service
    public List<ServiceBarang> getAll(){
        return serviceRepository.findAll();
    }

    // Mengambil semua data konfirmasi berdasarkan ID tertentu
    public List<TglKonf> getAllKonfirm(Long id){
        return tglKonfRepository.findByIdTT(id);
    }

    // Menghapus data konfirmasi berdasarkan ID dan mengembalikan status penghapusan
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

    // Mengambil data layanan berdasarkan ID
    public ServiceBarang getById(Long id){
        return serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

    // Menghapus data layanan berdasarkan ID dan mengembalikan status penghapusan
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

    // Mengambil daftar layanan berdasarkan rentang tanggal dan status
    public List<ServiceBarang> getByTanggalAndStatus(Date tanggalAwal , Date tanggalAkhir , String status){
        return serviceRepository.findByTanggalAndStatus(tanggalAwal , tanggalAkhir , status);
    }

    // Mengambil daftar layanan yang telah diambil
    public List<ServiceBarang> getByTaken(){
        return serviceRepository.findByTaken();
    }

    // Mengedit biaya layanan berdasarkan DTO editDataDTO
    public ServiceBarang editBiayaService(EditBiayaServiceDTO editDataDTO, Long id) {
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        serviceBarang.setBiayaService(editDataDTO.getBiaya_service());
        serviceBarang.setBiayaSparepart(editDataDTO.getBiaya_sparepart());
        serviceBarang.setTotal(editDataDTO.getTotal());
        return serviceRepository.save(serviceBarang);
    }

    // Mengedit riwayat poin berdasarkan DTO serta menghitung kembali total poin teknisi
    public PoinHistory editPoinHistory(EditPoinDTO editPoinDTO , String id) {
        PoinHistory poinHistory = poinHistoryRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Service Not Found"));
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(Long.valueOf(id)).orElseThrow(() -> new NotFoundException("Id Not Found"));
        Poin poin = poinRepository.findByIdTeknisi(serviceBarang.getIdTT()).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found"));
        double poinlama = poinHistory.getPoin();
        double totalPoinLama = poin.getPoin() - poinlama;
        double totalPoinBaru = totalPoinLama + editPoinDTO.getPoin();
        double nominal = editPoinDTO.getPoin() * 90000;
        poin.setPoin(totalPoinBaru);
        poinHistory.setNominal(Double.hashCode(nominal));
        poinRepository.save(poin);
        poinHistory.setPoin(editPoinDTO.getPoin());
        return poinHistoryRepository.save(poinHistory);
    }

    // Mengedit ID TT pada layanan berdasarkan ID
    @Transactional
    public ServiceBarang editTandaTerima(EditIdTtDTO editIdTtDTO, Long id) {
        ServiceBarang serviceBarang = serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id Not Found"));

        if (serviceRepository.existsByIdTT(editIdTtDTO.getId_tt())) {
            throw new NotFoundException("Id Already Exists");
        }

        // Detach the entity from the persistence context
        entityManager.detach(serviceBarang);

        // Change the ID
        serviceBarang.setIdTT(editIdTtDTO.getId_tt());

        // Merge the entity back into the persistence context
        return entityManager.merge(serviceBarang);
    }

    // Mengedit status Tanda Terima pada layanan berdasarkan ID
    public ServiceBarang editStatusTandaTerima(EditStatusTtDTO editStatusTtDTO, Long id) {
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(id)
                .orElseThrow(() -> new NotFoundException("Id Not Found"));
        serviceBarang.setStatusEnd(editStatusTtDTO.getStatusEnd());
        return serviceRepository.save(serviceBarang);
    }

    // Menghapus status berdasarkan ID dan mengembalikan status penghapusan
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

    // Mengambil data layanan dalam bentuk DTO berdasarkan bulan
    public List<ServiceDataDTO> findDataService(Date months) {
        List<ServiceDataDTO> data = serviceRepository.findDataService(months);
        return data.stream()
                .sorted((d1, d2) -> Long.compare(d2.getSuccess(), d1.getSuccess()))
                .collect(Collectors.toList());
    }

    // Menghitung total layanan kategori Elektro berdasarkan bulan
    public int getTotalServiceElektro(Date months) {
        return serviceRepository.countTotalServiceElektro(months);
    }

    // Menghitung total layanan kategori CPU berdasarkan bulan
    public int getTotalServiceCPU(Date months) {
        return serviceRepository.countTotalServiceCPU(months);
    }

    // Menghitung total layanan kategori Elektro yang sukses berdasarkan bulan
    public int getTotalServiceSuccessElektro(Date months) {
        return serviceRepository.countTotalServiceSuccessElektro(months);
    }

    // Menghitung total layanan bukan kategori Elektro berdasarkan bulan
    public int getTotalServiceNotElektro(Date months) {
        return serviceRepository.countTotalServiceNotElektro(months);
    }

    // Menghitung total layanan bukan kategori CPU berdasarkan bulan
    public int getTotalServiceNotCPU(Date months) {
        return serviceRepository.countTotalServiceNotCPU(months);
    }

    // Menghitung total layanan sukses kategori CPU berdasarkan bulan
    public int getTotalServiceSuccessCPU(Date months) {
        return serviceRepository.countTotalServiceSuccessCPU(months);
    }

    // Mengambil semua layanan
    public List<ServiceBarang> getService() {
        return serviceRepository.getService();
    }

    // Mengambil semua layanan yang telah diambil (status 'Taken')
    public List<ServiceBarang> getTakenN(){
        return serviceRepository.findByTakenN();
    }

    // Mencari layanan berdasarkan rentang tanggal dan status
    public List<ServiceBarang> filterServiceByDateAndStatus(Date awal, Date akhir, String status) {
        return serviceRepository.filterByDateAndStatus(awal, akhir, status);
    }

    // Mencari layanan berdasarkan status
    public List<ServiceBarang> filterServiceByStatus(String status) {
        return serviceRepository.filterByStatus(status);
    }

    // Mencari layanan berdasarkan rentang tanggal
    public List<ServiceBarang> filterServiceByDateRange(Date awal, Date akhir) {
        return serviceRepository.filterByDateRange(awal, akhir);
    }

    // Mengambil semua layanan yang dibatalkan (status 'CANCEL')
    public List<ServiceBarang> getServiceCancel() {
        return serviceRepository.findServiceCancel("CANCEL");
    }

    // Mengambil semua layanan yang dibatalkan dalam rentang tanggal tertentu (status 'CANCEL')
    public List<ServiceBarang> getTglFilterServiceCancel(Date awal, Date akhir) {
        return serviceRepository.findServiceCancelByDate("CANCEL", awal, akhir);
    }

    // Mengambil semua layanan yang ditangani oleh seorang teknisi berdasarkan ID teknisi
    public List<ServiceBarang> getMyServices(Long teknisiId) {
        return serviceRepository.findMyServices(teknisiId);
    }

    // Mengambil semua layanan retur yang ditangani oleh seorang teknisi berdasarkan ID teknisi
    public List<ServiceBarang> getMyServicesRetur(Long teknisiId) {
        return serviceRepository.findMyServicesRetur(teknisiId);
    }

    // Mengambil semua layanan yang telah diambil (status 'Taken')
    public List<ServiceBarang> getServiceTaken() {
        return serviceRepository.findServiceTaken();
    }

    // Menghitung total semua layanan yang ada
    public long countAllServices() {
        return serviceRepository.countAllServices();
    }

    // Menyimpan status baru 'PROSES' untuk layanan dan mencatatnya dalam entitas Status
    public Status aksiStatusNew(Long idTT, Long teknisiId, String status, String solusi, String ket, String validasi) {
        Optional<ServiceBarang> serviceBarangOptional = serviceRepository.findById(idTT);
        if (serviceBarangOptional.isPresent()) {
            ServiceBarang serviceBarang = serviceBarangOptional.get();
            serviceBarang.setStatusEnd("PROSES");
            serviceBarang.setTeknisi(teknisiRepository.findById(teknisiId).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found"))); // Assuming Teknisi entity has an appropriate constructor
            serviceRepository.save(serviceBarang);

            Status newStatus = new Status();
            newStatus.setService(serviceRepository.findByIdTT(idTT).orElseThrow(() -> new NotFoundException("Id Service Not Found")));
            newStatus.setTeknisi(teknisiRepository.findById(teknisiId).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found")));
            newStatus.setTanggal(new Date());
            newStatus.setStatus(status);
            newStatus.setSolusi(solusi);
            newStatus.setKet(ket);
            newStatus.setType(validasi);
            newStatus.setValidasi("0");


            return statusRepository.save(newStatus);
        }
       throw new NotFoundException("Service Not Found");
    }

    // Menyimpan status baru tanpa mengubah status layanan untuk entitas Status
    public Status aksiStatusPlus(Long idTT, Long teknisiId, String status, String solusi, String ket, String validasi) {
        Status newStatus = new Status();
        newStatus.setService(serviceRepository.findByIdTT(idTT).orElseThrow(() -> new NotFoundException("Id Service Not Found")));
        newStatus.setTeknisi(teknisiRepository.findById(teknisiId).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found")));
        newStatus.setTanggal(new Date());
        newStatus.setStatus(status);
        newStatus.setSolusi(solusi);
        newStatus.setKet(ket);
        newStatus.setType(validasi);
        newStatus.setValidasi("0");
        return statusRepository.save(newStatus);
    }

    // Mengambil alih layanan oleh seorang teknisi dan mencatatnya dalam entitas Take
    public Take aksiTakeOver(Long idTT, Long teknisiId, Long takeTeknisiId) {
        Optional<ServiceBarang> serviceBarangOptional = serviceRepository.findById(idTT);
        if (serviceBarangOptional.isPresent()) {
            ServiceBarang serviceBarang = serviceBarangOptional.get();
            serviceBarang.setTeknisi(teknisiRepository.findById(teknisiId).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found")));
            serviceRepository.save(serviceBarang);

            Take takeOver = new Take();
            takeOver.setService(serviceRepository.findByIdTT(idTT).orElseThrow(() -> new NotFoundException("Id Service Not Found")));
            takeOver.setId_tekinisi(teknisiRepository.findById(teknisiId).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found")));
            takeOver.setId_take(teknisiRepository.findById(teknisiId).orElseThrow(() -> new NotFoundException("Id Teknisi Not Found")));
            return takeRepository.save(takeOver);

        }
        throw new NotFoundException("Service Not Found");
    }

    // Mengambil semua layanan dengan pagination
    public Page<ServiceBarang> getAllWithPagination(Long page, Long limit, String sort, String search) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sort.startsWith("-")) {
            sort = sort.substring(1);
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(Math.toIntExact(page - 1), Math.toIntExact(limit), direction, sort);

        Long id = null;
        try {
            id = Long.parseLong(search);
        } catch (NumberFormatException e) {
        }

        if (id != null) {
            // Jika search adalah nomor ID, cari berdasarkan ID
            Optional<ServiceBarang> result = serviceRepository.findByIdAndTaken(id);
            return result.map(serviceBarang -> new PageImpl<>(Collections.singletonList(serviceBarang), pageable, 1))
                    .orElseGet(() -> new PageImpl<>(Collections.emptyList(), pageable, 0));
        } else if (search != null && !search.isEmpty()) {
            // Jika search adalah teks, cari berdasarkan teks
            return serviceRepository.findAllByKeywordAndTaken(search, pageable);
        } else {
            return serviceRepository.findAllByTaken(pageable);
        }
    }

    // Mengambil semua layanan yang telah diambil (status 'Taken') untuk pimpinan
    public List<ServiceBarang> getServiceTakenPimpinan() {
        return serviceRepository.findServiceTaken();
    }

    // Mengambil semua layanan yang telah diambil (status 'Taken') dalam rentang tanggal tertentu
    public List<ServiceBarang> getServiceTakenByDateRange(Date awal, Date akhir) {
        return serviceRepository.findServiceTakenByDateRange(awal, akhir);
    }
    public List<Object[]> getServiceBarang() {
        return serviceRepository.findServiceBarang();
    }

    public List<Object[]> getServiceBarangTaken() {
        return serviceRepository.findServiceBarangTaken();
    }

    public List<ServiceBarang> getServiceBarangTakenBetweenDates(Date awal, Date akhir) {
        return serviceRepository.findServiceBarangTakenBetweenDates(awal, akhir);
    }

    public List<ServiceBarang> getServiceBarangByDateRangeAndStatus(Date awal, Date akhir, String status) {
        return serviceRepository.findByStatusEndAndTanggalMasukBetween(status, awal, akhir);
    }

    public List<ServiceBarang> getServiceBarangByStatus(String status) {
        return serviceRepository.findByStatusEnd(status);
    }

    public List<ServiceBarang> getServiceBarangByDateRange(Date awal, Date akhir) {
        return serviceRepository.findByTanggalMasukBetween(awal, akhir);
    }
    public List<Status> getStatusByIdTt(Long idTt) {
        return statusRepository.findStatusByIdTt(idTt);
    }

    public List<TglKonf> getTglKonfimasiByIdTt(Long idTt) {
        return tglKonfRepository.findTglKonfimasiByIdTt(idTt);
    }

    public List<ServiceReportDTO> getServiceReportByMonth(Date month) {
        return serviceRepository.findDataServicePimpinan(month);
    }

    public int totalServiceElektro(String months) {
        return serviceRepository.totalServiceElektro(months);
    }

    public int totalServiceCpu(String months) {
        return serviceRepository.totalServiceCpu(months);
    }

    public int totalServiceSuccessElektro(String months) {
        return serviceRepository.totalServiceSuccessElektro(months);
    }

    public int totalServiceNotElektro(String months) {
        return serviceRepository.totalServiceNotElektro(months);
    }

    public int totalServiceNotCpu(String months) {
        return serviceRepository.totalServiceNotCpu(months);
    }

    public int totalServiceSuccessCpu(String months) {
        return serviceRepository.totalServiceSuccessCpu(months);
    }

}

