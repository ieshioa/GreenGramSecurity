package com.green.greengram.feedcomment.model_copy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedCommentGetRes {
    private long feedCommentId;
    private String comment;
    private String createdAt;
    private String writerNm;
    private String writerPic;
    private long writerId;
}
