package com.template.eazypos.service.auth;


import com.template.eazypos.exception.NotFoundException;
import com.template.eazypos.model.User;
import com.template.eazypos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user;
        if (userRepository.existsByUser(username)) {
            user = userRepository.findByUser(username).orElseThrow(() -> new NotFoundException("Username Not Found"));
        }else {
            throw new NotFoundException("User Not Found with username or email: " + username);
        }
        return UserDetail.buildUser(user);
    }

}
