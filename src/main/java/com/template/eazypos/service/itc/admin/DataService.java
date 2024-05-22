package com.template.eazypos.service.itc.admin;

import com.template.eazypos.dto.AddServiceDTO;
import com.template.eazypos.dto.TakenServiceDTO;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.BarangTransaksi;
import com.template.eazypos.model.Customer;
import com.template.eazypos.model.ServiceBarang;
import com.template.eazypos.model.Status;
import com.template.eazypos.repository.CustomerRepository;
import com.template.eazypos.repository.ServiceRepository;
import com.template.eazypos.repository.StatusRepository;
import com.template.eazypos.repository.TeknisiRepository;
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
    public List<ServiceBarang> getAll(){
        return serviceRepository.findAll();
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

