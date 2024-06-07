package com.green.greengram.feedcomment.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class GetCommentsRes {

    private long feedCommentId;
    private String comment;
    private String writerNm;
    private String writerPic;
    private long writerId;
}
