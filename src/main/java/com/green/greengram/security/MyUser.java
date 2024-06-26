package com.green.greengram.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor  // JSON > Object 할 때 필요
@Builder
public class MyUser {
    // @JsonIgnore 다 붙이기 힘드니까 만듦

    private long userId;    // 로그인 한 사용자의 PK
    private String role;    // 사용자 권한, ROLE_권한이름

}
