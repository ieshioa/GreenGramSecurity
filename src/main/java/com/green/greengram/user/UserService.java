package com.green.greengram.user;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final CustomFileUtils customFileUtils;

    @Transactional
    public int signUp(MultipartFile pic, PostUserReq p) {
        String hashPw = BCrypt.hashpw(p.getUpw(),BCrypt.gensalt());
        p.setUpw(hashPw);
        if(pic == null || pic.isEmpty()) {
            return mapper.insUser(p);
        }
        String fileName = customFileUtils.makeRandomFileName(pic);
        p.setPic(fileName);
        int result = mapper.insUser(p);
        try {
            String path = String.format("user/%s",p.getUserId());
            customFileUtils.makeFolders(path);
            String target = String.format("%s/%s",path,fileName);
            customFileUtils.transferTo(pic,target);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("프로필 사진 업로드 오류");
        }
        return result;
    }

    public SignInRes signIn (SignInReq p) {
        User user = mapper.getUser(p.getUid());
        if (user == null) {
            throw new RuntimeException("아이디가 없습니다");
        }
        if (!BCrypt.checkpw(p.getUpw(),user.getUpw())) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }
        return SignInRes.builder().userId(user.getUserId()).nm(user.getNm()).pic(user.getPic()).build();
    }

    public UserInfoGetRes profileUserInfo(UserInfoGetReq p) {
        return mapper.selProfileUserInfo(p);
    }

    @Transactional
    public String patchProfilePic(UserProfilePatchReq p) {
        String fileNm = customFileUtils.makeRandomFileName(p.getPic());
        p.setPicName(fileNm);
        mapper.updProfilePic(p);

        // 기존 폴더 삭제 & 저장
        try {
            // String folderPath = String.format("%s/user/%d",customFileUtils.uploadPath,p.getSignedUserId());
            String folderPath = String.format("user/%d", p.getSignedUserId());
            customFileUtils.deleteFolder(folderPath);
            customFileUtils.makeFolders(folderPath);
            String filePath = String.format("%s/%s",folderPath,fileNm);
            customFileUtils.transferTo(p.getPic(),filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return fileNm;
    }

}
