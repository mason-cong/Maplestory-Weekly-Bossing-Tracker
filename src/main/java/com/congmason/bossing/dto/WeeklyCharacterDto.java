package com.congmason.bossing.dto;

import java.util.List;

public record WeeklyCharacterDto(
        Long id,
        String characterClass,
        String characterLevel,
        String characterName,
        Long characterMeso,
        List<WeeklyBossDto> weeklyBosses
) {
}
