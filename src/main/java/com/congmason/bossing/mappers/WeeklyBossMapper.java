package com.congmason.bossing.mappers;

import com.congmason.bossing.dto.WeeklyBossDto;
import com.congmason.bossing.entity.WeeklyBoss;

public interface WeeklyBossMapper {

    WeeklyBoss fromDto(WeeklyBossDto weeklyBossDto);
    WeeklyBossDto toDto(WeeklyBoss weeklyBoss);

}
