package com.congmason.bossing.mappers;

import com.congmason.bossing.dto.UserDto;
import com.congmason.bossing.entity.User;

public interface UserMapper {

    User fromDto(UserDto userDto);
    UserDto toDto(User user);

}
