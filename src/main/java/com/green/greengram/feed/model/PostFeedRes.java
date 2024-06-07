package com.green.greengram.feed.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Builder
@Data
@EqualsAndHashCode
public class PostFeedRes {
    private long feedId;
    private List<String> pics;
}
