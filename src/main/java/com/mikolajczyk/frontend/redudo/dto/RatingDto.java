package com.mikolajczyk.frontend.redudo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDto {

    private long id;
    private UserDto userDto;
    private BookDto bookDto;
    private int value;
    private String comment;

    public RatingDto(UserDto userDto, BookDto bookDto, int value, String comment) {
        this.userDto = userDto;
        this.bookDto = bookDto;
        this.value = value;
        this.comment = comment;
    }
}
