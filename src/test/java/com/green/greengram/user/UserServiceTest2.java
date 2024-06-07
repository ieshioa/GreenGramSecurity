package com.green.greengram.user;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.user.model.PostUserReq;
import com.green.greengram.user.model.UserInfoGetReq;
import com.green.greengram.user.model.UserInfoGetRes;
import com.green.greengram.user.model.UserProfilePatchReq;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@Import({UserServiceImpl.class})
@TestPropertySource(
        properties = {
                "file.dir=D:/Students/MJ/download/greengram_tdd_test/"
        }
)
class UserServiceTest2 {

    @Value(("${file.dir}")) String uploadPath;
    @MockBean UserMapper mapper;
    @MockBean CustomFileUtils customFileUtils;
    @Autowired UserService service;

    @Test
    void signUp()   {
    }

    @Test
    void signUpPostReq() {
    }

    @Test
    void signIn() {
    }

    @Test
    void profileUserInfo() {
    }

    @Test
    void patchProfilePic() throws Exception {
        // CustomFileUtils 가짜 쓰는 테스트
        long signedUserId1 = 500;
        UserProfilePatchReq p1 = new UserProfilePatchReq();
        p1.setSignedUserId(signedUserId1);
        MultipartFile fm1 = new MockMultipartFile(
                "pic", "1.jpg", "image/jpg",
                new FileInputStream(String.format("%s/test/1.jpg", uploadPath))
        );
        p1.setPic(fm1);
        // 여기까지가 파라미터 세팅

        // 가짜한테 임무주기 (안해주면 null을 리턴함)
        given(customFileUtils.makeRandomFileName(fm1)).willReturn("a1b2.jpg");

        String fileNm1 = service.patchProfilePic(p1);
        assertEquals("a1b2.jpg", fileNm1);

        // mapper가 호출이 됐는지 확인
        verify(mapper).updProfilePic(p1);
        String midpath = String.format("user/%d", signedUserId1);
        verify(customFileUtils).deleteFolder(midpath);
        verify(customFileUtils).makeFolders(midpath);
        verify(customFileUtils).transferTo(fm1,String.format("%s/%s", midpath, fileNm1));

    }
}