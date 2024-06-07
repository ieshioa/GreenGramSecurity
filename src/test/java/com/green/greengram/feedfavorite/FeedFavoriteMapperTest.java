package com.green.greengram.feedfavorite;

import com.green.greengram.feedfavorite.model.FeedFavoriteEntity;
import com.green.greengram.feedfavorite.model.FeedFavoriteToggleReq;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("tdd")
class FeedFavoriteMapperTest {

    @Autowired
    private FeedFavoriteMapper mapper;

    @Test
    void insFav() {
        FeedFavoriteToggleReq all = new FeedFavoriteToggleReq(0,0);
        List<FeedFavoriteEntity> beforeInsertList = mapper.selFeedFavoriteForTest(all);
        FeedFavoriteToggleReq ins1 = new FeedFavoriteToggleReq(5,2);
        assertEquals(1,mapper.insFav(ins1),"영향받은 행이 0임");
        List<FeedFavoriteEntity> afterInsertList = mapper.selFeedFavoriteForTest(all);
        assertEquals(1, afterInsertList.size() - beforeInsertList.size(),"데이터베이스에 저장되지 않음");
        List<FeedFavoriteEntity> listIns1 = mapper.selFeedFavoriteForTest(ins1);
        assertEquals(5,listIns1.get(0).getFeedId(),"feedId가 다름");
        assertEquals(2,listIns1.get(0).getUserId(),"userId가 다름");

    }

    @Test
    void delFav() {
        FeedFavoriteToggleReq all = new FeedFavoriteToggleReq(0,0);
        List<FeedFavoriteEntity> beforeDeleteList = mapper.selFeedFavoriteForTest(all);
        FeedFavoriteToggleReq del1 = new FeedFavoriteToggleReq(5,1);
        assertEquals(1,mapper.delFav(del1),"영향받은 행이 0임");
        List<FeedFavoriteEntity> afterDeleteList = mapper.selFeedFavoriteForTest(all);
        assertEquals(1, beforeDeleteList.size() - afterDeleteList.size(),"데이터베이스에서 삭제되지 않음");
        List<FeedFavoriteEntity> listDel1 = mapper.selFeedFavoriteForTest(del1);
        assertEquals(0,listDel1.size(),"리스트의 사이즈가 0이어야함");

        FeedFavoriteToggleReq del2 = new FeedFavoriteToggleReq(15,1);
        List<FeedFavoriteEntity> listDel2 = mapper.selFeedFavoriteForTest(del2);
        assertEquals(0,listDel2.size(),"없는 값을 입력함");
        assertEquals(0,mapper.delFav(del2),"영향받은 행이 있으면 안됨");

    }

    @Test
    void selFeedFavoriteForTest() {

    }
}