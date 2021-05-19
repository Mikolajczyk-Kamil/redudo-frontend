package com.mikolajczyk.frontend.redudo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Book {

    private String googleId;
    private String isbn;
    private String industryId;
    private String title;
    private String author;
    private String description;
    private String publisher;
    private String published;
    private String categories;
    private String coverUrl;
    private String priceEbook;
    private String ebookUrl;
}
