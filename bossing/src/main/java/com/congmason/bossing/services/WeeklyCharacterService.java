package com.congmason.bossing.services;

import com.congmason.bossing.entity.WeeklyCharacter;
import com.congmason.bossing.mappers.WeeklyCharacterMapper;

import java.util.List;
import java.util.Optional;

public interface WeeklyCharacterService {
    List<WeeklyCharacter> listWeeklyCharacters(Long id);
    WeeklyCharacter createWeeklyCharacter(WeeklyCharacter weeklyCharacter);
    Optional<WeeklyCharacter> getWeeklyCharacter(Long id);
    WeeklyCharacter updateWeeklyCharacter(Long id, WeeklyCharacter weeklyCharacter);
    void deleteWeeklyCharacter(Long id);
}
