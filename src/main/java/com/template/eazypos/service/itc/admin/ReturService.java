package com.template.eazypos.service.itc.admin;

import com.template.eazypos.dto.AddServiceDTO;
import com.template.eazypos.dto.GetServiceReturDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Retur;
import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.repository.CustomerRepository;
import com.template.eazypos.repository.ReturRepository;
import com.template.eazypos.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReturService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private ReturRepository returRepository;

    @Autowired
    private CustomerRepository customerRepository;

    // Proses retur barang layanan berdasarkan ID layanan yang diberikan
    public ServiceBarang retur(Long id , AddServiceDTO serviceDTO){
        ServiceBarang service = new ServiceBarang();
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
        service.setTaken("N");
        service.setAlamat(customer.getAlamat());
        service.setNama(customer.getNama_customer());
        ServiceBarang serviceBarang = serviceRepository.save(service);

        Retur retur = new Retur();
        retur.setTanggalRetur(new Date());
        retur.setTTLama(serviceRepository.findByIdTT(id).orElseThrow(() -> new NotFoundException("Id Service Old Not Found")));
        retur.setTTBaru(serviceBarang);
        returRepository.save(retur);

        return serviceBarang;
    }

    // Mengambil semua entitas Retur yang tersimpan
    public List<GetServiceReturDTO> getAll() {
        return returRepository.findAllReturWithoutTeknisi();
    }

    // Mengambil layanan yang siap dengan status "READY" dalam rentang tanggal tertentu
    public List<ServiceBarang> getServiceReady(String tglAwal, String tglAkhir) {
        return serviceRepository.findByStatusEndContainingAndTakenAndTglMasukBetween("%READY%", "Y", tglAwal, tglAkhir);
    }

    // Mengambil layanan dengan status "N_A" dalam rentang tanggal tertentu
    public List<ServiceBarang> getServiceNA(String tglAwal, String tglAkhir) {
        return serviceRepository.findByStatusEndAndTglMasukBetween("N_A", tglAwal, tglAkhir);
    }

    // Mengambil layanan berdasarkan status dan rentang tanggal masuk tertentu
    public List<ServiceBarang> getServiceByStatusAndTanggalMasuk(String status, String tglAwal, String tglAkhir) {
        return serviceRepository.findByStatusAndTanggalMasukBetween(status, tglAwal, tglAkhir);
    }

    // Mengambil layanan yang telah diretur dalam rentang tanggal tertentu
    public List<ServiceBarang> getServiceRetur(String tglAwal, String tglAkhir) {
        return serviceRepository.findServiceRetur(tglAwal, tglAkhir);
    }

    // Mengambil layanan yang dibatalkan dalam rentang tanggal tertentu
    public List<ServiceBarang> getServiceCancel(String tglAwal, String tglAkhir) {
        return serviceRepository.findServiceCansel(tglAwal, tglAkhir);
    }

    // Mengambil layanan yang memiliki notifikasi dalam rentang tanggal tertentu
    public List<ServiceBarang> getServiceNotif(String tglAwal, String tglAkhir) {
        return serviceRepository.findServicesNotif();
    }

    // Mengambil layanan yang telah diambil dalam rentang tanggal tertentu
    public List<ServiceBarang> getTakenServices(String tglAwal, String tglAkhir) {
        return serviceRepository.findTakenServices(tglAwal, tglAkhir);
    }
}
