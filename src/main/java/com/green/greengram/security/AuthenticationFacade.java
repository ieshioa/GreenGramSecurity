package com.green.greengram.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    /*
        로그인 한 사용자 정보를 가져오는 객체
        SecurityContextHolder > Context > Authentication 의 정보를 얻어옴
        여기에 저장이 되어있지 않으면 로그인이 되지 않은 것
     */
    public MyUser getLoginUser() {
        MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder
                                            .getContext()
                                            .getAuthentication()
                                            .getPrincipal();  // 우리가 집어넣었던 정보를 가져옴
        return myUserDetails == null ? null : myUserDetails.getMyUser();
    }

    public long getLoginUserId() {
        MyUser myUser = getLoginUser();
        return myUser == null ? 0 : myUser.getUserId();
    }
}
