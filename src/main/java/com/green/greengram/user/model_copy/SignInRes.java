package com.green.greengram.user.model_copy;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInRes {
    @Schema(example = "1", description = "유저PK")
    private long userId;
    @Schema(description = "유저 이름")
    private String nm;
    @Schema(description = "유저 프로필 이미지")
    private String pic;

}
