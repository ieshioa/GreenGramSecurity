package com.green.greengram.feed.model;

import com.green.greengram.feedcomment.model.GetCommentsRes;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class GetFeedRes {
    // feed
    private long feedId;
    private long writerId;
    private String contents;
    private String location;
    private String createdAt;
    private String updatedAt;
    // feed_pics
    private List<String> pics;
    //user
    private String writerNm;
    private String writerPic;
    //favorite
    private int isFav;
    // feed_comment
    private List<GetCommentsRes> comments;
    private int isMoreComment;
}
