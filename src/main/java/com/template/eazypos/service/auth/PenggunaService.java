package com.template.eazypos.service.auth;

import com.template.eazypos.dto.LoginRequest;
import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Pengguna;
import com.template.eazypos.repository.PenggunaRepository;
import com.template.eazypos.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PenggunaService {
    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    public Map<Object, Object> login(LoginRequest loginRequest) {
        Optional<Pengguna> userOptional = penggunaRepository.findByUsername(loginRequest.getUsername());
        Pengguna user = userOptional.orElseThrow(() -> new NotFoundException("Username not found"));

        if (encoder.matches(loginRequest.getPassword(), user.getPasswordPengguna())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateToken(authentication);
            user.setLastLogin(new Date());
            penggunaRepository.save(user);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedLastLogin = sdf.format(user.getLastLogin());
            Map<Object, Object> response = new HashMap<>();
            response.put("data", user);
            response.put("token", jwt);
            response.put("last_login", formattedLastLogin);
            response.put("type_token", user.getLevelPengguna());
            return response;
        }
        throw new NotFoundException("Password not found");
    }


    public Pengguna addPengguna(Pengguna user) {
        if (penggunaRepository.findByUsername(user.getUsernamePengguna()).isPresent()){
        throw new BadRequestException("Username Pengguna sudah digunakan");
        }
        String encodedPassword = encoder.encode(user.getPasswordPengguna());
        user.setPasswordPengguna(encodedPassword);
        user.setLastLogin(new Date());
        return penggunaRepository.save(user);
    }

    public Pengguna get(Long id) {
            return penggunaRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

    public List<Pengguna> getAll() {
            return penggunaRepository.findAll();
    }

    public Pengguna edit(Long id , Pengguna user) {
        Pengguna update = penggunaRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
        String encodedPassword = encoder.encode(user.getPasswordPengguna());
        update.setUsernamePengguna(user.getUsernamePengguna());
        update.setNamaPengguna(user.getNamaPengguna());
        update.setLevelPengguna(user.getLevelPengguna());
        update.setPasswordPengguna(encodedPassword);
        return penggunaRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id ) {
            try {
                penggunaRepository.deleteById(id);
                Map<String, Boolean> res = new HashMap<>();
                res.put("Deleted", Boolean.TRUE);
                return res;
            } catch (Exception e) {
                return null;
            }
    }


}
