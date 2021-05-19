package com.mikolajczyk.frontend.redudo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Rating {

    private long id;
    private User user;
    private Book book;
    private int value;
    private String comment;

    public Rating(User user, Book book, int value, String comment) {
        this.user = user;
        this.book = book;
        this.value = value;
        this.comment = comment;
    }
}
