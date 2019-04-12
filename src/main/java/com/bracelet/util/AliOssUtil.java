package com.bracelet.util;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

public class AliOssUtil {
	private static Logger logger = LoggerFactory.getLogger(AliOssUtil.class);

	private static OSSClient ossClient = new OSSClient(Utils.endpointOSS, Utils.accessKeyIdOSS,
			Utils.accessKeySecretOSS);

	// 上传本地固定地址的资源到oss
	public static String picOssByLocalAddress(String localAddress, String filename) {
		// 创建OSSClient实例
		// OSSClient ossClient = new OSSClient(endpoint, accessKeyId,
		// accessKeySecret);
		// 上传
		// long time = new Date().getTime();
		String url="";
		try {
		File pdfFile = new File(localAddress);
		FileInputStream fileInputStream = new FileInputStream(pdfFile);
		MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(),
					ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
		ossClient.putObject(Utils.bucketNameOSS, filename, new ByteArrayInputStream(multipartFile.getBytes()));
		// 关闭client
		ossClient.shutdown();
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		 url = ossClient.generatePresignedUrl(Utils.bucketNameOSS, filename, expiration).toString();
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;

	}

	// 上传byte 到oss
	public static String picOssByByte(byte[] content, String filename) {
		// 创建OSSClient实例
		// OSSClient ossClient = new OSSClient(endpoint, accessKeyId,
		// accessKeySecret);
		// 上传
		// long time = new Date().getTime();

		ossClient.putObject(Utils.bucketNameOSS, filename, new ByteArrayInputStream(content));
		// 关闭client
		ossClient.shutdown();
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		String url = ossClient.generatePresignedUrl(Utils.bucketNameOSS, filename, expiration).toString();
		return url;

	}

}
