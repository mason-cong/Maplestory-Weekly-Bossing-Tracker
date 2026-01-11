package com.congmason.bossing.repository;

import com.congmason.bossing.entity.PasswordResetToken;
import com.congmason.bossing.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByUser(User user);

    @Transactional
    void deleteByExpiryDateBefore(LocalDateTime date);

    @Transactional
    void deleteByUser(User user);
}