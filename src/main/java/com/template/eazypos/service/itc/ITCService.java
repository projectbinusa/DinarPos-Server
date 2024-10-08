package com.template.eazypos.service.itc;

import com.template.eazypos.dto.SalesmanDTO;
import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Marketting;
import com.template.eazypos.model.Pengguna;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.model.Teknisi;
import com.template.eazypos.repository.PenggunaRepository;
import com.template.eazypos.repository.SalesmanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ITCService {
    @Autowired
    private SalesmanRepository salesmanRepository;

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    PasswordEncoder encoder;

    public Salesman add(SalesmanDTO salesmanDTO){
        if (salesmanDTO.getUsername() == null || salesmanDTO.getUsername().trim().isEmpty()) {
            throw new BadRequestException("Username tidak boleh kosong!");
        }
        Salesman salesman = new Salesman();
        salesman.setTarget(salesmanDTO.getTarget());
        salesman.setNoTelpSalesman(salesmanDTO.getNo_hp());
        salesman.setNamaSalesman(salesmanDTO.getNama());
        salesman.setAlamatSalesman(salesmanDTO.getAlamat());
        salesman.setUsername(salesmanDTO.getUsername());
        salesman.setStatus("1");
        salesman.setDelFlag(1);

        Pengguna pengguna = new Pengguna();
        pengguna.setRoleToko("itc");
        pengguna.setNamaPengguna(salesmanDTO.getNama());
        pengguna.setLevelPengguna("Marketting");
        pengguna.setUsernamePengguna(salesmanDTO.getUsername());
        if (penggunaRepository.findByUsername(salesmanDTO.getUsername()).isPresent()){
            throw new BadRequestException("Username Pengguna sudah digunakan");
        }
        pengguna.setDelFlag(1);
        pengguna.setLastLogin(new Date());
        String userPass = salesmanDTO.getPassword().trim();
        boolean PasswordIsNotValid = !userPass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}");
        if (PasswordIsNotValid) throw new BadRequestException("Password not valid!");
        String encodedPassword = encoder.encode(salesmanDTO.getPassword());
        pengguna.setPasswordPengguna(encodedPassword);

        penggunaRepository.save(pengguna);
        return salesmanRepository.save(salesman);
    }
    public Salesman getById(Long id){
        return salesmanRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
    }
    public List<Salesman> getAll(){
        return salesmanRepository.findAllSalesmen();
    }
    public Salesman put(Long id , Salesman salesman){
        Salesman update = salesmanRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        update.setNamaSalesman(salesman.getNamaSalesman());
        update.setTarget(salesman.getTarget());
        update.setAlamatSalesman(salesman.getAlamatSalesman());
        update.setNoTelpSalesman(salesman.getNoTelpSalesman());
        return salesmanRepository.save(update);
    }
    public Salesman getByNama(String nama){
        return salesmanRepository.findByNama(nama).orElseThrow(() -> new NotFoundException("Nama Not Found"));
    }
    public Map<String, Boolean> delete(Long id) {
        try {
            Salesman salesman = salesmanRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
            salesman.setDelFlag(0);
            salesmanRepository.save(salesman);
            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }

}
