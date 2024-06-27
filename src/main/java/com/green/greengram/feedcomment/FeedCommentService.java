package com.green.greengram.feedcomment;

import com.green.greengram.feedcomment.model.DelCommentReq;
import com.green.greengram.feedcomment.model.GetCommentsRes;
import com.green.greengram.feedcomment.model.PostCommentReq;

import java.util.List;

public interface FeedCommentService {

    long postComment(PostCommentReq p);
    List<GetCommentsRes> getComments(long feedId);
    int delCommet(DelCommentReq p);
}