package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignInReq {
    @Schema(example = "mj", description = "아이디")
    private String uid;
    @Schema(example = "1234", description = "비밀번호")
    private String upw;
}
