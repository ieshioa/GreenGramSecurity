package com.green.greengram.feedfavorite.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class FeedFavoriteEntity {
    private long feedId;
    private long userId;
    private String createdAt;
}
