package com.green.greengram.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor  // final 붙은것은 꼭 생성자를 만들겠다 > 주소값이 필요하기 때문에 빈등록이 필요
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter { // extends 했는데 빨간줄이 뜬다 ! > 100% 추상클래스임 (인터페이스는 원래 뜸)
    // OncePerRequestFilter : 한페이지에서 요청이 여러번 있으면 필터도 여러번 실행됨 > 딱 한번만 실행해달라 하는거임
    private final JwtTokenProviderV2 jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request); // header 의 authorization 키에 저장되어 있는 값을 리턴
                                                                    //  있으면(로그인했다) 문자열 (JWT) 없으면(로그인 안했다) null
        // null이 넘어오지 않으면 잘된거
        log.info("JwtAuthenticationFilter-Token : {}",token);

        if(token != null && jwtTokenProvider.isValidateToken(token)) { // null이 아니고 토큰이 살아있다면
            Authentication auth = jwtTokenProvider.getAuthentication(token); // SecurityContextHolder의 Context 의 담기 위한 Authentication 객체 생성
            if(auth != null) {
                // Authentication 객체 주소값을 담으면 인증되었다고 인식
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
    }
}
