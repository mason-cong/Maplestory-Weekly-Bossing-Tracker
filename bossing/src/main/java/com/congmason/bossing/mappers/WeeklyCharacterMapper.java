package com.congmason.bossing.mappers;

import com.congmason.bossing.dto.WeeklyCharacterDto;
import com.congmason.bossing.entity.WeeklyCharacter;

public interface WeeklyCharacterMapper {

    WeeklyCharacter fromDto(WeeklyCharacterDto weeklyCharacterDto);
    WeeklyCharacterDto toDto(WeeklyCharacter weeklyCharacter);

}
