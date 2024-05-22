package com.green.greengram.feedcomment.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GetCommentsRes {

    private long feedCommentId;
    private String comment;
    private String writerNm;
    private String writerPic;
    private long writerId;
}
