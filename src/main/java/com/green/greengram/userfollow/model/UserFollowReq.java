package com.green.greengram.userfollow.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.BindParam;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class UserFollowReq {
    @Parameter(name = "from_user_id")
    @Schema(example = "45", description = "팔로워 유저 pk")
    private long fromUserId;
    @Parameter(name = "to_user_id")
    @Schema(example = "46", description = "팔로잉 유저 pk")
    private long toUserId;

    public UserFollowReq(@BindParam("from_user_id") long fromUserId, @BindParam("to_user_id") long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
}
/*
    @JsonProperty("from_user_id")
    : 둘다 됨, url 안바뀜
    ============================================================
    post: 4개 뜸
    delete: O
    @Parameter(name = "from_user_id")
    @Schema(example = "45", description = "팔로워 유저 pk")
    private long fromUserId;
    @Parameter(name = "to_user_id")
    @Schema(example = "46", description = "팔로잉 유저 pk")
    private long toUserId;

    @ConstructorProperties({"from_user_id","to_user_id"})
    public UserFollowReq(long fromUserId, long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
    ============================================================
    post: 0으로 들어감
    delete: O
    @Schema(name = "from_user_id", example = "45", description = "팔로워 유저 pk")
    private long fromUserId;
    @Schema(name = "to_user_id", example = "46", description = "팔로잉 유저 pk")
    private long toUserId;

    @ConstructorProperties({"from_user_id","to_user_id"})
    public UserFollowReq(long fromUserId, long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
    ============================================================
    post: o
    delete: o
    @Parameter(name = "from_user_id")
    @Schema(example = "45", description = "팔로워 유저 pk")
    private long fromUserId;
    @Parameter(name = "to_user_id")
    @Schema(example = "46", description = "팔로잉 유저 pk")
    private long toUserId;

    public UserFollowReq(@BindParam("from_user_id") long fromUserId, @BindParam("to_user_id") long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
    ============================================================
    post: 0으로 들어감
    delete: O
    @Schema(name = "from_user_id", example = "45", description = "팔로워 유저 pk")
    private long fromUserId;
    @Schema(name = "to_user_id", example = "46", description = "팔로잉 유저 pk")
    private long toUserId;

    public UserFollowReq(@BindParam("from_user_id") long fromUserId, @BindParam("to_user_id") long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
    ============================================================
 */

