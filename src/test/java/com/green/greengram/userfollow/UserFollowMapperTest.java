package com.green.greengram.userfollow;

import com.green.greengram.userfollow.model.UserFollowEntity;
import com.green.greengram.userfollow.model.UserFollowReq;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("tdd")  // 기본적으로는 야믈에 있는 디폴트가  실행되는데 이거 달면 내용이 덮어쓰기가 됨
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class UserFollowMapperTest {

    @Autowired
    private UserFollowMapper mapper;

    @Test
    @DisplayName("팔로우 insert")
    void insFollow() {
        UserFollowReq p1 = new UserFollowReq(0,0);
        List<UserFollowEntity> list1 = mapper.selUserFollowListForTest(p1);
        // list1 : 기존에 있던 전체 팔로우 리스트

        UserFollowReq p2 = new UserFollowReq(10,20);
        int affectedRow = mapper.insFollow(p2);
        Assertions.assertEquals(1, affectedRow); // 제대로 insert 되었는가?
        List<UserFollowEntity> list2 = mapper.selUserFollowListForTest(p1);
        // list2 : 10,20 을 추가한 전체 팔로우 리스트

        assertEquals(1,list2.size() - list1.size(),"실제 insert 되지 않음");
        // 리스트가 제대로 추가 되었는가

        List<UserFollowEntity> list3 = mapper.selUserFollowListForTest(p2);
        // list3 : 10,20 이 인서트 된 레코드 1개

        assertEquals(1,list3.size(),"p2값이 제대로 insert되지 않음");
        // 실제로 레코드가 한개가 맞는지?

        assertEquals(10,list3.get(0).getFromUserId());
        assertEquals(20,list3.get(0).getToUserId());
        // 값이 제대로 들어가 있는지?

        // 10, 20이 아닌 다른 값을 넣어서도 한번 더 확인
    }

    @Test
    @DisplayName("팔로우 select")
    void selUserFollowListForTest() {
        // 1. 전체 레코드 테스트
        UserFollowReq p1 = new UserFollowReq(0, 0);
        List<UserFollowEntity> list1 = mapper.selUserFollowListForTest(p1);
        assertEquals(12, list1.size(),"1. 레코드 수가 다르다");  //  에러 터지면 메시지 출력
        UserFollowEntity record0 = list1.get(0);
        assertEquals(1, record0.getFromUserId(),"1. 0번 레코드 from_user_id 다름");
        assertEquals(2, record0.getToUserId(),"1. 0번 레코드 to_user_id 다름");

        assertEquals(new UserFollowEntity(1,3,
                "2024-05-20 13:04:21"), list1.get(1),
                "1. 1번 레코드 값이 다름");

        // 2. from_user_id = 1
        UserFollowReq p2 = new UserFollowReq(1, 0);
        List<UserFollowEntity> list2 = mapper.selUserFollowListForTest(p2);
        assertEquals(4,list2.size(),"2. 레코드 수가 다르다.");
        assertEquals(new UserFollowEntity(1,2,
                "2024-05-20 13:04:15"), list2.get(0),
                "2. 0번 레코득 다름");
        assertEquals(new UserFollowEntity(1,3,
                        "2024-05-20 13:04:21"), list2.get(1),
                "2. 1번 레코득 다름");

        // 3. 없는 유저 아이디
        UserFollowReq p3 = new UserFollowReq(300,0);
        List<UserFollowEntity> list3 = mapper.selUserFollowListForTest(p3);
        assertEquals(0,list3.size(),"3. 레코드가 있으면 안됨");

        // 4. to_user_id = 1
        UserFollowReq p4 = new UserFollowReq(0, 1);
        List<UserFollowEntity> list4 = mapper.selUserFollowListForTest(p4);
        assertEquals(2,list4.size(),"4. 레코드 수가 다르다.");
        assertEquals(new UserFollowEntity(2,1,
                        "2024-05-20 13:04:32"), list4.get(0),
                "4. 0번 레코드 다름");
        assertEquals(new UserFollowEntity(3,1,
                        "2024-05-20 13:03:31"), list4.get(1),
                "4. 1번 레코드 다름");

    }

    @Test
    void delFollow() {
        UserFollowReq p1 = new UserFollowReq(0,0);
        List<UserFollowEntity> list1 = mapper.selUserFollowListForTest(p1);
        UserFollowReq del = new UserFollowReq(1,2);
        assertEquals(1,mapper.delFollow(del),"delete 되지 않음");
        List<UserFollowEntity> list2 = mapper.selUserFollowListForTest(p1);
        assertEquals(1, list1.size() - list2.size(),"데이터가 아직 남아있음");
        List<UserFollowEntity> list3 = mapper.selUserFollowListForTest(del);
        assertEquals(0,list3.size(),"레코드가 넘어오면 안됨");

        List<UserFollowEntity> list4 = mapper.selUserFollowListForTest(p1);
        UserFollowReq del2 = new UserFollowReq(4,5);
        assertEquals(0,mapper.delFollow(del2),"없는 데이터가 삭제된거..");
        List<UserFollowEntity> list5 = mapper.selUserFollowListForTest(p1);
        assertEquals(0,list4.size() - list5.size(),"뭔가가 삭제됐어");

    }


}