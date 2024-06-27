package com.green.greengram.feedcomment;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedcomment.model.DelCommentReq;
import com.green.greengram.feedcomment.model.GetCommentsRes;
import com.green.greengram.feedcomment.model.PostCommentReq;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.green.greengram.common.model.ResultDto.returnDto;

public interface FeedCommentController {
    ResultDto<Integer> postComment (PostCommentReq p) ;
    ResultDto<List<GetCommentsRes>> getComments (long feedId);
    ResultDto<Integer> delComment(DelCommentReq p) ;

}
