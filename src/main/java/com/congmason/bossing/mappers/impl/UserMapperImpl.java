package com.congmason.bossing.mappers.impl;

import com.congmason.bossing.dto.UserDto;
import com.congmason.bossing.entity.User;
import com.congmason.bossing.mappers.UserMapper;
import com.congmason.bossing.mappers.WeeklyCharacterMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserMapperImpl implements UserMapper {

    private final WeeklyCharacterMapper characterMapper;

    public UserMapperImpl(WeeklyCharacterMapper weeklyCharacterMapper) {
        this.characterMapper = weeklyCharacterMapper;
    }

    @Override
    public User fromDto(UserDto userDto) {
        return new User(
                userDto.id(),
                userDto.username(),
                userDto.email(),
                userDto.password(),
                Optional.ofNullable(userDto.weeklyCharacters())
                        .map(weeklyCharacters -> weeklyCharacters.stream()
                                .map(characterMapper::fromDto)
                                .toList()).orElse(null)
        );
    }

    @Override
    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                Optional.ofNullable(user.getWeeklyCharacters())
                        .map(weeklyCharacters -> weeklyCharacters.stream()
                                .map(characterMapper::toDto)
                                .toList()).orElse(null)
        );
    }
}
