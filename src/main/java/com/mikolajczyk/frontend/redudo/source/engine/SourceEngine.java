package com.mikolajczyk.frontend.redudo.source.engine;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mikolajczyk.frontend.redudo.configuration.PropertiesConfig;
import com.mikolajczyk.frontend.redudo.dto.BookDto;
import com.mikolajczyk.frontend.redudo.dto.RatingDto;
import com.mikolajczyk.frontend.redudo.session.Session;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SourceEngine {

    private final Session session;
    private final PropertiesConfig propertiesConfig;
    private final String pathAccount = "/v1/account";
    private final String pathBooks = "/v1/books";

    public SourceEngine(Session session, PropertiesConfig propertiesConfig) {
        this.session = session;
        this.propertiesConfig = propertiesConfig;
        Unirest.setObjectMapper(new ObjectMapper() {
            com.fasterxml.jackson.databind.ObjectMapper mapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            @SneakyThrows
            public String writeValue(Object value) {
                return mapper.writeValueAsString(value);
            }

            @SneakyThrows
            public <T> T readValue(String value, Class<T> valueType) {
                return mapper.readValue(value, valueType);
            }
        });
    }

    public HttpResponse<JsonNode> getBooksRequest(Map<String, Object> values) throws UnirestException {
        String url = propertiesConfig.getApiRoot() + pathBooks;
        return Unirest.get(url)
                .queryString(values)
                .header("Authorization", session.getToken())
                .asJson();
    }

    public HttpResponse<String> singInRequest() throws UnirestException {
        String url = propertiesConfig.getApiRoot() + pathAccount;
        return Unirest.post(url)
                .header("Authorization", session.getToken())
                .asString();
    }

    public HttpResponse<String> deleteAccountRequest() throws UnirestException {
        String url = propertiesConfig.getApiRoot() + pathAccount;
        return Unirest.delete(url)
                .header("Authorization", session.getToken())
                .asString();
    }

    public HttpResponse<JsonNode> getBooksFromListRequest(String listName) throws UnirestException {
        String url = propertiesConfig.getApiRoot() + pathAccount + "/" + listName;
        return Unirest.get(url)
                .header("Authorization", session.getToken())
                .asJson();
    }

    public HttpResponse<String> addBookToListRequest(String listName, BookDto bookDto) throws UnirestException {
        String url = propertiesConfig.getApiRoot() + pathAccount + "/" + listName;
        return Unirest.put(url)
                .header("Authorization", session.getToken())
                .header("Content-Type", "application/json")
                .body(bookDto)
                .asString();
    }

    public HttpResponse<String> removeBookFromListRequest(String listName, String googleId) throws UnirestException {
        String url = propertiesConfig.getApiRoot() + pathAccount + "/" + listName + "/" + googleId;
        return Unirest.delete(url)
                .header("Authorization", session.getToken())
                .asString();
    }

    public HttpResponse<JsonNode> getRatingRequest(String googleId) throws UnirestException {
        String url = propertiesConfig.getApiRoot() + pathBooks + "/" + googleId;
        return Unirest.get(url)
                .header("Authorization", session.getToken())
                .asJson();
    }

    public HttpResponse<String> addRatingRequest(String googleId, RatingDto ratingDto) throws UnirestException {
        String url = propertiesConfig.getApiRoot() +  pathBooks +  "/" + googleId;
        return Unirest.post(url)
                .header("Authorization", session.getToken())
                .header("Content-Type", "application/json")
                .body(ratingDto)
                .asString();
    }
}
