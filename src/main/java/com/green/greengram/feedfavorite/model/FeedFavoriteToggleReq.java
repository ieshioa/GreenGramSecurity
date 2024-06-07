package com.green.greengram.feedfavorite.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class FeedFavoriteToggleReq {
    private long feedId;
    private long userId;
}
