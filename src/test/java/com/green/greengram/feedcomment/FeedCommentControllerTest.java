package com.green.greengram.feedcomment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.CharEncodingConfiguration;
import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedcomment.model.DelCommentReq;
import com.green.greengram.feedcomment.model.GetCommentsRes;
import com.green.greengram.feedcomment.model.PostCommentReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({CharEncodingConfiguration.class})
@WebMvcTest({FeedCommentControllerImpl.class})
class FeedCommentControllerTest {

    @Autowired
    private ObjectMapper om;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private FeedCommentService service;

    private final String BASE_URL = "/api/feed/comment";


    @Test
    void postComment1() throws Exception {
        PostCommentReq p = new PostCommentReq();
        p.setComment("댓글");
        p.setFeedId(1);
        p.setUserId(2);
        int resultData = 1;
        given(service.postComment1(p)).willReturn(resultData);

        // Long
        // long
        // 속도차이
        String pJson = om.writeValueAsString(p);

        Map expect = new HashMap<>();
        expect.put("statusCode", HttpStatus.OK);
        expect.put("resultMsg", "댓글 작성 완료");
        expect.put("resultData", resultData);

        String expectJson = om.writeValueAsString(expect);

        mvc.perform(MockMvcRequestBuilders
                .post(BASE_URL+"/post1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(pJson)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(expectJson))
                .andDo(print());

        verify(service).postComment1(p);

    }

    @Test
    void postComment2() throws Exception {
        PostCommentReq p = new PostCommentReq();
        p.setComment("댓글");
        p.setFeedId(1);
        p.setUserId(2);
        Long resultData = 1L;
        given(service.postComment2(p)).willReturn(1L);

        String pJson = om.writeValueAsString(p);

        Map expect = new HashMap<>();
        expect.put("statusCode", HttpStatus.OK);
        expect.put("resultMsg", "댓글 작성 완료");
        expect.put("resultData", 1L);

        String expectJson = om.writeValueAsString(expect);

        mvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL+"/post2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pJson)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectJson))
                .andDo(print());

        verify(service).postComment2(p);

    }

    @Test
    void getComments() throws Exception {
        long feedId = 1;
        List<GetCommentsRes> list = new ArrayList<>();
        GetCommentsRes comment1 = new GetCommentsRes();
        comment1.setFeedCommentId(1);
        comment1.setComment("댓글1");
        comment1.setWriterId(1);

        list.add(comment1);

        MultiValueMap<String, String> p = new LinkedMultiValueMap<>();
        p.add("feed_id", String.valueOf(feedId));

        given(service.getComments(feedId)).willReturn(list);

//
//        Map result = new HashMap<>();
//        result.put("statusCode", HttpStatus.OK);
//        result.put("resultMsg", "댓글 불러오기");
//        result.put("resultData", list);

        ResultDto<List<GetCommentsRes>> result = ResultDto.<List<GetCommentsRes>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("댓글 불러오기")
                .resultData(list)
                .build();

        String resultJson = om.writeValueAsString(result);

        mvc.perform(
                get(BASE_URL)
                        .params(p)
        )   .andExpect(status().isOk())
                .andExpect(content().json(resultJson))
                .andDo(print());

        verify(service).getComments(feedId);

    }

    @Test
    void delComment() throws Exception {
        DelCommentReq p = new DelCommentReq(1,2);
        MultiValueMap<String, String> value = new LinkedMultiValueMap<>();
        value.add("signed_user_id", String.valueOf(p.getSignedUserId()));
        value.add("feed_comment_id", String.valueOf(p.getFeedCommentId()));

        int resultData = 1;
        given(service.delCommet(p)).willReturn(resultData);

        Map result = new HashMap<>();
        result.put("statusCode", HttpStatus.OK);
        result.put("resultMsg", "댓글 삭제");
        result.put("resultData", resultData);

        String resultJson = om.writeValueAsString(result);

        mvc.perform(
                delete(BASE_URL)
                        .params(value)
        ).andExpect(status().isOk())
                .andExpect(content().json(resultJson))
                .andDo(print());

        verify(service).delCommet(p);
    }
}