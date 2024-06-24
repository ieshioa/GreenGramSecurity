package com.green.greengram.feedfavorite;

import com.green.greengram.BaseIntegrationTest;
import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedfavorite.model.FeedFavoriteToggleReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FeedFavoriteIntegrationTest extends BaseIntegrationTest {
    private final String BASE_URL = "/api/feed/favorite";

    @Test
    @Rollback(value = false)
    @DisplayName("get - 좋아요 토글")
    public void favoriteTest() throws Exception {
        FeedFavoriteToggleReq p = new FeedFavoriteToggleReq(5,1);

        MultiValueMap<String, String> req = new LinkedMultiValueMap<>();
        req.add("feed_id", String.valueOf(p.getFeedId()));
        req.add("user_id", String.valueOf(p.getUserId()));

        MvcResult mr = mvc.perform(
                get(BASE_URL)
                        .params(req)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        String mrResult = mr.getResponse().getContentAsString();
        ResultDto<Integer> result = om.readValue(mrResult, ResultDto.class);
        // resultData 1 = 좋아요, 0 = 좋아요 취소
        assertEquals(0,result.getResultData());

        // ================================================================================

        FeedFavoriteToggleReq p2 = new FeedFavoriteToggleReq(5,2);
        MultiValueMap<String, String> req2 = new LinkedMultiValueMap<>();
        req2.add("feed_id", String.valueOf(p2.getFeedId()));
        req2.add("user_id", String.valueOf(p2.getUserId()));

        int resultData = 1;
        String msg = resultData == 1 ? "좋아요" : "좋아요 취소";

        Map expectResult = new HashMap();
        expectResult.put("statusCode", HttpStatus.OK);
        expectResult.put("resultMsg", msg);
        expectResult.put("resultData", resultData);

        String expectResultJson = om.writeValueAsString(expectResult);
        mvc.perform(
                        get(BASE_URL)
                                .params(req2)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectResultJson))
                .andDo(print());
    }

}
