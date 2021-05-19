package com.mikolajczyk.frontend.redudo.source.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mikolajczyk.frontend.redudo.domain.Book;
import com.mikolajczyk.frontend.redudo.dto.BookDto;
import com.mikolajczyk.frontend.redudo.source.engine.SourceEngine;
import com.mikolajczyk.frontend.redudo.source.mapper.SourceBookMapper;
import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SourceAccountService {

    private final SourceEngine engine;
    private final SourceBookMapper sourceBookMapper;

    public Long singIn() {
        log.info("Trying to sing in...");
        try {
            HttpResponse<String> httpResponse = engine.singInRequest();
            Long response = Long.parseLong(httpResponse.getBody());
            log.info("SUCCESS");
            return response;
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        log.info("FAILED");
        return 0L;
    }

    public Long deleteAccount() {
        try {
            HttpResponse<String> httpResponse = engine.deleteAccountRequest();
            Long response = Long.parseLong(httpResponse.getBody());
            log.info("SUCCESS");
            new Notification("Account deleted. Signing out...", 4000, Notification.Position.TOP_CENTER).open();
            return response;
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        log.info("FAILED");
        return 0L;
    }

    public List<Book> getBooksFromList(ListType listType) {
        try {
            HttpResponse<JsonNode> httpResponse = engine.getBooksFromListRequest(listType.getStringType(listType));
            return sourceBookMapper.mapToListBook(httpResponse);
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Long addBookList(BookDto bookDto, ListType listType) {
        if (bookDto.getDescription().length() > 251)
            bookDto.setDescription(bookDto.getDescription().substring(0, 250));
        try {
            HttpResponse<String> httpResponse = engine.addBookToListRequest(listType.getStringType(listType), bookDto);
            Long response = Long.parseLong(httpResponse.getBody());
            if (response > 0) {
                new Notification("Added!", 3000, Notification.Position.TOP_CENTER).open();
                return response;
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        new Notification("It's our fault. Try again later...", 3000, Notification.Position.TOP_CENTER).open();
        return 0L;
    }

    public Long removeFromList(String googleId, ListType listType) {
        try {
            HttpResponse<String> httpResponse = engine.removeBookFromListRequest(listType.getStringType(listType), googleId);
            Long response = Long.parseLong(httpResponse.getBody());
            if (response > 0) {
                new Notification("Success!", 3000, Notification.Position.TOP_CENTER).open();
                return response;
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        new Notification("It's our fault. Try again later...", 3000, Notification.Position.TOP_CENTER).open();
        return 0L;
    }
}