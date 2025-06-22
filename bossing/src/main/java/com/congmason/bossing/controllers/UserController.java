package com.congmason.bossing.controllers;

import com.congmason.bossing.dto.UserDto;
import com.congmason.bossing.entity.User;
import com.congmason.bossing.mappers.UserMapper;
import com.congmason.bossing.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register (@RequestBody UserDto userDto) {
        User registeredUser = null;
        try {
            registeredUser = userService.registerUser(userMapper.fromDto(userDto));
        } catch (Exception e) {
            return new ResponseEntity<>("An account with that email is already registered", HttpStatus.CONFLICT);
        }


        return ResponseEntity.ok("User " + registeredUser.getUsername() + " was created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<User> login (@RequestBody UserDto userDto, HttpServletRequest request) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(userDto.username(), userDto.password());
        Authentication authenticationResponse =
                this.authenticationManager.authenticate(authenticationRequest);

        /*SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

        HttpSession session = request.getSession();
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());*/

        User returningUser = userService.loginUser(userMapper.fromDto(userDto));

        return ResponseEntity.ok(returningUser);
    }

}
