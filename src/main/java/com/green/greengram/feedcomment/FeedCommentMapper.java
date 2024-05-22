package com.green.greengram.feedcomment;


import com.green.greengram.feedcomment.model.DelCommentReq;
import com.green.greengram.feedcomment.model.GetCommentsRes;
import com.green.greengram.feedcomment.model.PostCommentReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedCommentMapper {
    int insComment(PostCommentReq p);
    List<GetCommentsRes> getComments(long feedId);
    int delComment(DelCommentReq p);
}

