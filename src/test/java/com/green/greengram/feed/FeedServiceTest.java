package com.green.greengram.feed;

import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.feed.model.*;
import com.green.greengram.feed.model_copy.FeedPostReq;
import com.green.greengram.feed.model_copy.FeedPostRes;
import com.green.greengram.feedcomment.model.GetCommentsRes;
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

import javax.imageio.IIOException;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import({FeedServiceImpl.class})
@TestPropertySource(
        properties = {
                "file.dir=D:/Students/MJ/download/greengram_tdd_test/"
        }
)
class FeedServiceTest {

    @Value("${file.dir}") String uploadPath;
    @Autowired FeedService service;
    @MockBean FeedMapper mapper;
    @MockBean CustomFileUtils customFileUtils;
    // RequiredArgsConstructor + final > 주소값을 넣는 생성자를 만들어줌
    // 객제화 할 떄 넣을 주소값이 없음 > MockBean으로 만들어줌

    @Test
    void postFeed() throws Exception {
        PostFeedReq p = new PostFeedReq();
        p.setFeedId(10);

        List<MultipartFile> pics = new ArrayList<>();
        MultipartFile fm1 = new MockMultipartFile(
                "pic", "1.jpg","image/jpg" ,
                new FileInputStream(String.format("%stest/1.jpg",uploadPath))
        );
        MultipartFile fm2 = new MockMultipartFile(
                "pic", "2.png","image/png" ,
                new FileInputStream(String.format("%stest/2.png",uploadPath))
        );
        pics.add(fm1);
        pics.add(fm2);

        List<String> fileNm = new ArrayList<>();
        fileNm.add("a1b1.jpg");
        fileNm.add("a2b2.png");
        given(customFileUtils.makeRandomFileName(fm1)).willReturn(fileNm.get(0));
        given(customFileUtils.makeRandomFileName(fm2)).willReturn(fileNm.get(1));

        //given 매퍼

        PostFeedRes res = service.postFeed(pics, p);
        String path = String.format("feed/%s", p.getFeedId());
        verify(customFileUtils).makeFolders(path);

        PicsDto dto = PicsDto.builder()
                .feedId(p.getFeedId())
                .pics(fileNm)
                .build();

        for(int i = 0; i <pics.size(); i++) {
            MultipartFile f = pics.get(i);
            verify(customFileUtils).makeRandomFileName(f);
            verify(customFileUtils).transferTo(f,String.format("%s/%s",path,fileNm.get(i)));
        }
        verify(mapper).insPics(dto);

        PostFeedRes postres = PostFeedRes.builder()
                .feedId(dto.getFeedId())
                .pics(dto.getPics())
                .build();

        assertEquals(postres,res,"리턴 다름");
    }

    @Test
    void getFeed() {
        GetFeedReq p1 = new GetFeedReq(1,10,1, null);
        GetFeedRes res1 = new GetFeedRes();
        res1.setContents("글1");
        res1.setFeedId(1);
        GetFeedRes res2 = new GetFeedRes();
        res2.setContents("글2");
        res2.setFeedId(2);
        List<GetFeedRes> result = new ArrayList<>();
        result.add(res1);
        result.add(res2);

        given(mapper.getFeed(p1)).willReturn(result);

        GetCommentsRes commentres1 = new GetCommentsRes();
        commentres1.setComment("글1 댓글1");

        List<GetCommentsRes> commentsResList = new ArrayList<>();
        commentsResList.add(commentres1);

        given(mapper.getComments4(res1.getFeedId())).willReturn(commentsResList);

        List<String> pics = new ArrayList<>();
        pics.add("사진1");
        pics.add("사진2");
        pics.add("사진3");
        given(mapper.getPics(res2.getFeedId())).willReturn(pics);

        List<GetFeedRes> result1 = service.getFeed(p1);
        verify(mapper).getFeed(p1);

        verify(mapper).getComments4(res1.getFeedId());
        verify(mapper).getComments4(res2.getFeedId());

        verify(mapper).getPics(res1.getFeedId());
        verify(mapper).getPics(res2.getFeedId());

        for(GetFeedRes item : result) {
            verify(mapper).getPics(item.getFeedId());
            verify(mapper).getComments4(item.getFeedId());
        }
        assertEquals(result.size(), result1.size(), "리턴값이 다름");
        assertEquals(pics.size(), result1.get(1).getPics().size(), "이미지 다름");
    }
}