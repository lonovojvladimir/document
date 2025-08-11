package com.lonovojvladimir.document.minio.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class MinioService {

    @Value("${services.s3.bucketName}")
    private String bucketName;
    private final S3Client s3Client;

    public void uploadDocx(MultipartFile file) throws Exception {
        String key = file.getOriginalFilename();
        Path tempFile = Files.createTempFile("upload-", ".docx");
        file.transferTo(tempFile.toFile());
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(URLConnection.guessContentTypeFromName(key))
                .build();

        s3Client.putObject(putObjectRequest, tempFile);
        Files.deleteIfExists(tempFile);
    }

    public Resource downloadFile(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getObjectRequest);
        InputStreamResource resource = new InputStreamResource(s3Object);
        return resource;
    }

    public void deleteFile(String fileName) throws Exception {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

}
