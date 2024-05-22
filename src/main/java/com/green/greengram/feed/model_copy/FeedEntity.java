package com.green.greengram.feed.model_copy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedEntity {
    private long feedId;
    private long writerId;
    private String contents;
    private String location;
    private String createdAt;
    private String updatedAt;
}
