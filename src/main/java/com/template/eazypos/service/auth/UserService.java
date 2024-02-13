package com.template.eazypos.service.auth;

import com.template.eazypos.dto.LoginRequest;
import com.template.eazypos.exception.BadRequestException;
import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.User;
import com.template.eazypos.repository.UserRepository;
import com.template.eazypos.security.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    public Map<Object, Object> login(LoginRequest loginRequest) {
        User user = userRepository.findByUser(loginRequest.getUsername()).orElseThrow(() -> new NotFoundException("Username not found"));
        if (encoder.matches(loginRequest.getPassword(), user.getPassword())) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateToken(authentication);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedLastLogin = sdf.format(new Date());
            Map<Object, Object> response = new HashMap<>();
            response.put("data", user);
            response.put("token", jwt);
            response.put("last_login", formattedLastLogin);
            response.put("type_token", user.getLevel());
            return response;
        }
        throw new NotFoundException("Password not found");
    }

    public User addUser(User user) {
        if (userRepository.existsByUser(user.getUser())){
        throw new BadRequestException("Username sudah digunakan");
        }
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User get(Long id) {
            return userRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak dinemukan"));
    }

    public List<User> getAll() {
            return userRepository.findAll();
    }

    public User edit(Long id , User user) {
        User update = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Id tidak ditemukan"));
        update.setUser(user.getUser());
        update.setLevel(user.getLevel());
        update.setPassword(user.getPassword());
        return userRepository.save(update);
    }
    public Map<String, Boolean> delete(Long id ) {
            try {
                userRepository.deleteById(id);
                Map<String, Boolean> res = new HashMap<>();
                res.put("Deleted", Boolean.TRUE);
                return res;
            } catch (Exception e) {
                return null;
            }
    }


}
