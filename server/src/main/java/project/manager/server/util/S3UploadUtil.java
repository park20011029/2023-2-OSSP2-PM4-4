package project.manager.server.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3UploadUtil {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷

    // S3 파일 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        System.err.println(multipartFile);
        // MultipartFile -> File
        File convertFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("file convert error")); // 파일을 변환할 수 없으면 에러



        // S3에 저장할 파일명
        String fileName = dirName + "/" + UUID.randomUUID() + "_" + convertFile.getName();
        System.err.println(fileName);
        System.err.println(bucket);

        // S3에 파일 업로드
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, convertFile).withCannedAcl(CannedAccessControlList.PublicRead));

        String uploadImageUrl = amazonS3Client.getUrl(bucket, fileName).toString();

        // 로컬 파일 삭제
        convertFile.delete();

        return uploadImageUrl;
    }

    // S3 파일 삭제
    public String delete(String fileUrl) {
        String objectKey = extractObjectKey(fileUrl);
        amazonS3Client.deleteObject(bucket, "pm4/"+objectKey);
        return objectKey;
    }

    // 파일 convert 후 로컬에 업로드
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());

        System.err.println(convertFile);
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }


    //url을 받아
    private String extractObjectKey(String fileUrl) {
        try {

            URL url = new URL(fileUrl);


            String path = url.getPath();
            String decodedPath = URLDecoder.decode(path, "UTF-8");


            String[] pathSegments = decodedPath.split("/");
            if (pathSegments.length >= 2) {
                return String.join("/", Arrays.copyOfRange(pathSegments, 2, pathSegments.length)); // Skip the first two segments (empty and the bucket name)
            }

        } catch (MalformedURLException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null; // 추출 실패 시 null 반환
    }


}
