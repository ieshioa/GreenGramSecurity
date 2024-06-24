package com.green.greengram.feedcomment;

import com.green.greengram.feedcomment.model.DelCommentReq;
import com.green.greengram.feedcomment.model.GetCommentsRes;
import com.green.greengram.feedcomment.model.PostCommentReq;
import com.green.greengram.feedcomment.model_copy.FeedCommentDelReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import({FeedCommentServiceImpl.class})
class FeedCommentServiceTest {

    @MockBean
    private FeedCommentMapper mapper;

    @Autowired
    private FeedCommentService service;

    @Test
    void postComment() {
        PostCommentReq p1 = new PostCommentReq();
        p1.setFeedId(1);
        p1.setUserId(2);
        p1.setComment("댓글");
        given(mapper.insComment(p1)).willReturn(1);
        int r = service.postComment1(p1);
        assertEquals(1,r);
        verify(mapper).insComment(p1);

    }

    @Test
    void getComments() {
        List<GetCommentsRes> list1 = new ArrayList<>();
        List<GetCommentsRes> list2 = new ArrayList<>();

        GetCommentsRes r1 = new GetCommentsRes();
        GetCommentsRes r2 = new GetCommentsRes();

        list1.add(r1);
        list1.add(r2);
        r1.setFeedCommentId(1);
        r1.setWriterId(100);
        r1.setWriterNm("홍길동");
        r1.setWriterPic("홍길동.jpg");
        r1.setComment("r1");

        r2.setFeedCommentId(20);
        r2.setComment("r2");
        r2.setWriterId(200);
        r2.setWriterNm("남길동");
        r2.setWriterPic("남길동.jpg");

        long paramFeedId1 = 5;
        long paramFeedId2 = 7;

        given(mapper.getComments(paramFeedId1)).willReturn(list1);
        given(mapper.getComments(paramFeedId2)).willReturn(list2);

        List<GetCommentsRes> result1 = service.getComments(paramFeedId1);
        assertEquals(list1, result1, "1. 리턴값이 다름");

        List<GetCommentsRes> result2 = service.getComments(paramFeedId2);
        assertEquals(list2.size(), result2.size(), "2. 리턴값이 다름");

        verify(mapper).getComments(paramFeedId1);
        verify(mapper).getComments(paramFeedId2);
    }

    @Test
    void delCommet() {
        DelCommentReq p1 = new DelCommentReq(1,2);
        given(mapper.delComment(p1)).willReturn(1);
        int r = service.delCommet(p1);
        assertEquals(1,r);
        verify(mapper).delComment(p1);

        DelCommentReq p2 = new DelCommentReq(5,6);
        given(mapper.delComment(p2)).willReturn(0);
        int r2 = service.delCommet(p2);
        assertEquals(0,r2);
        verify(mapper).delComment(p2);

    }
}