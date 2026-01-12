package com.congmason.bossing.controllers;

import com.congmason.bossing.dto.ForgotPasswordRequest;
import com.congmason.bossing.dto.ResetPasswordRequest;
import com.congmason.bossing.entity.PasswordResetToken;
import com.congmason.bossing.entity.User;
import com.congmason.bossing.repository.PasswordResetTokenRepository;
import com.congmason.bossing.services.EmailService;
import com.congmason.bossing.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @PostMapping("/forgot-password")
    @Transactional
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        try {
            String email = request.getEmail();

            // Find user by email
            User user = userService.findByEmail(email);
            if (user == null) {
                // Don't reveal if email exists (security best practice)
                return ResponseEntity.ok(Map.of("message", "If email exists, reset link sent"));
            }

            // Delete any existing tokens for this user
            tokenRepository.deleteByUser(user);

            // Generate reset token (valid for 1 hour)
            String resetToken = UUID.randomUUID().toString();
            LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);

            // Create and save token entity
            PasswordResetToken token = new PasswordResetToken(resetToken, user, expiryDate);
            tokenRepository.save(token);

            // Send email with reset link
            String resetUrl = frontendUrl + "/reset-password?token=" + resetToken;
            emailService.sendPasswordResetEmail(email, resetUrl);

            return ResponseEntity.ok(Map.of("message", "Password reset email sent"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to process request"));
        }
    }

    @PostMapping("/reset-password")
    @Transactional
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            String tokenString = request.getToken();
            String newPassword = request.getNewPassword();

            // Find token
            Optional<PasswordResetToken> optionalToken = tokenRepository.findByToken(tokenString);
            if (optionalToken.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Invalid token"));
            }

            PasswordResetToken token = optionalToken.get();

            // Check if token is expired
            if (token.isExpired()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Token has expired"));
            }

            // Check if token was already used
            if (token.isUsed()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Token already used"));
            }

            // Update user password
            User user = token.getUser();
            user.setPassword(passwordEncoder.encode(newPassword));
            userService.save(user);

            // Mark token as used
            token.setUsed(true);
            tokenRepository.save(token);

            // Delete all tokens for this user
            tokenRepository.deleteByUser(user);

            return ResponseEntity.ok(Map.of("message", "Password reset successful"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to reset password"));
        }
    }
}