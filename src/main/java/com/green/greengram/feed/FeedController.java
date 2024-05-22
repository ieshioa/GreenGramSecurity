package com.green.greengram.feed;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feed.model.GetFeedReq;
import com.green.greengram.feed.model.GetFeedRes;
import com.green.greengram.feed.model.PostFeedReq;
import com.green.greengram.feed.model.PostFeedRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.green.greengram.common.model.ResultDto.returnDto;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/feed")
@Tag(name = "피드 컨트롤러", description = "post, get")
public class FeedController {
    private final FeedService service;

    @PostMapping
    @Operation(summary = "피드 등록", description = "postFeed")
    public ResultDto<PostFeedRes> postFeed(@RequestPart(required = false)List<MultipartFile> pics, @RequestPart PostFeedReq p) {
        PostFeedRes result = service.postFeed(pics,p);
        return returnDto(HttpStatus.OK,"피드 등록 완료", result);
    }

    @GetMapping
    @Operation(summary = "피드 불러오기", description = "getFeed")
    public ResultDto<List<GetFeedRes>> getFeed(@ParameterObject @ModelAttribute GetFeedReq p) {
        List<GetFeedRes> result = service.getFeed(p);
        return returnDto(HttpStatus.OK,"피드 불러오기", result);
    }

}
