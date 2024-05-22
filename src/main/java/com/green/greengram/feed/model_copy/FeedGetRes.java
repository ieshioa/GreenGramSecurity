package com.green.greengram.feed.model_copy;

import com.green.greengram.feedcomment.model_copy.FeedCommentGetRes;
import lombok.*;

import java.util.List;

@Setter
@Builder
@Getter
@NoArgsConstructor  // 제이슨으로 바꾸려면 둘다 써줘야함
@AllArgsConstructor //
public class FeedGetRes {  // 응답은 무조건 json
                            // 생성자를 이용하는게 아니라 빌더패턴을 쓰고싶음

    private long feedId;
    private List<String> pics;
    private long writerId;
    private String writerNm;
    private String writerPic;
    private String contents;
    private String location;
    private String createdAt;
    private int isFav;
    private int isMoreComment;
    private List<FeedCommentGetRes> comments;


    // N+1
}
