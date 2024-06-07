package com.green.greengram.userfollow;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.userfollow.model.UserFollowReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.green.greengram.common.model.ResultDto.returnDto;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/user/follow")
public class UserFollowControllerImpl implements UserFollowController {
    private final UserFollowService service;

    @Override
    @PostMapping
    public ResultDto<Integer> postUserFollow(@RequestBody UserFollowReq p) {
        int result = service.postUserFollow(p);
        return returnDto(HttpStatus.OK,HttpStatus.OK.toString(),result);
    }

    @Override
    @DeleteMapping
    public ResultDto<Integer> delUserFollow(@ParameterObject @ModelAttribute UserFollowReq p) {
        int result = service.delUserFollow(p);
        return returnDto(HttpStatus.OK,HttpStatus.OK.toString(),result);
    }
}
