package com.green.greengram.feedcomment;

import com.green.greengram.feedcomment.model.DelCommentReq;
import com.green.greengram.feedcomment.model.GetCommentsRes;
import com.green.greengram.feedcomment.model.PostCommentReq;
import com.green.greengram.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.green.greengram.common.GlobalConst.COMMENT_SIZE_FER_FEED;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentServiceImpl implements FeedCommentService{
    private final FeedCommentMapper mapper;
    private final AuthenticationFacade authenticationFacade;

    public long postComment(PostCommentReq p) {
        p.setUserId(authenticationFacade.getLoginUserId());
        int r = mapper.insComment(p);
        return p.getCommentId();
    }

    public List<GetCommentsRes> getComments(long feedId) {
        List<GetCommentsRes> list = mapper.getComments(feedId);
        if(list.size() >= COMMENT_SIZE_FER_FEED) {
            list.subList(COMMENT_SIZE_FER_FEED-1,list.size()).clear();
        }
        return list;
    }

    public int delCommet(DelCommentReq p) {
        p.setSignedUserId(authenticationFacade.getLoginUserId());
        return mapper.delComment(p);
    }
}
