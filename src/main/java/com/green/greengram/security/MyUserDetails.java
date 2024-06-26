package com.green.greengram.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
// @JsonIgnoreProperties(ignoreUnknown = true)
            // 현재는 이게 필요 없음, 동작이 제대로 안돼서 MyUser로 따로 뺌
            // json에 더 많은 속성이 있는데 MyUser에 없는 멤버필드는 무시하고 객체를 생성할 수 있는 것
/*
            예를들어
            {
                "userId" : 10,
                "role" : "ROLE_USER",
                "addr" : "대구시"
            }
            위 json 을 MyUser로 파싱할 때 addr 값을 담을 수 없기 떄문에 에러가 발생된다.
            위의 애노테이션을 작성하면 addr은 무시하고 객체를 생성한다.
 */

public class MyUserDetails implements UserDetails {

    private MyUser myUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> list = new ArrayList<>();
//        list.add(new SimpleGrantedAuthority(role));
//        return list;
        return Collections.singletonList(new SimpleGrantedAuthority(myUser.getRole()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
