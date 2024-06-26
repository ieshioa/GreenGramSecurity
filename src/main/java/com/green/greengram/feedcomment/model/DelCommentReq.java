package com.green.greengram.feedcomment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.beans.ConstructorProperties;

@Getter
@Setter
@EqualsAndHashCode
public class DelCommentReq {
    @Parameter(name = "signed_user_id")
    @JsonIgnore
    private long signedUserId;
    @Parameter(name = "feed_comment_id")
    private long feedCommentId;

    @ConstructorProperties("feed_comment_id")
    public DelCommentReq(long feedCommentId) {
        this.feedCommentId = feedCommentId;
    }

}


