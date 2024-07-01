package com.green.greengram.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.AppProperties;
import com.green.greengram.security.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    /*
        V2와 다른점은 SecretKey 멤버필드의 final 유무
        ObjectMapper, AppProperties 를 생성자를 통해 DI 받고 있음
        SecretKey 생성자 호출 이후에 @PostConstruct 애노테이션을 가지고 있는 init 메소드르 통해 초기화 됨
        init 은 메소드이지 생성자가 아니기 떄문에 final을 초기화 할 수 없다.
        SecretKey 를 final로 세팅할 수 없음
        나머지는 똑같이 적어도 됨
     */
    private final ObjectMapper om;
    private final AppProperties appProperties;
//    private SecretKeySpec secretKeySpec;
    private SecretKey secretKey;

//    @PostConstruct  // 시큐리티 애노테이션, 생성자 호출 이후에 한번 실행되는 메소드
//    public void init() {
//        this.secretKey = new SecretKeySpec(appProperties.getJwt().getSecret().getBytes()
//        , SignatureAlgorithm.HS256.getJcaName());
//    }

    @PostConstruct  // 시큐리티 애노테이션, 생성자 호출 이후에 한번 실행되는 메소드
    public void init() {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(appProperties.getJwt().getSecret()));
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, appProperties.getJwt().getAccessTokenExpiry());
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails, appProperties.getJwt().getRefreshTokenExpiry());
    }



    private String generateToken(UserDetails userDetails, long tokenValidMilliSecond) {
        return Jwts.builder()
                .claims(createClaims(userDetails))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenValidMilliSecond))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public Claims createClaims(UserDetails userDetails) {
        try {
            String json = om.writeValueAsString(userDetails);
            return Jwts.claims().add("signedUser", json).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private UserDetails getUserDetailsFromToken(String token) {
        try {
            Claims claims = getAllClaims(token);
            String json = (String)claims.get("signedUser");
            return om.readValue(json, MyUserDetails.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
