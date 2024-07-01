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
        // img, css, js, favicon 등을 요청할 때와 프론트가 헤더에 엑세스토큰을 담지 않았을 때 (프론트의 실수 혹은 비로그인) 엑세스토큰이 없음
        // null이 넘어오지 않으면 잘된거
        log.info("JwtAuthenticationFilter-Token : {}",token);

        if(token != null && jwtTokenProvider.isValidateToken(token)) { // null이 아니고 토큰이 살아있다면
            Authentication auth = jwtTokenProvider.getAuthentication(token); // SecurityContextHolder의 Context 의 담기 위한 Authentication 객체 생성
            /*
            토큰으로부터 마이유저를 얻고 > 마이유저디테일즈에 담고 > 유저네임패스워드어센티케이션토큰에 담아서 리턴
            유저네임~~토큰이 어센티케이션의 자식 클래스
             */
            if(auth != null) {
                // Authentication 객체 주소값을 담으면 인증되었다고 인식
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request, response);
        /*
            다음 필터로 넘긴다
            만약 로그인이 필요한 엔드포인트 (url) 인데 로그인이 되어있지 않으면
            JWtAuthenticationEntryPoint 에 의해서 401 에러가 리턴

            만약 권한이 필요한 엔드포인트인데 권한이 없으면
            JwtAuthenticationAccessDeniedHandler에 의해 403 ㅇ에러가 리턴

            엔드포인트 세팅은 시큐리티컨피규레이션의 시큐리티필터체인 메소드에서 한다.
         */
    }
}
