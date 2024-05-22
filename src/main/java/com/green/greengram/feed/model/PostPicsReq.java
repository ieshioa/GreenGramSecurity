package com.green.greengram.feed.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class PostPicsReq {
    private long feedId;
    private List<String> pics;
}
