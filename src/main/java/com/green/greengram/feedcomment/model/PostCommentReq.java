package com.green.greengram.feedcomment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class PostCommentReq {
    @Schema(example = "1", description = "피드 pk")
    private long feedId;
    @Schema(example = "1", description = "유저 pk")
    private long userId;
    @Schema(example = "댓글댓글", description = "댓글 내용")
    private String comment;
    @JsonIgnore
    private long commentId;
}
