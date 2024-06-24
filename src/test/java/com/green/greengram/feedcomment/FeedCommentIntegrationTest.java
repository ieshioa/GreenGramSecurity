package com.green.greengram.feedcomment;

import com.fasterxml.jackson.core.type.TypeReference;
import com.green.greengram.BaseIntegrationTest;
import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedcomment.model.DelCommentReq;
import com.green.greengram.feedcomment.model.GetCommentsRes;
import com.green.greengram.feedcomment.model.PostCommentReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedCommentIntegrationTest extends BaseIntegrationTest {
    private final String BASE_URL = "/api/feed/comment";

    @Test
    @Rollback(value = false)
    @DisplayName("post - 댓글")
    public void commentPostTest() throws Exception{
        PostCommentReq p = new PostCommentReq();
        p.setFeedId(1);
        p.setUserId(2);
        p.setComment("댓글 ~");

        String reqJson = om.writeValueAsString(p);

        MvcResult mr = mvc.perform(
                post(BASE_URL+"/post2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reqJson)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String res = mr.getResponse().getContentAsString();
        ResultDto<Integer> result = om.readValue(res, ResultDto.class);
        assertEquals(11, result.getResultData());
    }

    @Test
    @Rollback(value = false)
    @DisplayName("get - 댓글")
    public void commentListTest() throws Exception{
        long feedId = 20;

        MvcResult mr = mvc.perform(
                get(BASE_URL)
                        .param("feed_id",String.valueOf(feedId))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String res = mr.getResponse().getContentAsString();
        ResultDto<List<GetCommentsRes>> result = om.readValue(res, ResultDto.class);
    }

    @Test
    @Rollback(value = false)
    @DisplayName("delete - 댓글")
    public void deleteCommentTest() throws Exception{
        DelCommentReq p = new DelCommentReq(1,1);
        MultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.add("signed_user_id", String.valueOf(p.getSignedUserId()));
        req.add("feed_comment_id", String.valueOf(p.getFeedCommentId()));

        MvcResult mr = mvc.perform(
                delete(BASE_URL)
                        .params(req)
        ).andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String res = mr.getResponse().getContentAsString();
        ResultDto<Integer> result = om.readValue(res, ResultDto.class);
        assertEquals(1,result.getResultData());
    }
}
