package com.green.greengram;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration  // 이거 없어도 빈등록은 되는데 싱글톤을 보장하진 않음
public class CharEncodingConfiguration {
    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        return new CharacterEncodingFilter("UTF-8", true);
    }
}
