package com.inhatc.spring.capstone.file.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {
    private static String uploadPath; //실제 업로드 장소
    private static String resourceHandlerURL; // 외부에서 이미지로 접근하는 경로
	
	@Value(value = "${uploadPath}")
	public void setUploadPath(String uploadPath) {
		FileService.uploadPath = uploadPath.replace("file:///", "");
	}
    
	@Value(value = "${resourceHandlerUrl}")
	public void setResourceHandlerURL(String resourceHandlerURL) {
		FileService.resourceHandlerURL = resourceHandlerURL;
	}

	public String uploadFile(String uploadPath, String oriFileName, byte[] fileData) throws IOException {
		UUID uuid = UUID.randomUUID();
        String extension = oriFileName.substring(oriFileName.lastIndexOf(".")); // jpg, gif 등등
        String savedFileName = uuid.toString() + extension; 
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
	}
	
	public void deleteFile(String filePath) {
		File deleteFile = new File(filePath);
		
		if(deleteFile.exists()) {
			deleteFile.delete();
			log.info("파일을 삭제했습니다.");
		}
		else {
			log.info("파일이 존재하지 않습니다.");
		}
	}
	
	
	public void moveFile(String sourcePath, String targetPath) throws IOException {
		File src = new File(sourcePath);
		File target = new File(targetPath);
		FileUtils.moveFile(src, target);
	}
	
	public static String resourcePathToSavedPath(String resourcePath) {
		return resourcePath.replace(resourceHandlerURL, uploadPath);
	}
	
	public static String savedPathToResourcePath(String savedPath) {
		return savedPath.replace(uploadPath, resourceHandlerURL);
	}
}
