package com.congmason.bossing.services;

import com.congmason.bossing.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    Long findByUsername(String username) throws UsernameNotFoundException;
    User registerUser(User user) throws Exception;
    User loginUser(User user);
}
