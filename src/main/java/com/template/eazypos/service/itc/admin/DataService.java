package com.template.eazypos.service.itc.admin;

import com.template.eazypos.dto.*;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.*;
import com.template.eazypos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        service.setStatusEnd("N_A");
        service.setAlamat(customer.getAlamat());
        service.setNama(customer.getNama_customer());
        return serviceRepository.save(service);
    }
    public Status takenService(TakenServiceDTO takenServiceDTO){
        ServiceBarang serviceBarang = serviceRepository.findByIdTT(takenServiceDTO.getId_teknisi()).orElseThrow(() -> new NotFoundException("Id Service Not Found"));
        serviceBarang.setStatusEnd("PROSES");
        serviceBarang.setTeknisi(teknisiRepository.findById(takenServiceDTO.getId_teknisi()).get());
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
        return serviceRepository.save(serviceBarang);
    }
    public List<ServiceBarang> getAll(){
        return serviceRepository.findAll();
    }
    public List<TglKonf> getAllKonfirm(Long id){
        return tglKonfRepository.findByIdTT(id);
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

}

