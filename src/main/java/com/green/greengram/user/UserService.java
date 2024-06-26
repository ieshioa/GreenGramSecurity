package com.green.greengram.user;

import com.green.greengram.user.model.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    int signUp(MultipartFile pic, PostUserReq p);
    SignInRes signIn (SignInReq p, HttpServletResponse res) ;
    UserInfoGetRes profileUserInfo(UserInfoGetReq p) ;
    String patchProfilePic(UserProfilePatchReq p);
}
