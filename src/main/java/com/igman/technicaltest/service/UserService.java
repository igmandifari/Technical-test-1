package com.igman.technicaltest.service;

import com.igman.technicaltest.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Service;

//@Service
public interface UserService extends UserDetailsService {
//    AppUser loadUserByUserId(String id);
    AppUser loadUserByUserId(String id);
}
