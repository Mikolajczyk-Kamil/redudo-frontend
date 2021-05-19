package com.mikolajczyk.frontend.redudo.mapper;

import com.mikolajczyk.frontend.redudo.domain.Rating;
import com.mikolajczyk.frontend.redudo.dto.RatingDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class RatingMapper {

    private final BookMapper bookMapper;

    public RatingDto mapToRatingDto(Rating rating) {
        return new RatingDto(
                null,
                bookMapper.mapToBookDto(rating.getBook()),
                rating.getValue(),
                rating.getComment()
        );
    }
}
