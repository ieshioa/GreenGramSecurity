package com.green.greengram.feed;

import com.green.greengram.feed.model.*;
import com.green.greengram.feedcomment.model.GetCommentsRes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedMapper {
    int insFeed(PostFeedReq p);
    int insPics(PicsDto p);
    List<GetFeedRes> getFeed(GetFeedReq p);
    List<String> getPics(long feedId);
    List<GetCommentsRes> getComments4 (long feedId);
}
