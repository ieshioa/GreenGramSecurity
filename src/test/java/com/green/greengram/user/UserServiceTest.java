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
import org.springframework.test.context.ActiveProfiles;
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

@ExtendWith(SpringExtension.class)
@Import({CustomFileUtils.class, UserServiceImpl.class})
@TestPropertySource(
        properties = {
                "file.dir=D:/Students/MJ/download/greengram_tdd_test/"
        }
)
class UserServiceTest {

    @Value(("${file.dir}")) String uploadPath;
    @MockBean
    private UserMapper mapper;
    @Autowired
    private UserService service;
    @Autowired private CustomFileUtils customFileUtils;

    @Test
    void signUp() throws IOException {
        String p1Upw = "qw";
        PostUserReq p1 = new PostUserReq();
        p1.setUserId(103);
        p1.setUpw(p1Upw);

        given(mapper.insUser(p1)).willReturn(1);

        PostUserReq p2 = new PostUserReq();
        p2.setUserId(20);
        p2.setUpw("as");

        given(mapper.insUser(p2)).willReturn(2);

        MultipartFile m1 = new MockMultipartFile("pic", // 유저 컨트롤러에 변수명이랑 맞추기
                "6f437ca4-236c-40d1-9868-9702e4229037.jpg",
                "image/jpg",
                new FileInputStream(("D:/Students/MJ/download/greengram_tdd_test/user/1/6f437ca4-236c-40d1-9868-9702e4229037.jpg"))); // 절대경로
        int result1 = service.signUp(m1,p1);
        assertEquals(1, result1);

        p1.getPic();

        File savedFile1 = new File(uploadPath, String.format("user/%d/%s", p1.getUserId(),p1.getPic()));
        assertTrue(savedFile1.exists(), "파일이 만들어지지 않음");  // 연동이 됐다면 exists 햇을 때 트루
        savedFile1.delete();

        assertNotEquals(p1Upw, p1.getUpw());
    }

    @Test
    void signUpPostReq() throws IOException {
        String p1Upw = "abc";
        PostUserReq p1 = new PostUserReq();
        p1.setUserId(200);
        p1.setUpw(p1Upw);
        given(mapper.insUser(p1)).willReturn(1);
        String p2Upw = "def";
        PostUserReq p2 = new PostUserReq();
        p2.setUserId(200);
        p2.setUpw("def");
        given(mapper.insUser(p2)).willReturn(2);
        MultipartFile fm1 = new MockMultipartFile(
                "pic", "6f437ca4-236c-40d1-9868-9702e4229037.jpg", "image/jpg",
                new FileInputStream("D:/Students/MJ/download/greengram_tdd_test/user/1/6f437ca4-236c-40d1-9868-9702e4229037.jpg")
        );
        int result1 = service.signUp(fm1, p1);
        assertEquals(1, result1);
        File savedFile1 = new File(uploadPath
                , String.format("%s/%d/%s", "user", p1.getUserId(), p1.getPic()));
        assertTrue(savedFile1.exists(), "1. 파일이 만들어지지 않음");
        savedFile1.delete();
        assertNotEquals(p1Upw, p1.getUpw());
        int result2 = service.signUp(fm1, p2);
        assertEquals(2, result2);
        File savedFile2 = new File(uploadPath
                , String.format("%s/%d/%s", "user", p2.getUserId(), p2.getPic()));
        assertTrue(savedFile2.exists(), "2. 파일이 만들어지지 않음");
        savedFile2.delete();
        assertNotEquals(p2Upw, p2.getUpw());
    }

    @Test
    void signIn() {

    }

    @Test
    void profileUserInfo() {
        UserInfoGetReq p1 = new UserInfoGetReq(1,2);
        UserInfoGetRes result1 = new UserInfoGetRes();
        result1.setNm("test1");
        given(mapper.selProfileUserInfo(p1)).willReturn(result1);

        UserInfoGetRes res1 = mapper.selProfileUserInfo(p1);
        assertEquals(result1,res1);

        UserInfoGetReq p2 = new UserInfoGetReq(3,1);
        UserInfoGetRes result2 = new UserInfoGetRes();
        result2.setNm("test2");
        given(mapper.selProfileUserInfo(p2)).willReturn(result2);

        UserInfoGetRes res2 = service.profileUserInfo(p2);
        assertEquals(result2, res2);
    }

