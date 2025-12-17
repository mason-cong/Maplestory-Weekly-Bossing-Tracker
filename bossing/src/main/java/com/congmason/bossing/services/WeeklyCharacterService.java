package com.congmason.bossing.services;

import com.congmason.bossing.entity.WeeklyCharacter;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface WeeklyCharacterService {
    List<WeeklyCharacter> listWeeklyCharacters(Long userId);
    WeeklyCharacter createWeeklyCharacter(Long userId, WeeklyCharacter weeklyCharacter);
    Optional<WeeklyCharacter> getWeeklyCharacter(Long userId, Long weeklyCharacterId);
    WeeklyCharacter updateWeeklyCharacter(Long userId, Long weeklyCharacterId, WeeklyCharacter weeklyCharacter);
    void deleteWeeklyCharacter(Long userId, Long weeklyCharacterId);
    void updateWeeklyMesos(Long userId, Long weeklyCharacterId);
    Long weeklyCharacterMeso(Long userId, Long weeklyCharacterId);
}
