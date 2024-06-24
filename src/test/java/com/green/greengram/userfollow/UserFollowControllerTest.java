package com.green.greengram.userfollow;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.CharEncodingConfiguration;
import com.green.greengram.common.model.ResultDto;
import com.green.greengram.userfollow.model.UserFollowReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(CharEncodingConfiguration.class)  // 한글 안깨지게 하려고
@WebMvcTest(UserFollowControllerImpl.class)  // 얘를 빈등록해서 들고있어라
                                            //  그래야 UserFollowController 주소값 넣어줄 수 있으니까

@AutoConfigureMockMvc
class UserFollowControllerTest {
    @Autowired private ObjectMapper om;  // 제이슨을 객체로 객체를 제이슨으로 바꿔줌  왜? 쓰기 편하려고
    // object 는 주소값을 가지고 있는데 프론트에 주소값을 보내봤자 아무 의미가 없다.
    // 데이터가 필요함
    // 그래서 오브젝트를 json으로 바꿈
    // 다른데서도 쓸 수 있게 문자열로 바꿈 (직렬화)
    @Autowired private MockMvc mvc;  // restful 통신할 수 있게, 응답을 뭐로 하는지도 체크해줌
    @MockBean private UserFollowService service;  // 스프링 컨테이너는 절대 null을 주면서 객체화를 하지 않음
    // 진짜를 안쓰는게 속도가 더 빨라지니까 가짜를 써서 슬라이스 테스트를 한다.


    // DI, 빈등록, 컨트롤러impl에서 서비스 생성자 2개
    private final String BASE_URL = "/api/user/follow";
    @Test
    void postUserFollow() throws Exception {
        UserFollowReq p = new UserFollowReq(1,2);
        int resultData = 1;
        given(service.postUserFollow(p)).willReturn(resultData);
        String json = om.writeValueAsString(p);

        ResultDto<Integer> exResult = ResultDto.<Integer>builder()
                        .statusCode(HttpStatus.OK)
                        .resultMsg(HttpStatus.OK.toString())
                        .resultData(resultData)
                        .build();

        Map expectedResultMap = new HashMap();
        expectedResultMap.put("statusCode", HttpStatus.OK);
        expectedResultMap.put("resultMsg", HttpStatus.OK.toString());
        expectedResultMap.put("resultData", resultData);

      //  String exResultJson = om.writeValueAsString(exResult);
        String exResultJson = om.writeValueAsString(expectedResultMap);

        mvc.perform(MockMvcRequestBuilders
                .post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
                .andExpect(status().isOk())  // 응답이 200으로 왔는지
                .andExpect(content().json(exResultJson))  // 응답해준 값이 ()랑 같은지
                .andDo(print());        // 해도되고 안해도되고) 통신의 결과를 찍어라

        verify(service).postUserFollow(p);
    }

    @Test
    void delUserFollow() throws Exception {
        UserFollowReq p =new UserFollowReq(1,2);
        given(service.delUserFollow(p)).willReturn(1);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("from_user_id", String.valueOf(p.getFromUserId()));
        params.add("to_user_id", String.valueOf(p.getToUserId()));

        ResultDto<Integer> exResult = ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(1)
                .build();

        String exResultJson = om.writeValueAsString(exResult);

        mvc.perform(
                delete(BASE_URL)
                        .params(params)  // 쿼리스트링으로 바꿔줌
        ).andExpect(status().isOk())
                .andExpect(content().json(exResultJson))
                .andDo(print());

        verify(service).delUserFollow(p);
    }
}