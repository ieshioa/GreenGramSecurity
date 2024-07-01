package com.green.greengram.security;

import com.green.greengram.security.jwt.JwtAuthenticationAccessDeniedHandler;
import com.green.greengram.security.jwt.JwtAuthenticationEntryPoint;
import com.green.greengram.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration // 이걸 붙이면 싱글톤이 됨
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)  throws  Exception{
        /*
            @Bean
            - 메소드 타입의 빈등록 (파라미터, 리턴타입 중요)
            - @Configuration 있든 없든 동작함
            - 파라미터는 빈등록 할 때 필요한 객체
            - 파라미터 없이 내가 직접 new 객체화해서 리턴으로 빈등록 가능
            - 스프링컨테이너가 무조건 이 메소드를 호출함
            - 파라미터에는 스프링이 부를 수 있는 것만 써야함 아니면 에러
            - HttpSecurity : 라이브러리에 추가해서 바로 쓸 수 있음

            메소드 빈등록으로 주로 쓰는 케이스는 (현재 기준으로 설명하면)
            Security와 관련된 빈등록을 여러개 하고싶을 때 메소드 형식으로 빈등록하면
            한곳에 모을 수가 있으니 좋다.


         */

        // 람다식 2차프로젝트 할때 배울거임 일단 따라적기
        // 자바에서 제공해주는 대표적인 interface 3개 (람다식을 사용할 수 있는)
        new Customizer<HttpSecurity>() {
            @Override
            public void customize(HttpSecurity httpSecurity) {

            }
        };
        new Supplier<HttpSecurity>() {
            @Override
            public HttpSecurity get() {
                return null;
            }
        };
        new Function<Integer, Integer>() {

            @Override
            public Integer apply(Integer integer) {
                return null;
            }
        };
        // 파라미터는 있고 리턴타입은 없는 (Customizer) -> 소비만
        // 파라미터는 없고 리턴타입만 있는 (Supplier) -> 공급만
        // 둘다 있는  (Function)    -> 동시에 사용과 공급


        // 람다식 쓸 때 중괄호
        // 안에 내용이 한줄이면 중괄호, 세미콜론 생략 가능
        // 여러문장이면 써야함


        // 리턴 주소 빈등록
        return httpSecurity.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ) // 시큐리티에서 세션 사용을 하지 않음을 세팅 (세션 자체를 끌 순 없음(사용만 안할 뿐 살아는 있다), 시큐리티에서만 끔)
            .httpBasic(http -> http.disable()) //  form 로그인 방식을 사용하지 않음을 세팅
                                                // SSR = 서버사이드 렌더링 CSR = 클라이언트 사이드 렌더링
                                                // 주소를 입력했을 떄 보이는 화면 - html 문서
                                                // SSR = 서버측에서 렌더링을 한다(화면을 그린다)
                                                // CSR = 주소를 입력하면 그대로 요청이 되는게 아니고 서버에 있는 index.html(모바일로 따지면 앱 설치) 파일을 다운로드 한다 , 나한테 요청을 보낸 고객의 cpu를 씀
                                                // 그다음에 주소로 라우팅돼서 이동함 (이런걸 SPA(화면이 하나다)라고 부름)
                                                // 서버사이드 렌더링(SSR) 하지 않는다 즉 html 화면을 백엔드가 만들지 않는다
                                                // 백엔드에서 화면을 만들지 않더라도 위 세팅을 끄지 않아도 괜찮음. 사용하지 않는 걸 끔으로써 리소스를 확보하기 위해 사용
                                                // 시큐리티에서 제공해주는 로그인 화면을 사용하지 않겠다
                .formLogin(form -> form.disable()) // form 로그인 방식을 사용하지 않음을 세팅 (안이쁘니까!!)
                .csrf(csrf -> csrf.disable()) // CSRF (CORS랑 많이 헷갈려 함)
                .authorizeHttpRequests(auth ->     // 로그인해야하는가? 사용자 권한 설정하는 곳
                        auth.requestMatchers(
                                            // 회원가입, 로그인
                                        "/api/user/sign-up"
                                        ,"/api/user/sign-in"
                                        ,"/api/user/access-token"
                                            // 스웨거
                                        , "/swagger"
                                        , "/swagger-ui/**"
                                        , "v3/api-docs/**"
                                            // 사진
                                        , "/pic/**" // "/pic/*" 하면 1차까지만 허용됨 ex. /pic/ab.jpg 가능 /pic/aa/abc.jpg 불가능
                                        ,"/fimg/**"
                                            // 프론트에서 작업한 화면
                                        , "/"
                                        , "/index.html"
                                        , "/css/**"
                                        , "/js/**"
                                        , "/static/**"
                                        // 프론트에서 사용하는 라우터 주소를 허용해줘야한다 (안해주면 새로고침 했을 떄 안됨)
                                        , "/sign-in"
                                        , "/sign-up"
                                        , "/profile/*"
                                        , "/feed"   // /feed 로 요청이 들어옴 -> 시큐리티에서 허용한거면 통과 -> 핸들러 -> /feed 연결된 api가 없음 -> static을 뒤짐

                                        // actuator
                                ,"/actuator"
                                , "/actuator/*"
                                ).permitAll()
                        .anyRequest().authenticated() // 나머지 다른 요청들은 인증이 필요함 (근데 스웨거는 주소가 다르니까 이렇게 해두면 다 막힘)
// 사인업과 사인인은 로그인 안해도 쓸 수 있고 나머지는 로그인 해야만 쓸 수 있게 설정
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                        .accessDeniedHandler(new JwtAuthenticationAccessDeniedHandler())
                )
            .build();
        // 람다식 안쓰고 리턴하면
//        return httpSecurity.sessionManagement(new Customizer<SessionManagementConfigurer<HttpSecurity>>() {
//            @Override                               // Customizer : 람다식으로 바꿀 수 있는 인터페이스 : @FunctionalInterface 어노테이션이 붙어있음 , FunctionalInterface 특징 : 추상 메소드가 한개만 있음
//            public void customize(SessionManagementConfigurer<HttpSecurity> session) {
//                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//            }
//        }).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}


