package com.congmason.bossing.dto;

public record WeeklyBossDto(
        Long id,
        String bossName,
        int partySize,
        Long crystalValue
) {
}
