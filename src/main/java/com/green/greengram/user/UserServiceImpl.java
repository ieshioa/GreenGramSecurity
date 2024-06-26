package com.green.greengram.user;

import com.green.greengram.common.AppProperties;
import com.green.greengram.common.CookieUtils;
import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.security.AuthenticationFacade;
import com.green.greengram.security.jwt.JwtTokenProviderV2;
import com.green.greengram.security.MyUser;
import com.green.greengram.security.MyUserDetails;
import com.green.greengram.user.model.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProviderV2 jwtTokenProvider;
    private final CookieUtils cookieUtils;
    private final AuthenticationFacade authenticationFacade;
    private final AppProperties appProperties;

    // SecurityContextHolder > Context > Authentication (UserNamePasswordAuthenticationToken) > MyUserDetails > MyUser

    @Transactional
    public int signUp(MultipartFile pic, PostUserReq p) {
        String password = passwordEncoder.encode(p.getUpw());  // 규격화를 시킨 비크립트를 사용함
                                                    // 다른 라이브러리를 사용하려고 할 때 여기서는 수정할 필요 없음
                                                    // SecurityConfiguration 에서 패스워드 인코더만 수정하면 됨
//        String password = BCrypt.hashpw(p.getUpw(),BCrypt.gensalt());
        p.setUpw(password);
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

    public SignInRes signIn (SignInReq p, HttpServletResponse res) {
        User user = mapper.getUser(p.getUid());
        if (user == null) {
            throw new RuntimeException("아이디가 없습니다");
        }
        if (!BCrypt.checkpw(p.getUpw(),user.getUpw())) {
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }
//        UserDetails userDetails = new MyUserDetails(user.getUserId(), "ROLE_USER");
        MyUser myUser = MyUser.builder()
                .userId(user.getUserId())
                .role("ROLE_USER")
                .build();
        String accessToken = jwtTokenProvider.generateAccessToken(myUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(myUser);

        // refreshToken 은 보안쿠키를 이용해서 처리
        // 쿠기로 넘겨주면 프론트가 훨씬 편함
        // 근데 보안이 약함 > 그래서 보안쿠키 이용
        int refreshTokenMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge();
        cookieUtils.deleteCookie(res, "refresh-token"); // 기존의 쿠키가 있을 수 있으니 삭제하고 설정함
        cookieUtils.setCookie(res, "refresh-token", refreshToken, refreshTokenMaxAge);


        return SignInRes.builder().userId(user.getUserId()).nm(user.getNm())
                .pic(user.getPic())
                .accessToken(accessToken)
                .build();
    }

    public Map getAccessToken(HttpServletRequest req) {
        Cookie cookie = cookieUtils.getCookie(req,"refresh-token");
        if(cookie == null) {    // 리프레시토큰 값이 쿠키에 존재하니?
            throw new RuntimeException();
        }
        String refreshToken = cookie.getValue();
        if(!jwtTokenProvider.isValidateToken(refreshToken)) { // 만료가 되었니?
            throw new RuntimeException();
        }

        UserDetails auth = jwtTokenProvider.getUserDetailsFromToken(refreshToken);
        MyUser myUser = ((MyUserDetails)auth).getMyUser();

        String accessToken = jwtTokenProvider.generateAccessToken(myUser);

        Map map = new HashMap();
        map.put("accessToken", accessToken);
        return map;

    }

    public UserInfoGetRes profileUserInfo(UserInfoGetReq p) {
        p.setSignedUserId(authenticationFacade.getLoginUserId());
        return mapper.selProfileUserInfo(p);
    }

    @Transactional
    public String patchProfilePic(UserProfilePatchReq p) {
        p.setSignedUserId(authenticationFacade.getLoginUserId());

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
