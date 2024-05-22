package com.green.greengram.feedfavorite;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.feedfavorite.model.FeedFavoriteToggleReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.green.greengram.common.model.ResultDto.returnDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/feed/favorite")
@Tag(name = "좋아요", description = "좋아요 토글 처리")
public class FeedFavoriteController {
    private final FeedFavoriteService service;

    @GetMapping
    @Operation(summary = "좋아요 토글", description = "favorite")
    public ResultDto<Integer> favorite(@ParameterObject @ModelAttribute FeedFavoriteToggleReq p) {
        int result = service.favorite(p);
        String msg = result == 1 ? "좋아요" : "좋아요 취소";
        return returnDto(HttpStatus.OK,msg,result);
    }

}
