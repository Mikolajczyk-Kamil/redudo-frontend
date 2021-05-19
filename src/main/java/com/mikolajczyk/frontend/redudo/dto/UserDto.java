package com.mikolajczyk.frontend.redudo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {

    private long id;
    private String googleId;
    private String name;
    private String lastname;
    private String email;
    private String pictureUrl;

    public UserDto(long id, String name, String lastname, String email, String pictureUrl) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.pictureUrl = pictureUrl;
    }
}
