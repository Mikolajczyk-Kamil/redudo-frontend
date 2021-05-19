package com.mikolajczyk.frontend.redudo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {

    private long id;
    private String googleId;
    private String name;
    private String lastname;
    private String email;
    private String pictureUrl;

    public User(String googleId, String name, String lastname, String email, String pictureUrl) {
        this.googleId = googleId;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.pictureUrl = pictureUrl;
    }
}
