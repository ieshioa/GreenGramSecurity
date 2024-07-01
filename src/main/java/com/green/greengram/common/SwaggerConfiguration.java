package com.green.greengram.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@OpenAPIDefinition(
        info = @Info(
                title = "그린그램",
                description = "그린그램연습",
                version = "v3"
        ),
        security = @SecurityRequirement(name = "authorization")  //EndPoint 마다 자물쇠 아이콘 생성 (로그인 가능) 각각에 로그인 기능 넣음
)
@SecurityScheme(        // 얘는 전체
        type = SecuritySchemeType.HTTP
        , name = "authorization"
        , in = SecuritySchemeIn.HEADER
        , bearerFormat = "JWT"
        , scheme = "Bearer"
)
public class SwaggerConfiguration {

}
// sign-in > response 의 accessToken 복사 > 자물쇠에 붙여넣기

// 로그인을 하면 제일먼저 jwt 필터로 감 (addFilterBefore 설정해줘서 시큐리티 컨피규레이션에서)
// 필터 거칠떄마다 스프링 컨테이너가 다음 필터로 이동해줌
// JWT어센티케이션필터 :로그인이 되었으면 다음필터로 넘김
// SecurityContextHolder에 데이터가 담겨있지 않다면 비로그인 상태임
// api/user/sign-in 은 permitAll 임 > 담겨있지 않아도 문제는 없다
// access 토큰 만들어서 쭉 진행하고 리스폰스 해줌
// 프론트가 이제 이걸 저장함
// 그다음부터는 백엔드로 오는 모든 요청의 헤더에 Authorization(대소문자 상관없음) 이라는 키값으로 Bearer 띄우고 access 붙여서 올거임

// 헤더에 키값이 저장 된 채로 센드 보냄
// jwt 필터에서 토큰이 널이 아님
// 만료가 됐는지 벨리데이트 체크 (만료가 됐으면 SecurityContextHolder에 데이터를 넣지 못한다 )
// 암호화 된 토큰에 들어있는 MyUser 를 담고있는 UserDetail를 담고있는 userAuthentication(?) 객체를 뺴냄
// p에는 사인드 유저 값이 아직 들어있지 ㅇ ㅏㄶ음
// 서비스에서 authenticationFacade 를 이용해 마이유저까지 끄집어내서 pk를 리턴함

// 만료된 토큰 = 401 unautorized 에러