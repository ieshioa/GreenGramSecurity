package com.green.greengram.feedfavorite;

import com.green.greengram.feedfavorite.model.FeedFavoriteToggleReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.mockito.BDDMockito.given;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import({FeedFavoriteServiceImpl.class})
class FeedFavoriteServiceTest {

    @MockBean  // 테스트를 위해 가짜를 만들어 줌
    private FeedFavoriteMapper mapper;

    @Autowired
    private FeedFavoriteService service;

    @Test
    void favorite() {
        // given - when - then
        FeedFavoriteToggleReq p1 = new FeedFavoriteToggleReq(1,2);
        FeedFavoriteToggleReq p2 = new FeedFavoriteToggleReq(10,20);

        given(mapper.delFav(p1)).willReturn(0);  // 0을 리턴해줘
        given(mapper.delFav(p2)).willReturn(1); // 1을 리턴해줘

        given(mapper.insFav(p1)).willReturn(100);
        given(mapper.insFav(p2)).willReturn(200);

        int result1 = service.favorite(p1);
        assertEquals(100,result1);

        int result2 = service.favorite(p2);
        assertEquals(0,result2);

        verify(mapper, times(1)).delFav(p1);
        verify(mapper, times(1)).delFav(p2);

        verify(mapper, times(1)).insFav(p1);
        verify(mapper, never()).insFav(p2);  // p2일때는 실행되면 아됨

    }
}