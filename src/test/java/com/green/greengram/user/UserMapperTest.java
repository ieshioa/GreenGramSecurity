package com.green.greengram.user;

import com.green.greengram.user.model.User;
import com.green.greengram.user.model.UserInfoGetReq;
import com.green.greengram.user.model.UserInfoGetRes;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ActiveProfiles("tdd")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {
    @Autowired
    private UserMapper mapper;

    @Test
    void insUser() {
        User user1 = mapper.getUser("사용자1");
        List<User> userList1 = mapper.selTest(user1.getUserId());
        User user1Comp = userList1.get(0);
        assertEquals(user1Comp,user1);

        User user3 = mapper.getUser("사용자3");
        List<User> userList3 = mapper.selTest(user3.getUserId());
        User user3Comp = userList3.get(0);
        assertEquals(user3Comp,user3);

        User userNo = mapper.getUser("이런사람없어요");
        assertNull(userNo,"없는 사람인데요");
    }

    @Test
    void getUser() {
    }

    @Test
    void selProfileUserInfo() {
        UserInfoGetReq p1 = new UserInfoGetReq(2,1);
        UserInfoGetRes p1res = mapper.selProfileUserInfo(p1);
        assertEquals("사용자1", p1res.getNm(),"이름 다름");
        assertEquals(2, p1res.getFeedCnt(),"피드 개수 다름");

        UserInfoGetReq p2 = new UserInfoGetReq(3,4);
        UserInfoGetRes p2res = mapper.selProfileUserInfo(p2);
        User p2User = mapper.getUser("사용자4");
        assertEquals(p2User.getNm(),p2res.getNm(), "이름 다름");
        assertEquals(p2User.getPic(),p2res.getPic(), "사진 다름");


    }

    @Test
    void updProfilePic() {
    }
}