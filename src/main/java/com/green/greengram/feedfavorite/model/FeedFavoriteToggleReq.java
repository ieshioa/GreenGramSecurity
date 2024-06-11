package com.green.greengram.feedfavorite.model;

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
    private long userId;

    @ConstructorProperties({"feed_id","user_id"})
    public FeedFavoriteToggleReq (long feedId, long userId){
        this.feedId = feedId;
        this.userId = userId;
    }
}
