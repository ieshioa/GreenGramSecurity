package com.green.greengram.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.AppProperties;
import com.green.greengram.security.MyUser;
import com.green.greengram.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProviderV2 {
    private final ObjectMapper om;
    private final AppProperties appProperties;
    private final SecretKey secretKey;

    public JwtTokenProviderV2(ObjectMapper om, AppProperties appProperties) {
        this.om = om;
        this.appProperties = appProperties;
        System.out.println("============================" + appProperties.getJwt().getSecret());
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(appProperties.getJwt().getSecret()));
        // 암호화, 복호화할 때 사용하는 키를 생성하는 부분, 디코드 메소드에 보내는 아규먼트값은 우리가 설정한 문자열
    }

    public String generateAccessToken(MyUser myUser) {
        return generateToken(myUser, appProperties.getJwt().getAccessTokenExpiry());
        // 야믈파일에서 app.jwt.access-token-expiry 내용을 가져오는 부분
    }

    public String generateRefreshToken(MyUser myUser) {
        return generateToken(myUser, appProperties.getJwt().getRefreshTokenExpiry());
        // 야믈파일에서 app.jwt.refresh-token-expiry 내용을 가져오는 부분
    }



    private String generateToken(MyUser myUser, long tokenValidMilliSecond) {
        return Jwts.builder()
                .claims(createClaims(myUser))  // claims는 payload(내용)에 저장하고 싶은 내용을 저장
                .issuedAt(new Date(System.currentTimeMillis())) // JWT 생성 일시
                .expiration(new Date(System.currentTimeMillis() + tokenValidMilliSecond))   // JWT 만료 일시
                .signWith(secretKey, Jwts.SIG.HS512)    // JWT 암호화 선택, 위변조 검증 (시그니처 서명)
                .compact(); // 토큰 생성
        // . . . . 해서 리턴이 되는 기법
        // .메소드호출.메소드호출.메소드호출 >> 체이닝기법
        // 원리는 메소드 호출 시 자신의 주소값 리턴을 하기 때문
    }

    public Claims createClaims(MyUser myUser) {
        try {
            String json = om.writeValueAsString(myUser); // 객체 to json
            return Jwts.claims().add("signedUser", json).build();   // claims에 json 저장
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Claims getAllClaims(String token) {  // payload를 빼냄
        return Jwts
                .parser()
                .verifyWith(secretKey)  // 똑같은 키로 복호화를 하겠다
                .build()
                .parseSignedClaims(token)
                .getPayload(); // JWT 안에 들어있는 payload(claims)를 리턴
    }

    public UserDetails getUserDetailsFromToken(String token) {
        try {       // jwt, json 둘다 문자열인데 jwt는 인증코드 json은 데이터
            Claims claims = getAllClaims(token); // jwt에 저장되어있는 claims를 얻어온다
            String json = (String)claims.get("signedUser"); // claims에 저장되어있는 값을 얻어온다 (json)
            MyUser myUser = om.readValue(json, MyUser.class); // json > 객체로 변환 (그것이 UserDetails 인데 정확히는 MyUserDetails이다)
            MyUserDetails myUserDetails = new MyUserDetails();
            myUserDetails.setMyUser(myUser);
            return myUserDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // SpringContextHolder에 저장할 자료를 세팅
    // (나중에 service 단에서 빼서 쓸 값, 로그인 처리, 인가처리를 위해)
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = getUserDetailsFromToken(token); // MyUserDetails 객체 주소값
        return userDetails == null ? null
                : new UsernamePasswordAuthenticationToken(userDetails
                , null
                , userDetails.getAuthorities()
        );
        // UserNamePasswordAuthenticationToken 객체를 SpringContextHolder에 저장하는 자체만으로도 인증 완료
        // userDetails 는 로그인한 사용자의 정보를 controller or Service 단에서 빼서 사용하기 위함
        // userDetails.getAuthorities()는 인가(권한) 부분 세팅, 현재 권한은 하나만 가질 수 있음, 다수 권한 가능
    }
    public boolean isValidateToken(String token) {
        try {
            // 원래는 만료시간이 안지났으면 리턴 false, 지났으면 true
            // 앞에 ! 해서 반전
            // 지남 = false, 안지남 = true
            return !getAllClaims(token).getExpiration().before(new Date()); // new Date() 여기에 값 안넣어주면 현재 시간을 기준으로 만듦
        } catch (Exception e) {
            return false;
        }
    }
    // 요청이 오면 jwt를 열어보는 부분 > 헤더에서 토큰(jwt)을 꺼낸다.
    public String resolveToken(HttpServletRequest req) {
        // 프론트가 백엔드에 요청을 보낼 때 (로그인을 했다면) 항상 jwt를 보낼건데
        // 헤더에 약속한 key에 저장해서 보낸다.
        String auth = req.getHeader(appProperties.getJwt().getHeaderSchemaName());
       // String auth = req.Header("authorization");  이렇게 작성한 것과 값음 key 값은 변경 가능
        if(auth == null) {
            return null;
        }
        // if 를 넘어왔다면 프론트가 헤더에 authorization 키에 데이터를 담아서 보내왔다는 뜻
        // auth에는 Bearer JWT 문자열이 있을 것이다.
        // 문자열이 Bearer 로 시작하는지 체크
        if(!auth.startsWith(appProperties.getJwt().getTokenType())) { // Bearer 로 시작하지 않는다면 리턴널 (appProperties.getJwt().getTokenType() : "Bearer")
            return null;
        }
        // Bearer 지우고 JWT토큰만 뽑아옴
        return auth.substring(appProperties.getJwt().getTokenType().length()).trim();  // trim() : 빈칸 지움 (양쪽에 있는 빈칸만. 중간은 안지워줌)
    }
}
