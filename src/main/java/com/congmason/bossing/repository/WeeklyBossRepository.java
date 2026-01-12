package com.congmason.bossing.repository;

import com.congmason.bossing.entity.WeeklyBoss;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeeklyBossRepository extends JpaRepository<WeeklyBoss, Long> {
    List<WeeklyBoss> findByWeeklyCharacterId(Long weeklyCharacterId);
    Optional<WeeklyBoss> findByWeeklyCharacterIdAndId(Long weeklyCharacterId, Long weeklyBossId);
    void deleteByWeeklyCharacterIdAndId(Long weeklyCharacterId, Long weeklyBossId);
}
