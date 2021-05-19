package com.mikolajczyk.frontend.redudo.source.mapper;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mikolajczyk.frontend.redudo.domain.Book;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SourceBookMapper {

    public List<Book> mapToListBook(HttpResponse<JsonNode> httpResponse) {
        if (httpResponse.getStatus() == 200) {
            JSONArray jsonArray = httpResponse.getBody().getArray();
            if (jsonArray.length() > 1) {
                List<Book> bookList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++)
                    bookList.add(mapToBook(jsonArray.getJSONObject(i)));
                return bookList;
            }
        }
        return List.of();
}

    public Book mapToBook(JSONObject jsonBook) {
        String googleId;
        String isbn = "";
        String industryId = "";
        String title;
        String author = "";
        String description = "";
        String publisher = "";
        String published = "";
        String categories = "";
        String coverUrl = "";
        String priceEbook = "";
        String ebookUrl = "";
        googleId = jsonBook.get("googleId").toString();
        title = jsonBook.get("title").toString();
        if (jsonBook.has("isbn"))
            isbn = jsonBook.get("isbn").toString();
        if (jsonBook.has("industryId"))
            industryId = jsonBook.get("industryId").toString();
        if (jsonBook.has("author"))
            author = jsonBook.get("author").toString();
        if (jsonBook.has("description"))
            description = jsonBook.get("description").toString();
        if (jsonBook.has("publisher"))
            publisher = jsonBook.get("publisher").toString();
        if (jsonBook.has("published"))
            published = jsonBook.get("published").toString();
        if (jsonBook.has("categories"))
            categories = jsonBook.get("categories").toString();
        if (jsonBook.has("coverUrl"))
            coverUrl = jsonBook.get("coverUrl").toString();
        if (jsonBook.has("priceEbook"))
            priceEbook = jsonBook.get("priceEbook").toString();
        if (jsonBook.has("ebookUrl"))
            ebookUrl = jsonBook.get("ebookUrl").toString();
        return new Book(
                googleId,
                isbn,
                industryId,
                title,
                author,
                description,
                publisher,
                published,
                categories,
                coverUrl,
                priceEbook,
                ebookUrl
        );
    }
}
