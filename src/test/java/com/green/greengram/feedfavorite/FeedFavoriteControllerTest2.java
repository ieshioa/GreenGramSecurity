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

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(CharEncodingConfiguration.class)
@WebMvcTest(FeedFavoriteController.class)
class FeedFavoriteControllerTest2 {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper om;
    @MockBean private FeedFavoriteService service;

    void proc(FeedFavoriteToggleReq p, Map<String, Object> result) throws Exception {
        int resultData = (int)result.get("resultData");
        given(service.favorite(p)).willReturn(resultData);

        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("feed_id", String.valueOf(p.getFeedId()));
        param.add("user_id", String.valueOf(p.getUserId()));


        String resultJson = om.writeValueAsString(result);

        mvc.perform(
                        get("/api/feed/favorite")
                                .params(param)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(resultJson))
                .andDo(print());

        verify(service).favorite(p);
    }

    @Test
    void favorite() throws Exception {
        FeedFavoriteToggleReq p = new FeedFavoriteToggleReq(1,2);
        int resultData = 0;
        String msg = resultData == 1 ? "좋아요" : "좋아요 취소";

        Map<String, Object> result = new HashMap<>();
        result.put("statusCode", HttpStatus.OK);
        result.put("resultMsg", msg);
        result.put("resultData", resultData);

        proc(p, result);

    }
}