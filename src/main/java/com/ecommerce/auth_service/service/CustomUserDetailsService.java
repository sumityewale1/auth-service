package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.client.UserServiceClient;
import com.ecommerce.auth_service.model.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserServiceClient userServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserView user = userServiceClient.getUserByEmail(username);
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new User("sumit", "{noop}sumit@123", new ArrayList<>());

    }
}
