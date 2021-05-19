package com.mikolajczyk.frontend.redudo.mapper;

import com.mikolajczyk.frontend.redudo.domain.Book;
import com.mikolajczyk.frontend.redudo.dto.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public BookDto mapToBookDto(Book book) {
        return new BookDto(
                book.getGoogleId(),
                book.getIsbn(),
                book.getIndustryId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getPublisher(),
                book.getPublished(),
                book.getCategories(),
                book.getCoverUrl(),
                book.getPriceEbook(),
                book.getEbookUrl()
        );
    }

    public Book mapToBook(BookDto bookDto) {
        return new Book(
                bookDto.getGoogleId(),
                bookDto.getIsbn(),
                bookDto.getIndustryId(),
                bookDto.getTitle(),
                bookDto.getAuthor(),
                bookDto.getDescription(),
                bookDto.getPublisher(),
                bookDto.getPublished(),
                bookDto.getCategories(),
                bookDto.getCoverUrl(),
                bookDto.getPriceEbook(),
                bookDto.getEbookUrl()
        );
    }
}
