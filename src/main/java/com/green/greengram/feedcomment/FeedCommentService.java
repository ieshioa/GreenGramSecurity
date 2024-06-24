package com.green.greengram.feedcomment;

import com.green.greengram.feedcomment.model.DelCommentReq;
import com.green.greengram.feedcomment.model.GetCommentsRes;
import com.green.greengram.feedcomment.model.PostCommentReq;

import java.util.List;

public interface FeedCommentService {

    int postComment1(PostCommentReq p);
    long postComment2(PostCommentReq p);
    List<GetCommentsRes> getComments(long feedId);
    int delCommet(DelCommentReq p);
}