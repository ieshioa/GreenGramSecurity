package com.green.greengram.feedcomment.model;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;
import java.beans.ConstructorProperties;

@Getter
@Setter
public class DelCommentReq {
    @Parameter(name = "signed_user_id")
    private long signedUserId;
    @Parameter(name = "feed_comment_id")
    private long feedCommentId;

    @ConstructorProperties({"signed_user_id","feed_comment_id"})
    public DelCommentReq(long signedUserId, long feedCommentId) {
        this.signedUserId = signedUserId;
        this.feedCommentId = feedCommentId;
    }

}


