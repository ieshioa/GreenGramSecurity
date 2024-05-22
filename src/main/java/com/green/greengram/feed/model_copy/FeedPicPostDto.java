package com.green.greengram.feed.model_copy;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
// feed_pics insert 매퍼에 들어감
@Getter
@Builder
public class FeedPicPostDto {
    private long feedId;
    @Builder.Default  // 이거 안해주면 null임
    private List<String> fileNames = new ArrayList<>();
}
/*
feedId = 10, fileNames = a.jpg, b.jpg, c.jpg 가 담겨있다면


INSERT INTO feed_pics
( feed_id, pic )
VALUES
<foreach item="item" collection="fileNames" separator=",">
( #{feedId}, #{item} )
</foreach>


INSERT INTO feed_pics
( feed_id, pic )
VALUES
( 10, 'a.jpg' )
, ( 10, 'b.jpg' )
, ( 10, 'c.jpg' )


방법 2

<foreach item="item" collection="fileNames"
         open="(" separator="),(" close=")">
    #{feedId}, #{item}
</foreach>


"(" 으로 시작
"),(" 으로 구분
")" 으로 끝

 */