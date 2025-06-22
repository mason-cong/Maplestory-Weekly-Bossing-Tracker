package com.congmason.bossing.services;

import com.congmason.bossing.entity.WeeklyBoss;

import java.util.List;
import java.util.Optional;

public interface WeeklyBossService {
    List<WeeklyBoss> listBosses(Long weeklyCharacterId);
    WeeklyBoss createBoss(Long weeklyCharacterId, WeeklyBoss boss);
    Optional<WeeklyBoss> getWeeklyBoss(Long weeklyCharacterId, Long weeklyBossId);
    WeeklyBoss updateBoss(Long weeklyCharacterId, Long bossId, WeeklyBoss boss);
    void deleteBoss(Long weeklyCharacterId, Long weeklyBossId);
}
