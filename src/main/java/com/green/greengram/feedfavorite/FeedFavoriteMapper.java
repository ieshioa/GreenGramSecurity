package com.green.greengram.feedfavorite;

import com.green.greengram.feedfavorite.model.FeedFavoriteToggleReq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FeedFavoriteMapper {
    int insFav(FeedFavoriteToggleReq p);
    int delFav(FeedFavoriteToggleReq p);
}
