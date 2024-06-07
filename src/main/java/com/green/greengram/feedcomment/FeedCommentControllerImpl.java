package com.green.greengram.feedcomment;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedcomment.model.DelCommentReq;
import com.green.greengram.feedcomment.model.GetCommentsRes;
import com.green.greengram.feedcomment.model.PostCommentReq;
import com.green.greengram.feedfavorite.FeedFavoriteController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.green.greengram.common.model.ResultDto.returnDto;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("api/feed/comment")
@Tag(name = "댓글 컨트롤러", description = "댓글 작성 삭제")
public class FeedCommentControllerImpl implements FeedCommentController {
    private final FeedCommentServiceImpl service;

    @PostMapping
    @Operation(summary = "댓글 작성", description = "postComment")
    public ResultDto<Integer> postComment (@RequestBody PostCommentReq p) {
        int result = service.postComment(p);
        return returnDto(HttpStatus.OK,"댓글 작성 완료", result);
    }

    @GetMapping
    public ResultDto<List<GetCommentsRes>> getComments (@RequestParam("feed_id") long feedId) {
        List<GetCommentsRes> result = service.getComments(feedId);
        return returnDto(HttpStatus.OK,"댓글 불러오기", result);
    }

    @DeleteMapping
    public ResultDto<Integer> delComment(@ParameterObject @ModelAttribute DelCommentReq p) {
        int result = service.delCommet(p);
        return returnDto(HttpStatus.OK,"댓글 삭제", result);
    }

}