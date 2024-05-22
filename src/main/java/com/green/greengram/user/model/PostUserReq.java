package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUserReq {
    @JsonIgnore
    private long userId;
    @Schema(example = "mj", description = "로그인 아이디")
    private String uid;
    @Schema(example = "1234", description = "로그인 비밀번호")
    private String upw;
    @Schema(example = "김민지", description = "유저 이름")
    private String nm;
    @JsonIgnore
    private String pic;
}
