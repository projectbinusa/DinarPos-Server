package com.template.eazypos.service.itc.admin;

import com.template.eazypos.dto.PasswordAdminDTO;
import com.template.eazypos.dto.PasswordDTO;
import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Pengguna;
import com.template.eazypos.repository.PenggunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UbahPasswordService {
    @Autowired
    private PenggunaRepository penggunaRepository;


    @Autowired
    PasswordEncoder encoder;

    public Pengguna ubahPass(PasswordDTO passwordDTO , Long id){
        Pengguna update = penggunaRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
        boolean conPassword = encoder.matches(passwordDTO.getOld_password(), update.getPasswordPengguna());
        if (conPassword) {
            if (passwordDTO.getNew_password().equals(passwordDTO.getConfirm_new_password())) {
                String userPass = passwordDTO.getNew_password().trim();
                boolean PasswordIsNotValid = !userPass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}");
                if (PasswordIsNotValid) throw new BadRequestException("Password not valid!");
                String encodedPassword = encoder.encode(passwordDTO.getNew_password());
                update.setPasswordPengguna(encodedPassword);
                return penggunaRepository.save(update);
            } else {
                throw new BadRequestException("Password tidak sesuai");
            }
        } else {
            throw new NotFoundException("Password lama tidak sesuai");
        }
    }
    public Pengguna ubahPassAdmin(PasswordAdminDTO passwordDTO , Long id){
        Pengguna update = penggunaRepository.findById(id).orElseThrow(() -> new NotFoundException("Id Not Found"));
            if (passwordDTO.getNew_password().equals(passwordDTO.getConfirm_new_password())) {
                String userPass = passwordDTO.getNew_password().trim();
                boolean PasswordIsNotValid = !userPass.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}");
                if (PasswordIsNotValid) throw new BadRequestException("Password not valid!");
                String encodedPassword = encoder.encode(passwordDTO.getNew_password());
                update.setPasswordPengguna(encodedPassword);
                return penggunaRepository.save(update);
            } else {
                throw new BadRequestException("Password tidak sesuai");
            }
    }

}
