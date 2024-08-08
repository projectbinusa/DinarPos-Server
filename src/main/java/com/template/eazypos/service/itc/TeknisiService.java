package com.template.eazypos.service.itc;

import com.template.eazypos.dto.TeknisiDTO;
import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.*;
import com.template.eazypos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeknisiService {
    @Autowired
    private TeknisiRepository teknisiRepository;
    @Autowired
    private PenggunaRepository penggunaRepository;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private PoinRepository poinRepository;
    @Autowired
    private ServiceRepository serviceBarangRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private BonBarangRepository bonBarangRepository;

    // Menambahkan data Teknisi baru berdasarkan TeknisiDTO
    public Teknisi add(TeknisiDTO teknisiDTO){
        Teknisi teknisi = new Teknisi();
        teknisi.setTotalPoin(0);
        teknisi.setStatus("Y");
        teknisi.setBagian(teknisiDTO.getBagian());
        teknisi.setNohp(teknisiDTO.getNohp());
        teknisi.setNama(teknisiDTO.getNama());
        teknisi.setAlamat(teknisiDTO.getAlamat());
        Teknisi saveTeknisi = teknisiRepository.save(teknisi);

        Poin poin = new Poin();
        poin.setPoin(0.0);
        poin.setTeknisi(saveTeknisi);
        poinRepository.save(poin);
        Pengguna pengguna = new Pengguna();
        if (penggunaRepository.findByUsername(teknisiDTO.getNama()).isPresent()){
            throw new BadRequestException("Username Pengguna sudah digunakan");
        }
        pengguna.setUsernamePengguna(teknisi.getNama());
        pengguna.setNamaPengguna(teknisi.getNama());
        pengguna.setLevelPengguna("Teknisi");
        pengguna.setDelFlag(1);
        String userPass = teknisiDTO.getPassword().trim();
        boolean PasswordIsNotValid = !userPass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}");
        if (PasswordIsNotValid) throw new BadRequestException("Password not valid!");
        String encodedPassword = encoder.encode(teknisiDTO.getPassword());
        pengguna.setPasswordPengguna(encodedPassword);
        pengguna.setLastLogin(new Date());
        pengguna.setRoleToko("itc");

        penggunaRepository.save(pengguna);
        return saveTeknisi;
    }

    // Mengambil semua data Teknisi
    public List<Teknisi> getAll() {
        return teknisiRepository.findAllWithStatusY();
    }

    // Mengambil data Teknisi berdasarkan ID
    public Teknisi getById(Long id){
        return teknisiRepository.findById(id).orElseThrow(() -> new NotFoundException("Id  tidak dinemukan"));
    }

    // Mengambil data Teknisi berdasarkan nama
    public Teknisi getByNama(String username) {
        return teknisiRepository.findByNama(username).orElseThrow(() -> new NotFoundException("Username Not Found"));
    }

    // Mengedit data Teknisi berdasarkan TeknisiDTO
    public Teknisi put(TeknisiDTO teknisiDTO, Long id) {
        Teknisi update = teknisiRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id Teknisi tidak ditemukan"));

        // Update data selain password
        update.setNama(teknisiDTO.getNama());
        update.setAlamat(teknisiDTO.getAlamat());
        update.setBagian(teknisiDTO.getBagian());
        update.setNohp(teknisiDTO.getNohp());

        return teknisiRepository.save(update);
    }

    // Menghapus data Teknisi berdasarkan ID
    public Map<String, Boolean> deleteTeknisi(Long id) {
        try {
            Teknisi teknisi = teknisiRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
            teknisi.setStatus("N");
            teknisiRepository.save(teknisi);

            Map<String, Boolean> response = new HashMap<>();
            response.put("Deleted", Boolean.TRUE);
            return response;
        } catch (Exception e) {
            return Collections.singletonMap("Deleted", Boolean.FALSE);
        }
    }

    //  Mengambil halaman data Teknisi dengan pagination, sorting, dan pencarian
    public Page<Teknisi> getAllWithPagination(Long page, Long limit, String sort, String search) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sort.startsWith("-")) {
            sort = sort.substring(1);
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(Math.toIntExact(page - 1), Math.toIntExact(limit), direction, sort);
        if (search != null && !search.isEmpty()) {
            return teknisiRepository.findAllByKeyword(search, pageable);
        } else {
            return teknisiRepository.findAllWithStatusY(pageable);
        }
    }
}
