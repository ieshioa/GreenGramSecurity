package com.green.greengram.userfollow.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode  // 주소값이 달라도 안에 들어있는 값이 같으면 비교했을때 같다고 해줌
                    // 이것도 오버라이딩임
// Equals 와 HashCode 메소드를 오버라이딩함
// 같은값을 가지고 있으면 같은 해시코드를 만들서 비교했을 때 같게 해줌
@AllArgsConstructor
@ToString
public class UserFollowEntity {
    private long fromUserId;
    private long toUserId;
    private String createdAt;
}
