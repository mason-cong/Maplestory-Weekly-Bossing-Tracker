package com.congmason.bossing.mappers.impl;

import com.congmason.bossing.dto.WeeklyCharacterDto;
import com.congmason.bossing.entity.WeeklyCharacter;
import com.congmason.bossing.mappers.WeeklyBossMapper;
import com.congmason.bossing.mappers.WeeklyCharacterMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WeeklyCharacterMapperImpl implements WeeklyCharacterMapper {

    private final WeeklyBossMapper bossMapper;

    public WeeklyCharacterMapperImpl(WeeklyBossMapper bossMapper) {
        this.bossMapper = bossMapper;
    }

    @Override
    public WeeklyCharacter fromDto(WeeklyCharacterDto weeklyCharacterDto) {
        return new WeeklyCharacter(
                weeklyCharacterDto.id(),
                weeklyCharacterDto.characterClass(),
                weeklyCharacterDto.characterLevel(),
                weeklyCharacterDto.characterName(),
                null,
                Optional.ofNullable(weeklyCharacterDto.weeklyBosses())
                        .map(weeklyBosses -> weeklyBosses.stream()
                                .map(bossMapper::fromDto)
                                .toList()).orElse(null)
        );
    }

    @Override
    public WeeklyCharacterDto toDto(WeeklyCharacter weeklyCharacter) {
        return new WeeklyCharacterDto(
                weeklyCharacter.getId(),
                weeklyCharacter.getCharacterClass(),
                weeklyCharacter.getCharacterLevel(),
                weeklyCharacter.getCharacterName(),
                Optional.ofNullable(weeklyCharacter.getWeeklyBosses())
                        .map(weeklyBosses -> weeklyBosses.stream()
                                .map(bossMapper::toDto)
                                .toList()).orElse(null)
        );
    }
}
