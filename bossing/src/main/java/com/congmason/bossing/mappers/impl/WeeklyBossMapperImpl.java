package com.congmason.bossing.mappers.impl;

import com.congmason.bossing.dto.WeeklyBossDto;
import com.congmason.bossing.entity.WeeklyBoss;
import com.congmason.bossing.mappers.WeeklyBossMapper;
import org.springframework.stereotype.Component;

@Component
public class WeeklyBossMapperImpl implements WeeklyBossMapper {

    @Override
    public WeeklyBoss fromDto(WeeklyBossDto weeklyBossDto) {
        return new WeeklyBoss(
                weeklyBossDto.id(),
                weeklyBossDto.bossName(),
                weeklyBossDto.partySize(),
                weeklyBossDto.crystalValue(),
                null
        );
    }

    @Override
    public WeeklyBossDto toDto(WeeklyBoss weeklyBoss) {
        return new WeeklyBossDto(
                weeklyBoss.getId(),
                weeklyBoss.getBossName(),
                weeklyBoss.getPartySize(),
                weeklyBoss.getCrystalValue()
        );
    }
}
