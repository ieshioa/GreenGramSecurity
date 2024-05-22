package com.green.greengram.feed.model_copy;

import com.green.greengram.common.model_copy.Paging;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.BindParam;


@Getter
@Setter
public class FeedGetReq extends Paging {

    private long signedUserId;
    // 유저 pk 앞에 @BindParam("loginedUserId") 붙이면 그린그램에서 보기 가능
    // 안붙이면 swagger에서 보기 가능
    // 지금 이름이 다르게 설정돼있음
    public FeedGetReq(Integer page, Integer size
                , @BindParam("signed_user_id") long signedUserId) {
        super(page, size == null ? 20 : size);
        this.signedUserId = signedUserId;
    }

}
