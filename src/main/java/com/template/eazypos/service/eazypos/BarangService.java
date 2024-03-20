package com.template.eazypos.service.eazypos;

import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Barang;
import com.template.eazypos.model.Customer;
import com.template.eazypos.model.Salesman;
import com.template.eazypos.repository.BarangRepository;
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
public class BarangService {
    @Autowired
    private BarangRepository barangRepository;

    public Barang add(Barang barang){
        if(barangRepository.findByBarcodeBarang(barang.getBarcodeBarang()).isPresent()){
            throw new BadRequestException("Barcode sudah digunakan");
        }
        barang.setIdSuplier(0L);
        barang.setDelFlag(1);
        barang.setJumlahStok(0);
        return barangRepository.save(barang);
    }
    public  Barang getByBarcode(String barcode) {
        return barangRepository.findByBarcode(barcode);
    }
    public Barang get(Long id) {
        return barangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }
    public List<Barang> getAll(){
        return barangRepository.findAllBarang();
    }
    public Barang edit(Barang barang , Long id){
        Barang update = barangRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
        update.setBarcodeBarang(barang.getBarcodeBarang());
        update.setHargaBarang(barang.getHargaBarang());
        update.setHargaBeli(barang.getHargaBeli());
        update.setUnit(barang.getUnit());
        update.setNamaBarang(barang.getNamaBarang());
        return barangRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id ) {
        try {
            barangRepository.deleteById(id);
            Map<String, Boolean> res = new HashMap<>();
            res.put("Deleted", Boolean.TRUE);
            return res;
        } catch (Exception e) {
            return null;
        }
    }
    public Page<Barang> getAllWithPagination(Long page, Long limit, String sort, String search) {

        Sort.Direction direction = Sort.Direction.ASC;
        if (sort.startsWith("-")) {
            sort = sort.substring(1);
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(Math.toIntExact(page - 1), Math.toIntExact(limit), direction, sort);
        if (search != null && !search.isEmpty()) {
            return barangRepository.findAllByKeyword(search, pageable);
        } else {
            return barangRepository.findAll(pageable);
        }
    }
}
