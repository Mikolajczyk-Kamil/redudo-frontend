package com.mikolajczyk.frontend.redudo.source.mapper;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mikolajczyk.frontend.redudo.domain.Rating;
import com.mikolajczyk.frontend.redudo.dto.UserDto;
import com.mikolajczyk.frontend.redudo.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SourceRatingMapper {

    private final SourceUserMapper sourceUserMapper;
    private final UserMapper userMapper;

    public List<Rating> mapToListRating(HttpResponse<JsonNode> response) {
        if (response.getBody().getArray().length() > 0) {
            JSONArray jsonArray = response.getBody().getArray();
            List<Rating> ratingList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++)
                ratingList.add(mapToRating(jsonArray.getJSONObject(i)));
            return ratingList;
        }
        return new ArrayList<>();
    }

    public Rating mapToRating(JSONObject jsonBook) {
        long id;
        UserDto userDto;
        int value;
        String comment;
        id = Long.parseLong(jsonBook.get("id").toString());
        userDto = sourceUserMapper.mapToUserDto(jsonBook.getJSONObject("userDto"));
        value = Integer.parseInt(jsonBook.get("value").toString());
        comment = jsonBook.get("comment").toString();

        return new Rating(id, userMapper.mapToUser(userDto), null, value, comment);
    }
}
