package com.green.greengram.feed;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.feed.model.*;
import com.green.greengram.feedcomment.model.GetCommentsRes;
import com.green.greengram.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.green.greengram.common.GlobalConst.COMMENT_SIZE_FER_FEED;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedServiceImpl implements FeedService {
    private final FeedMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final AuthenticationFacade authenticationFacade;

    @Transactional
    public PostFeedRes postFeed(List<MultipartFile> pics, PostFeedReq p) {
        p.setUserId(authenticationFacade.getLoginUserId());
        int insFeedResult = mapper.insFeed(p);
        if(pics == null || pics.get(0).isEmpty()) {
            return PostFeedRes.builder().feedId(p.getFeedId()).build();
        }
        PicsDto picsDto = PicsDto.builder().feedId(p.getFeedId()).build();
        try {
            String path = String.format("feed/%s",p.getFeedId());
            String dd = customFileUtils.makeFolders(path);
            log.info("== {}",dd);
            for(MultipartFile pic : pics) {
                String fileName = customFileUtils.makeRandomFileName(pic);
                picsDto.getPics().add(fileName);
                String target = String.format("%s/%s",path,fileName);
                customFileUtils.transferTo(pic,target);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("피드 이미지 업로드 오류");
        }
        int insPicsResult = mapper.insPics(picsDto);
        return PostFeedRes.builder().feedId(p.getFeedId()).pics(picsDto.getPics()).build();
    }

    public List<GetFeedRes> getFeed (GetFeedReq p) {
        p.setSignedUserId(authenticationFacade.getLoginUserId());
        List<GetFeedRes> list = mapper.getFeed(p);
        for (GetFeedRes feed : list) {
            long feedId = feed.getFeedId();
            List<GetCommentsRes> comments = mapper.getComments4(feedId);
            if(comments.size() == COMMENT_SIZE_FER_FEED){
                comments.remove(COMMENT_SIZE_FER_FEED-1);
                feed.setIsMoreComment(1);
            }
            feed.setComments(comments);
            List<String> pics = mapper.getPics(feedId);
            feed.setPics(pics);
        }
        return list;
    }
}
