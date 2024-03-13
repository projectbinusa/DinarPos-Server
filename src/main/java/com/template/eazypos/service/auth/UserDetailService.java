package com.template.eazypos.service.auth;


import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.Pengguna;
import com.template.eazypos.model.User;
import com.template.eazypos.repository.PenggunaRepository;
import com.template.eazypos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    PenggunaRepository penggunaRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        Pengguna user;
        if (penggunaRepository.existsByUsername(username)) {
            user = penggunaRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Username Not Found"));
        }else {
            throw new NotFoundException("User Not Found with username or email: " + username);
        }
        return UserDetail.buildUser(user);
    }

}
