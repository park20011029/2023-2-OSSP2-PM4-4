package project.manager.server.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.manager.server.util.S3UploadUtil;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping(value = "/upload")
@RestController
public class FileTestController {

        private final S3UploadUtil s3UploadUtil;

        @PostMapping(name = "S3 파일 업로드", value = "/file")
        public String fileUpload(@RequestParam("files") MultipartFile multipartFile) throws IOException {

            return s3UploadUtil.upload(multipartFile, "pm4/");
        }

        @DeleteMapping(name = "S3 파일 삭제", value = "/file")
        public String fileDelete(@RequestParam("file_url") String path) {

            return s3UploadUtil.delete(path);
        }
    }

