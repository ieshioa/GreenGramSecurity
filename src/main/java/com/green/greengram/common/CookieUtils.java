package com.green.greengram.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CookieUtils {
    private final ObjectMapper om;

    public <T> T getData(T type, HttpServletRequest req, String name) {
        try {

        } catch (Exception e) {

        }
        return null;
    }

// jwt는 헤더에 백엔드에서 세팅을 못함 프론트가 해줘야함 쿠키는 백에서도 할 수 있음
    // 요청 헤더에 내가 원하는  쿠키가 있는지 찾는 메소드
    public Cookie getCookie(HttpServletRequest req, String  name) {
        Cookie[] cookies = req.getCookies(); // 요청에서 모든 쿠키정보를 받는다.
        if(cookies != null && cookies.length > 0) { // 쿠키 정보가 있고 하나 이상이라면
            for (Cookie cookie : cookies) {
                if(name.equals(cookie.getName())) {  // 찾고자 하는 key가 있는지 확인
                    return cookie;  // 똑같은 이름을 찾자마자 바로 종료시켜버림
                }
            }
        }
        return null;
    }

    public void setCookie (HttpServletResponse res, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");        // Root URL (백엔드 모든 요청에 해당하게 세팅)
        cookie.setHttpOnly(true);   // 보안 쿠키
        cookie.setMaxAge(maxAge);   // 만료 시간
        res.addCookie(cookie);      // res에 담아줌
    }

    public void deleteCookie(HttpServletResponse res, String name) {
        setCookie(res, name, null, 0); // maxAge 를 0으로 세팅하면 기존꺼가 지워짐

    }
}
