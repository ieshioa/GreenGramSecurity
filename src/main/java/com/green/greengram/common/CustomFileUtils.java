package com.green.greengram.common;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Getter
@Component
@Slf4j
public class CustomFileUtils {
    public final String uploadPath;
    public CustomFileUtils (@Value("${file.dir}") String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String makeFolders(String path) {
        File file = new File(uploadPath,path);
        file.mkdirs();
        String a;
        try {
            a = file.getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String b = file.getPath();
        log.info("{}",a);
        log.info("{}",b);
        return file.getAbsolutePath();
    }

    public String makeRandomFileName(MultipartFile mf) {
        String fileName = mf.getOriginalFilename();
        String randomUUID = UUID.randomUUID().toString();
        int idx = fileName.lastIndexOf(".");
        String ext = fileName.substring(idx);
        return randomUUID+ext;
    }

    public void transferTo (MultipartFile mf, String target) throws Exception {
        File file = new File(uploadPath,target);
        mf.transferTo(file);
    }

    // 폴더 삭제  (파일이나 디렉토리가 하나라도 있으면 삭제가 안됨)
    public void deleteFolder(String path) {  // path: 상대주소 ("/user/3")
        File folder = new File(uploadPath, path);
     //   File folder = new File(path);
        if(folder.exists() && folder.isDirectory()) {  // 폴더가 존재 하면서 디렉토리라면
            File[] files = folder.listFiles();  // 파일 객체로 객체화해서 배열로 리턴해줌
            for(File f : files) {
                if(f.isDirectory()) {
                    deleteFolder(f.getAbsolutePath());  // 재귀호출 (깊이를 알 수 없을 때 쓰면 좋음)
                } else {
                    f.delete();
                }
            }
            folder.delete();
        }
    }
}
