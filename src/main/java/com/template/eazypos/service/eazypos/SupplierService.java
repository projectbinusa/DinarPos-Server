package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.exception.CommonResponse;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.exception.ResponseHelper;
import com.template.eazypos.model.Suplier;
import com.template.eazypos.repository.SuplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierService {
    @Autowired
    private SuplierRepository suplierRepository;

    // Menambahkan supplier baru
    public Suplier add(Suplier supplier){
        if (suplierRepository.findByCode(supplier.getKodeSuplier()).isPresent()){
            throw new BadRequestException("Code telah digunakan");
        }
        supplier.setDelFlag(1);
        return suplierRepository.save(supplier);
    }

    // Mengambil supplier berdasarkan ID
    public Suplier get(Long id) {
        return suplierRepository.findById(id).orElseThrow(() -> new NotFoundException("ID supplier tidak ditemukan"));
    }

    // Mengambil semua supplier yang tersimpan dalam database
    public List<Suplier> getAll(){
        return suplierRepository.findAllSuplier();
    }

    // Mengedit data supplier berdasarkan ID
    public Suplier edit(Suplier suplier , Long id){
        Suplier update = suplierRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setKodeSuplier(suplier.getKodeSuplier());
        update.setAlamatSuplier(suplier.getAlamatSuplier());
        update.setKeterangan(suplier.getKeterangan());
        update.setNamaSuplier(suplier.getNamaSuplier());
        update.setNoTelpSuplier(suplier.getNoTelpSuplier());
        return suplierRepository.save(update);
    }

    // Menghapus supplier berdasarkan ID
    public CommonResponse<String> deleteSupplier(Long id) {
        suplierRepository.deleteById(id);
        return ResponseHelper.ok("Supplier deleted successfully");
    }

    // Mengambil daftar supplier dengan paginasi dan sorting
    public Page<Suplier> getAllWithPagination(Long page, Long limit, String sort, String search) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (sort.startsWith("-")) {
            sort = sort.substring(1);
            direction = Sort.Direction.DESC;
        }

        Pageable pageable;
        if (direction == Sort.Direction.ASC) {
            pageable = PageRequest.of(Math.toIntExact(page - 1), Math.toIntExact(limit), Sort.by(sort).ascending());
        } else {
            pageable = PageRequest.of(Math.toIntExact(page - 1), Math.toIntExact(limit), Sort.by(sort).descending());
        }

        if (search != null && !search.isEmpty()) {
            return suplierRepository.findAllByKeyword(search, pageable);
        } else {
            return suplierRepository.findAll(pageable);
        }
    }
}
