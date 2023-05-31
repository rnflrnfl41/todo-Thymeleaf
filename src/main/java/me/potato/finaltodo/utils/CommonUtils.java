package me.potato.finaltodo.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CommonUtils {

    public final  static String uploadPath = "C:\\download\\files\\";

    public static String getUUID( ) {
        String uuid =  UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "").substring(0, 16);
        return uuid;
    }

    public static Map<String,String> uploadFile(MultipartFile file) throws IOException {

        Map<String,String> fileParam = new HashMap<>();

        if(file!=null && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();

            String ext = originalFileName.substring(originalFileName.lastIndexOf(".")+1);

            String randomName = getUUID();

            String storedFileName = randomName+"."+ext;

            String fullPath = uploadPath + storedFileName;

            File newFile = new File(fullPath);

            //저장 경로가 없다면 전체 경로 생성
            if(!newFile.getParentFile().exists()) {
                newFile.getParentFile().mkdirs();
            }

            newFile.createNewFile();
            file.transferTo(newFile);

            fileParam.put("originalFileName",originalFileName);
            fileParam.put("storedFileName",storedFileName);
        }

        return fileParam;
    }

}
