package com.green.greengram.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
// @ConfigurationProperties : 야믈에 작성되어 있는 데이터를 객체화 시켜주는 애노테이션
@ConfigurationProperties(prefix = "app") // 야믈 파일의 app 을 의미
public class AppProperties {
    private final Jwt jwt = new Jwt();

    // class 명 jwt 는 야믈의 jwt 를 의미
    // 멤버필드 명은 야믈의 app/jwt/* 속성과 매칭
    // 야믈에서 - 는 멤버필드명 카멜케이스기법과 매칭
    @Getter
    @Setter
    public static class Jwt { // static 안넣으면 성능상의 이슈 발생
        private String secret;
        private String headerSchemaName;
        private String tokenType;
        private long accessTokenExpiry;
        private long refreshTokenExpiry;
        private int refreshTokenCookieMaxAge;

        public void setRefreshTokenExpiry(long refreshTokenExpiry) {
            this.refreshTokenExpiry = refreshTokenExpiry;
            this.refreshTokenCookieMaxAge = (int) (refreshTokenExpiry * 0.001);  // ms > s
        }
    }
}
