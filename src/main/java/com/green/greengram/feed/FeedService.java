package com.green.greengram.feed;

import com.green.greengram.feed.model.GetFeedReq;
import com.green.greengram.feed.model.GetFeedRes;
import com.green.greengram.feed.model.PostFeedReq;
import com.green.greengram.feed.model.PostFeedRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FeedService {
    PostFeedRes postFeed(List<MultipartFile> pics, PostFeedReq p);
    List<GetFeedRes> getFeed (GetFeedReq p);
    // 구현부가 없는 메소드는 추상메소드
    // 구현부가 없음 > 객체화 못함
    // 부모로서만 존재함, 나를 무조건 상속받아라, 나를 오버라이딩해라
    // 추상클래스랑 인터페이스 둘만 추상메소드를 가질 수 있다.
    // public abstract 생략
    // 메소드에 final 붙으면 오버라이딩 금지
    // 클래스 앞에 붙으면 상속금지
}
