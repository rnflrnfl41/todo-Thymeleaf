package me.potato.finaltodo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    //로컬에 있는 이미지 저장장소를 url로 만들어 view에서 바로 볼수있게 매핑
    @GetMapping(value = "image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> userSearch(@PathVariable("imageName") String imageName) throws IOException {
        InputStream imageStream = new FileInputStream("C://download/files/" + imageName);
        byte[] imageByteArray = imageStream.readAllBytes();
        imageStream.close();
        return new ResponseEntity<>(imageByteArray, HttpStatus.OK);
    }
}
