package com.green.greengram.user;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.green.greengram.common.model.ResultDto.returnDto;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")

@Tag(name = "유저 컨트롤러", description = "회원가입, 로그인")
public class UserControllerImpl {
    private final UserServiceImpl service;

    @PostMapping("sign-up")
    @Operation(summary = "signUp", description = "회원가입")
    public ResultDto<Integer> signUp (@RequestPart(required = false)MultipartFile pic
                                        , @RequestPart PostUserReq p) {
        int result = service.signUp(pic,p);
        return returnDto(HttpStatus.OK,"회원가입 성공", result);
    }

    @PostMapping("sign-in")
    @Operation(summary = "signIn", description = "로그인")
    public ResultDto<SignInRes> signIn (@RequestBody SignInReq p) {
        SignInRes result = service.signIn(p);
        return returnDto(HttpStatus.OK,"로그인 성공", result);
    }

    @GetMapping
    public ResultDto<UserInfoGetRes> profileUserInfo (@ParameterObject @ModelAttribute UserInfoGetReq p) {
        UserInfoGetRes result = service.profileUserInfo(p);
        return returnDto(HttpStatus.OK,HttpStatus.OK.toString(), result);
    }

    @PatchMapping(value = "pic", consumes = "multipart/form-data")
    public ResultDto<String> patchProfilePic(@ModelAttribute UserProfilePatchReq p) {
        String result = service.patchProfilePic(p);
        return returnDto(HttpStatus.OK,"",result);
    }

}
