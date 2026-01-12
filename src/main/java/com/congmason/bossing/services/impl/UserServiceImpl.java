package com.congmason.bossing.services.impl;

import com.congmason.bossing.entity.User;
import com.congmason.bossing.repository.UserRepository;
import com.congmason.bossing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if (user.isPresent()) {
            var userObj = user.get();

            return new User(userObj.getId(), userObj.getUsername(), userObj.getEmail(), userObj.getPassword(), null);

        } else {
            throw new UsernameNotFoundException("user not found");
        }
    }

    @Override
    public Long findByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            var userObj = user.get();
            return userObj.getId();

        } else {
            throw new UsernameNotFoundException("user not found");
        }
    }

    @Override
    public User registerUser(User user) throws Exception {

        if (userRepository.existsByUsername(user.getEmail())) {
            throw new Exception("Email already used!" + HttpStatus.BAD_REQUEST);
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(newUser);
    }

    @Override
    public User loginUser(User user) {
        Optional<User> userT = userRepository.findByUsername(user.getUsername());

        if (userT.isPresent()) {
            var userObj = userT.get();

            return new User(userObj.getId(), userObj.getUsername(), userObj.getEmail(), userObj.getPassword(), null);

        } else {
            throw new UsernameNotFoundException("user not found");
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
