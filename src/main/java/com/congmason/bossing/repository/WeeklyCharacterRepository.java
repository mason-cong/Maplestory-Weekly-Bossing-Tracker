package com.congmason.bossing.repository;

import com.congmason.bossing.entity.WeeklyCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyCharacterRepository extends JpaRepository<WeeklyCharacter, Long> {
    List<WeeklyCharacter> findByUserId(Long userId);
    Optional<WeeklyCharacter> findByUserIdAndId(Long userId, Long weeklyCharacterId);
    Optional<WeeklyCharacter> findByUserIdAndCharacterName(Long userId, String characterName);
    void deleteByUserIdAndId(Long userId, Long weeklyCharacterId);

}
