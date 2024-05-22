package com.green.greengram.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostFeedReq {
    @JsonIgnore
    private long feedId;
    @Schema(example = "1", description = "작성자 pk")
    private long userId;
    @Schema(example = "안녕하세요.", description = "게시글 내용")
    private String contents;
    @Schema(example = "대구시", description = "위치")
    private String location;
}
