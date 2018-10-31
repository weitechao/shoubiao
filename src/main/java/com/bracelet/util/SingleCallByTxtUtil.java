package com.bracelet.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsRequest;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/*
 * 文本转语音tts
 * 
 * https://help.aliyun.com/document_detail/55315.html?spm=5176.doc55070.2.2.5H2YAM
 * 帮助文档
 * */
public class SingleCallByTxtUtil {

	private static final String product = "Dyvmsapi";
	// 产品域名（接口地址固定，无需修改）
	private static final String domain = "dyvmsapi.aliyuncs.com";

	public static String push(String phone, String name) throws ClientException {
		// 设置访问超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		// 云通信产品-语音API服务产品名称（产品名固定，无需修改）

		// 初始化acsClient 暂时不支持多region
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", Utils.accessKeyIdOfBeidou,
				Utils.accessKeySecretOfBeidou);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		SingleCallByTtsRequest request = new SingleCallByTtsRequest();
		// 必填-被叫显号,可在语音控制台中找到所购买的显号
		request.setCalledShowNumber("01086393919");
		request.setTtsParam("{\"name\":\"" + name + "\"}");
		// 必填-被叫号码
		request.setCalledNumber(phone);
		// 必填-Tts模板ID
		request.setTtsCode("TTS_96825053");
		/*
		 * 您好,${name}发出求救，请及时查看相关信息。
		 */
		// 可选-外部扩展字段,此ID将在回执消息中带回给调用方
		request.setOutId("yourOutId");
		// hint 此处可能会抛出异常，注意catch
		SingleCallByTtsResponse singleCallByTtsResponse = acsClient.getAcsResponse(request);
		//msg = singleCallByTtsResponse.getCode();
		String msg = singleCallByTtsResponse.getMessage();
		return msg;
	}
}
