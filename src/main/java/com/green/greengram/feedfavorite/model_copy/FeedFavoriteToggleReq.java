package com.green.greengram.feedfavorite.model_copy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedFavoriteToggleReq {
    private long feedId;
    private long userId;
}