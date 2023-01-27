package com.son.facebookclone.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadHelper {

	private static final String root = "post-images/";
	
	public static void saveFile(Long id, MultipartFile file) throws IOException {
		
		Path uploadPath = Paths.get(root + id);
		
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		Files.copy(file.getInputStream(), uploadPath.resolve(file.getOriginalFilename()));
	}
	
	public static void updateFile(Long id, String oldFile, MultipartFile file) throws IOException {
		
		Path uploadPath = Paths.get(root + id);
		
		deleteFile(id, oldFile);
		
		Files.createDirectories(uploadPath);
		
		Files.copy(file.getInputStream(), uploadPath.resolve(file.getOriginalFilename()));
	}
	
	public static void deleteFile(Long id, String fileName) {
		Path dir = Paths.get(root + id);
		Path file = Paths.get(root + id + "/" + fileName);
		try {
			Files.deleteIfExists(file);
			FileSystemUtils.deleteRecursively(dir);
		} catch (IOException e) {
			
			e.getMessage();
		}
	}
}
