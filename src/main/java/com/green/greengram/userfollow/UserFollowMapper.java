package com.green.greengram.userfollow;

import com.green.greengram.userfollow.model.UserFollowEntity;
import com.green.greengram.userfollow.model.UserFollowReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserFollowMapper {

    int insFollow(UserFollowReq p);
    int delFollow(UserFollowReq p);
    List<UserFollowEntity> selUserFollowListForTest(UserFollowReq p);
}
