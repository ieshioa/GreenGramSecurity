package com.green.greengram.feedcomment;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedcomment.model.DelCommentReq;
import com.green.greengram.feedcomment.model.GetCommentsRes;
import com.green.greengram.feedcomment.model.PostCommentReq;
import com.green.greengram.feedfavorite.FeedFavoriteController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private final FeedCommentService service;

    @PostMapping("post1")
    @Operation(summary = "댓글 작성", description = "postComment")
    public ResultDto<Integer> postComment1 (@RequestBody PostCommentReq p) {
        int result = service.postComment1(p);
        return returnDto(HttpStatus.OK,"댓글 작성 완료", result);
    }

    @PostMapping("post2")
    @Operation(summary = "댓글 작성", description = "postComment")
    public ResultDto<Integer> postComment2 (@RequestBody PostCommentReq p) {
        long result = service.postComment2(p);
        int r = (int)result;
        return returnDto(HttpStatus.OK,"댓글 작성 완료", r);
    }

    @GetMapping
    @Operation(summary = "검색한 board 목록 가지고오기", description = "<strong>검색한 board 목록을 불러온다요~!~!</strong>" +
            "<p>검색어를 넣어주세요~!~!</p>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "dfs", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "401", description = "asda", content = @Content(schema = @Schema(implementation = ResultDto.class)))
    })
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