    @Test
    void patchProfilePic() throws IOException{
        UserProfilePatchReq p1 = new UserProfilePatchReq();
        p1.setSignedUserId(100);
        MultipartFile m1 = new MockMultipartFile("pic", // 유저 컨트롤러에 변수명이랑 맞추기
                "b797a030-37d9-46b8-a423-30ec777791a2.png",
                "image/png",
                new FileInputStream(("D:/Students/MJ/download/greengram_tdd_test/user/2/b797a030-37d9-46b8-a423-30ec777791a2.png"))); // 절대경로
        p1.setPic(m1);
        given(mapper.updProfilePic(p1)).willReturn(1);
        String result1 = service.patchProfilePic(p1);

        assertEquals(result1,p1.getPicName());

        File savedFile1 = new File(uploadPath, String.format("user/%d/%s", p1.getSignedUserId(),p1.getPicName()));
        assertTrue(savedFile1.exists(), "파일이 만들어지지 않음");  // 연동이 됐다면 exists 햇을 때 트루

        // ===============================================================================

        final String ORIGIN_FILE_PATH = String.format("%s/test/%s", uploadPath,"1.jpg");
        long signedUserID2 = 500;
        String midPath2 = String.format("%suser/%s", uploadPath,signedUserID2);

        File originFile = new File(ORIGIN_FILE_PATH);
        File copyFile = new File(midPath2,"1.jpg");

        File dic2 = new File(midPath2);
        if(!dic2.exists()) {
            dic2.mkdirs();

            Files.copy(originFile.toPath(),copyFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } else {
            File[] fileList = dic2.listFiles();
            if(fileList.length == 0) {
                Files.copy(originFile.toPath(), copyFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }

        UserProfilePatchReq p2 = new UserProfilePatchReq();
        p2.setSignedUserId(signedUserID2);

        // ===============================================================================

        final String ORIGIN_FILE_PATH3 = String.format("%s/test/%s", uploadPath,"2.png");
        long signedUserID3 = 501;
        String midPath3 = String.format("%suser/%s", uploadPath,signedUserID3);

        File originFile3 = new File(ORIGIN_FILE_PATH3);
        File copyFile3 = new File(midPath3,"2.png");

        File dic3 = new File(midPath3);
        if(dic3.exists()) {
            customFileUtils.deleteFolder(midPath3);
        }
        customFileUtils.makeFolders("user/" + signedUserID3);
        Files.copy(originFile3.toPath(), copyFile3.toPath(), StandardCopyOption.REPLACE_EXISTING);

        UserProfilePatchReq p3 = new UserProfilePatchReq();
        p3.setSignedUserId(signedUserID3);
        MultipartFile m3 = new MockMultipartFile(
                "pic", "2.png", "image/png",
                new FileInputStream(String.format("%s/test/2.png", uploadPath))
        );
        p3.setPic(m3);
        String fileName3 = service.patchProfilePic(p3);
        assertNotNull(fileName3, "파일명이 널임");

        // =============================

        File copy3_2 = new File(midPath3,"1.jpg");
        String path3_2 = "user/" + signedUserID3;
        if(dic3.exists()) {
            customFileUtils.deleteFolder(path3_2);
        }
        customFileUtils.makeFolders(path3_2);
        Files.copy(originFile.toPath(), copy3_2.toPath(), StandardCopyOption.REPLACE_EXISTING);

        UserProfilePatchReq p4 = new UserProfilePatchReq();
        p4.setSignedUserId(signedUserID3);
        MultipartFile m4 = new MockMultipartFile(
                "pic", "1.jpg", "image/jpg",
                new FileInputStream(String.format("%s/test/1.jpg", uploadPath))
        );
        p4.setPic(m4);
        String fileNm4 = service.patchProfilePic(p4);
        assertEquals(fileNm4, p4.getPicName(), "파일명이 다름");

    }
}