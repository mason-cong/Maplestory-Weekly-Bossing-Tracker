package com.congmason.bossing.dto;

import java.util.List;

public record UserDto(
        Long id,
        String email,
        String password,
        String username,
        List<WeeklyCharacterDto> weeklyCharacters
) {
}
