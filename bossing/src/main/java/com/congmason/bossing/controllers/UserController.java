package com.congmason.bossing.controllers;

import com.congmason.bossing.authentication.JwtHelper;
import com.congmason.bossing.dto.LoginResponse;
import com.congmason.bossing.dto.UserDto;
import com.congmason.bossing.entity.User;
import com.congmason.bossing.mappers.UserMapper;
import com.congmason.bossing.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

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
    public ResponseEntity<?> login (@RequestBody UserDto userDto, HttpServletRequest request) {
        Authentication authenticationRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(userDto.username(), userDto.password());
        //Authentication authenticationResponse =
               // this.authenticationManager.authenticate(authenticationRequest);

        //SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

        //HttpSession session = request.getSession();
        //session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

        User returningUser = userService.loginUser(userMapper.fromDto(userDto));

        String token = JwtHelper.generateToken(userDto.username());

        return ResponseEntity.ok(new LoginResponse(userDto.username(), token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        logoutHandler.logout(request, response, authentication);

        request.getSession().removeAttribute("SPRING_SECURITY_CONTEXT");
        request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);


    }

    @PostMapping("/userInfo")
    public ResponseEntity<?> userInfo(@RequestBody UserDto user) throws Exception {
        Long currentUserId = userService.findByUsername(user.username());
        return ResponseEntity.ok(currentUserId);
    }

}
