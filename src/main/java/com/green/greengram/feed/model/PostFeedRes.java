package com.green.greengram.feed.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PostFeedRes {
    private long feedId;
    private List<String> pics;
}
