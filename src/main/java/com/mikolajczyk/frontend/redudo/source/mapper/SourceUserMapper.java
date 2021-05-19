package com.mikolajczyk.frontend.redudo.source.mapper;

import com.mikolajczyk.frontend.redudo.dto.UserDto;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class SourceUserMapper {

    public UserDto mapToUserDto(JSONObject jsonBook) {
        Long id;
        String name;
        String lastname;
        String email;
        String pictureUrl;
        id = Long.parseLong(jsonBook.get("id").toString());
        name = jsonBook.get("name").toString();
        lastname = jsonBook.get("lastname").toString();
        email = jsonBook.get("email").toString();
        pictureUrl = jsonBook.get("pictureUrl").toString();
        return new UserDto(id, name, lastname, email, pictureUrl);
    }
}
