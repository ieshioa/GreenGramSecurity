package com.green.greengram.userfollow;

import com.green.greengram.BaseIntegrationTest;
import com.green.greengram.common.model.ResultDto;
import com.green.greengram.userfollow.model.UserFollowReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



public class UserFollowIntegrationTest extends BaseIntegrationTest {

    private final String BASE_URL = "/api/user/follow";

    @Test
    @Rollback(value = false)  // 데이터베이스가 들어가는지 눈으로 확인하려고 false함
    @DisplayName("post - 유저팔로우")
    public void postUserFollow() throws Exception {
        // 포스트맨 쓰는것을 코드화 했다고 볼 수 있다.
        // 직접 눈으로 확인하기 위해
        UserFollowReq p = new UserFollowReq(4,1);       // 데이터를 보내기 위해 json 작성
        String reqJson = om.writeValueAsString(p);

        // mr = 응답과 관련된 모든 정보
        MvcResult mr = mvc.perform(     // perform = send
                post(BASE_URL)                      // 메소드랑 url 설정
                        .contentType(MediaType.APPLICATION_JSON)    // 포스트맨에서는 body로 보내면 이부분 자동으로 추가됨
                        .content(reqJson)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String resContents = mr.getResponse().getContentAsString();
        ResultDto<Integer> result = om.readValue(resContents, ResultDto.class);  // (스트링, 스트링을 변환하고 싶은 객체)
        assertEquals(1,result.getResultData());
        // .andExpect(content().json(expectResultJson)) 이랑 위에 3줄이라 같은거임
        // 스트링으로 받아서 객체로 비교하는 방법 해본거임
    }

    @Test
    @Rollback(value = false)
    @DisplayName("delete - 유저팔로우")
    public void deleteUserFollow() throws Exception {
        UserFollowReq p = new UserFollowReq(1,2);

        MultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.add("from_user_id", String.valueOf(p.getFromUserId()));
        req.add("to_user_id", String.valueOf(p.getToUserId()));

        MvcResult mr = mvc.perform(
                delete(BASE_URL)
                        .params(req)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String resContent = mr.getResponse().getContentAsString();
        ResultDto<Integer> result = om.readValue(resContent, ResultDto.class);
        assertEquals(1, result.getResultData());
    }
}
