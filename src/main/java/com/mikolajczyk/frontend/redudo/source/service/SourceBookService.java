package com.mikolajczyk.frontend.redudo.source.service;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mikolajczyk.frontend.redudo.domain.Book;
import com.mikolajczyk.frontend.redudo.domain.Rating;
import com.mikolajczyk.frontend.redudo.dto.RatingDto;
import com.mikolajczyk.frontend.redudo.source.engine.SourceEngine;
import com.mikolajczyk.frontend.redudo.source.mapper.SourceBookMapper;
import com.mikolajczyk.frontend.redudo.source.mapper.SourceRatingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SourceBookService {

    private final SourceEngine engine;
    private final SourceBookMapper sourceBookMapper;
    private final SourceRatingMapper sourceRatingMapper;

    public List<Book> getBooksByQ(String query, boolean external) throws UnirestException {
        log.info("Getting books by query(Q: " + query + ")...");
        Map<String, Object> values = new HashMap<>();
        values.put("q", query);
        values.put("external", external);
        return sourceBookMapper.mapToListBook(engine.getBooksRequest(values));
    }

    public Long addRating(Book book, RatingDto ratingDto) throws UnirestException {
        log.info("Adding rating to book(GOOGLE_ID: " + book.getGoogleId() + ")");
        HttpResponse<String> response = engine.addRatingRequest(book.getGoogleId(), ratingDto);
        return Long.parseLong(response.getBody());
    }

    public List<Rating> getRatings(Book book) throws UnirestException {
        log.info("Getting book(GOOGLE_ID: " + book.getGoogleId() + ") rating...");
        HttpResponse<JsonNode> response = engine.getRatingRequest(book.getGoogleId());
        System.out.println(response.getBody().toString());
        return sourceRatingMapper.mapToListRating(response);
    }
}
