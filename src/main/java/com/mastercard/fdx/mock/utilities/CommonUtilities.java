package com.mastercard.fdx.mock.utilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.FileCopyUtils;

import java.nio.charset.StandardCharsets;
@Slf4j
public class CommonUtilities {

	private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	
	private static final String RESOURCE_FOLDER_PATH = "json/";
	public static String getFileContent(String fileName) {
		return getFileContent(RESOURCE_FOLDER_PATH, fileName);
	}

	public static String getFileContent(String resourceFolderPath, String fileName) {
        try {

			ClassPathResource cpr = new ClassPathResource(resourceFolderPath + fileName);
			byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            return new String(bdata, StandardCharsets.UTF_8);
		} catch (Exception e1) {
			log.error("Error while getting file", e1);
		}
		return "";
	}
	
	 public static String encrypthash(String password) {
	        return encoder.encode(password);
	  }

	public static String getStringOfStackTrace(StackTraceElement[] stackTrace) {
		var sb = new StringBuilder();
		for (StackTraceElement e : stackTrace) {
			sb.append(e.toString()).append("/=/");
		}
		return sb.toString();
	}
}
