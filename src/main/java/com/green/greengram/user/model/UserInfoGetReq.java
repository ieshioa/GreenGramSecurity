package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.BindParam;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class UserInfoGetReq {
    @Schema(name = "signed_user_id" , example = "45", description = "로그인 한 유저 pk")
    @JsonIgnore
    private long signedUserId;
    @Schema(example = "46", description = "방문한 프로필 유저 pk")
    @Parameter(name = "profile_user_id")
    private long profileUserId;


    public UserInfoGetReq (@BindParam("profile_user_id") long profileUserId) {
        this.profileUserId = profileUserId;
    }

}
