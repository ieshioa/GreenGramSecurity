package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class UserInfoGetReq {
    @Schema(name = "signed_user_id" , example = "45", description = "로그인 한 유저 pk")
    private long signedUserId;
    @Schema(name = "profile_user_id" , example = "46", description = "방문한 프로필 유저 pk")
    private long profileUserId;

    @ConstructorProperties({"signed_user_id","profile_user_id"})
    public UserInfoGetReq (long signedUserId, long profileUserId) {
        this.signedUserId = signedUserId;
        this.profileUserId = profileUserId;
    }
}
