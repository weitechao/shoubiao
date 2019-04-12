package com.bracelet;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;


import org.springframework.mock.web.MockMultipartFile;
import org.apache.http.entity.ContentType;


import com.aliyun.oss.OSSClient;

public class AliOss {
	
	
	/*存储空间  Bucket  	  20190411weicreate
		对象/文件 	Object
		地域  Region   oss-cn-zhangjiakou
	访问域名   	oss-cn-zhangjiakou.aliyuncs.com      oss-cn-zhangjiakou-internal.aliyuncs.com
	访问密钥  LTAIbB6A0M192V67   0Sch7htDMfWXba1BwYbGMrWSfQDerY
		
		*/
	
	
	// Endpoint以杭州为例，其它Region请按实际情况填写。
	static final String endpoint = "http://oss-cn-zhangjiakou.aliyuncs.com";
	// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
	static final String accessKeyId = "LTAIbB6A0M192V67";
	static final String accessKeySecret = "0Sch7htDMfWXba1BwYbGMrWSfQDerY";
	static final String bucketName = "20190411weicreate";
		
		
	// 上传本地固定地址的资源到oss
		public static String picOssByLocalAddress(String localAddress,String filename) throws Exception {
				// 创建OSSClient实例
				OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
				// 上传
				//long time = new Date().getTime();
				
				File pdfFile = new File(localAddress);
				FileInputStream fileInputStream = new FileInputStream(pdfFile);
				MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(),ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
				ossClient.putObject(bucketName, filename, new ByteArrayInputStream(multipartFile.getBytes()));
				// 关闭client
				ossClient.shutdown();
				Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
				String url = ossClient.generatePresignedUrl(bucketName, filename, expiration).toString();
				return url;

		}
		
		//上传byte 到oss
		public static String picOssByByte(byte[] content,String filename) throws Exception {
			// 创建OSSClient实例
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			// 上传
			//long time = new Date().getTime();
			
			ossClient.putObject(bucketName, filename, new ByteArrayInputStream(content));
			// 关闭client
			ossClient.shutdown();
			Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
			String url = ossClient.generatePresignedUrl(bucketName, filename, expiration).toString();
			return url;

	}
		
		public static void main(String[] args) throws IOException {
			/*try {
			File pdfFile = new File("F://testpng/abc.png");
			FileInputStream fileInputStream = new FileInputStream(pdfFile);
			MultipartFile multipartFile = new MockMultipartFile(pdfFile.getName(), pdfFile.getName(),ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
			String url =picOSS(multipartFile,"test.png");
			System.out.println(url);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			// 上传Byte数组。
			byte[] content = "Hello OSS".getBytes();
			try {
				String url = picOssByByte(content,"1.txt");
				System.out.println(url);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}

}
