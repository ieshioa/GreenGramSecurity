package com.green.greengram.user;

import com.green.greengram.user.model.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    int signUp(MultipartFile pic, PostUserReq p);
    SignInRes signIn (SignInReq p) ;
    UserInfoGetRes profileUserInfo(UserInfoGetReq p) ;
    String patchProfilePic(UserProfilePatchReq p);
}
