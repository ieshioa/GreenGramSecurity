package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignInRes {
    @Schema(example = "1", description = "유저 pk")
    private long userId;
    @Schema(example = "김민지", description = "유저 이름")
    private String nm;
    @Schema(example = "abcd.jpg", description = "유저 프로필 이미지")
    private String pic;

    private String accessToken;
}
