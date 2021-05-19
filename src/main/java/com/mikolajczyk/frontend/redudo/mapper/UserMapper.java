package com.mikolajczyk.frontend.redudo.mapper;

import com.mikolajczyk.frontend.redudo.domain.User;
import com.mikolajczyk.frontend.redudo.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapToUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getGoogleId(),
                userDto.getName(),
                userDto.getLastname(),
                userDto.getEmail(),
                userDto.getPictureUrl()
        );
    }
}
