package com.green.greengram.feed.model;

import com.green.greengram.common.model.Paging;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.BindParam;

import java.beans.ConstructorProperties;

import static com.green.greengram.common.GlobalConst.FEED_PAGING_SIZE;

@Getter
@Setter
public class GetFeedReq extends Paging {

    @Schema(name = "signed_user_id")
    private long signedUserId;
    @Schema(name = "profile_user_id", description = "프로필 화면에서 사용")
    private Long profileUserId;
    public GetFeedReq(Integer page, Integer size, @BindParam("signed_user_id") long signedUserId, @BindParam("profile_user_id") Long profileUserId) {
        super(page, size == null || size == 0 ? FEED_PAGING_SIZE : size);
        this.signedUserId = signedUserId;
        this.profileUserId = profileUserId;
    }
    /*
    @ConstructorProperties - 전체 수정할 때 편함
    @BindParam - 부분 수정할 때 편함
    결과 비슷함
    수정: 카멜케이스기법(signedUserId) -> 스네이크케이스기법(signed_user_id)
    제이슨으로 받을때는 카멜케이스로 필드명을 받아도 상관이 없는데
    쿼리스트링으로 받을 때는 쿼리스트링에 키값은 대문자를 안쓰는게 일반적임
    -- 마우스가 너무 무거워요 ;;; 마우스 교체하세요
     */

}
