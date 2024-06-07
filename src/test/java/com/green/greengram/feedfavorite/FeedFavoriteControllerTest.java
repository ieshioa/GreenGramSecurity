package com.green.greengram.feedfavorite;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.CharEncodingConfiguration;
import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedfavorite.model.FeedFavoriteToggleReq;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(CharEncodingConfiguration.class)
@WebMvcTest(FeedFavoriteController.class)
class FeedFavoriteControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper om;
    @MockBean private FeedFavoriteService service;

    @Test
    void favorite() throws Exception {
        FeedFavoriteToggleReq p = new FeedFavoriteToggleReq(1,2);
        int resultData = 1;
        given(service.favorite(p)).willReturn(resultData);


        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("feedId", String.valueOf(p.getFeedId()));
        param.add("userId", String.valueOf(p.getUserId()));

        String msg = resultData == 1 ? "좋아요" : "좋아요 취소";

        ResultDto<Integer> resultDto = ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(msg)
                .resultData(resultData)
                .build();

        String resultJson = om.writeValueAsString(resultDto);

        mvc.perform(
                get("/api/feed/favorite")
                        .params(param)
        )
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson))
                .andDo(print());

        verify(service).favorite(p);
    }
}