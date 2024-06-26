package com.green.greengram.feedfavorite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.*;
import org.springframework.web.bind.annotation.BindParam;

import java.beans.ConstructorProperties;

@Getter
@Setter
@EqualsAndHashCode
public class FeedFavoriteToggleReq {
    @Parameter(name = "feed_id")
    private long feedId;
    @Parameter(name = "user_id")
    @JsonIgnore
    private long userId;

    @ConstructorProperties("feed_id")
    public FeedFavoriteToggleReq (long feedId){
        this.feedId = feedId;
    }
}
