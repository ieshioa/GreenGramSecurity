package com.green.greengram.feedcomment.model_copy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FeedCommentPostReq {
    // 데이터 제공
    private long feedId;
    private long userId;
    private String comment;

    // 데이터 반환
    @JsonIgnore
    private long feedCommentId;
}
