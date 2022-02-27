package upload_file.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import upload_file.demo.S3.service.S3Service;
import upload_file.demo.dtos.Item;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/photo")
@Slf4j
@RequiredArgsConstructor
public class PhotoController {

    @Value("${file.dir}")
    private String fileDir;
    private final S3Service s3Service;


    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFile(@RequestParam String itemName,
                           @RequestParam(value = "file") MultipartFile multipartFile , HttpServletRequest httpServletRequest) throws IOException {

        log.info("request={}",httpServletRequest);
        log.info("itemName={}",itemName);
        log.info("multipartFile={}", multipartFile);

        if(!multipartFile.isEmpty()){
            String fullpath = fileDir + multipartFile.getOriginalFilename();
            log.info("파일 저장 fullPath={}", fullpath);
            System.out.println(fullpath);
            multipartFile.transferTo(new File(fullpath));
        }
        return "upload-form";
    }

    @PostMapping("/tos3")
    public ResponseEntity<String> saveToS3(@RequestPart(value = "userPhotos") List<MultipartFile> userPhotos, @RequestPart Item item){


        System.out.println("11111111111111");
        System.out.println(userPhotos.get(0).getOriginalFilename());
        System.out.println(userPhotos.get(1).getOriginalFilename());
        System.out.println(userPhotos.get(2).getOriginalFilename());

        System.out.println(item.getItemName());

        try {
            System.out.println(userPhotos.get(0).toString());
            s3Service.upload(userPhotos.get(0));
        }catch(IOException e){
            System.out.println("예외 처리 해야함");
        }

        System.out.println("success");
        return ResponseEntity.ok().body("success");
    }
}



















