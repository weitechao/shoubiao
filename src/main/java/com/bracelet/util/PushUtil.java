package com.bracelet.util;

import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;
import com.aliyuncs.utils.ParameterHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.bracelet.service.IPushlogService;

import java.util.Date;

public class PushUtil {

	private static Logger logger = LoggerFactory.getLogger(PushUtil.class);
	private static IClientProfile profile = DefaultProfile.getProfile(
			"cn-hangzhou", Utils.accessKeyId, Utils.accessKeySecret);
	private static Long appKey = 24610856L;

	@Autowired
	private static IPushlogService pushlogService;

	public static void push(String targetValue, String title, String content,
			String notifyContent) {
		if (targetValue != null && !"".equals(targetValue)) {
			pushMessage(targetValue, title, content, 0);
			pushNotify(targetValue, title, content, notifyContent, 0);
		}

	}

	public static PushResponse pushMessage(String targetValue, String title,
			String content, int second) {
		logger.info("开始推送[消息][targetValue:" + targetValue + ",title:" + title
				+ ",content:" + content + ",second:" + second + "]");
		PushResponse result = null;
		try {
			DefaultAcsClient client = new DefaultAcsClient(profile);
			PushRequest pushRequest = new PushRequest();
			pushRequest.setProtocol(ProtocolType.HTTP);
			pushRequest.setMethod(MethodType.POST);
			pushRequest.setAppKey(appKey);
			pushRequest.setTarget("ACCOUNT");
			pushRequest.setTargetValue(targetValue);
			pushRequest.setPushType("MESSAGE");
			pushRequest.setDeviceType("ALL");
			pushRequest.setTitle(title);
			pushRequest.setBody(content);
			// 推送控制
			Date pushDate = new Date(System.currentTimeMillis() + second * 1000);
			String pushTime = ParameterHelper.getISO8601Time(pushDate);
			pushRequest.setPushTime(pushTime);
			pushRequest.setStoreOffline(true);
			result = client.getAcsResponse(pushRequest);
			logger.info("收到推送[消息]结果[targetValue:" + targetValue + ",title:"
					+ title + ",content:" + content + ",second:" + second
					+ "] -> RequestId: " + result.getRequestId()
					+ ", MessageId:" + result.getMessageId());
		} catch (Exception e) {
			logger.info("推送发送错误:", e);
		}
		return result;
	}

	public static PushResponse pushNotify(String targetValue, String title,
			String content, String notifyContent, int second) {
		logger.info("开始推送[通知][targetValue:" + targetValue + ",title:" + title
				+ ",content:" + content + ",second:" + second + "]");
		PushResponse result = null;
		try {
			DefaultAcsClient client = new DefaultAcsClient(profile);
			PushRequest pushRequest = new PushRequest();
			pushRequest.setProtocol(ProtocolType.HTTP);
			// 内容较大的请求，使用POST请求
			pushRequest.setMethod(MethodType.POST);
			pushRequest.setAppKey(appKey);
			pushRequest.setTarget("ACCOUNT");
			pushRequest.setTargetValue(targetValue);
			pushRequest.setPushType("NOTICE");
			pushRequest.setDeviceType("ANDROID");
			// TODO iOS
			// pushRequest.setDeviceType("ALL");
			pushRequest.setTitle(title);
			pushRequest.setBody(notifyContent);
			// 推送配置: iOS
			// TODO iOS
			// pushRequest.setIOSBadge(1);
			// pushRequest.setIOSMutableContent(true);
			// pushRequest.setIOSApnsEnv("PRODUCT");
			// pushRequest.setIOSRemind(true);
			// pushRequest.setIOSRemindBody(title);
			// pushRequest.setIOSExtParameters(content);

			// 推送配置: Android
			pushRequest.setAndroidNotificationBarType(1);
			pushRequest.setAndroidNotificationBarPriority(1);
			pushRequest.setAndroidOpenType("APPLICATION");
			pushRequest.setAndroidExtParameters(content);
			// 推送控制
			Date pushDate = new Date(System.currentTimeMillis() + second * 1000);
			String pushTime = ParameterHelper.getISO8601Time(pushDate);
			pushRequest.setPushTime(pushTime);
			pushRequest.setStoreOffline(true);
			result = client.getAcsResponse(pushRequest);
			logger.info("收到推送[通知]结果[targetValue:" + targetValue + ",title:"
					+ title + ",content:" + content + ",second:" + second
					+ "] -> RequestId: " + result.getRequestId()
					+ ", MessageId:" + result.getMessageId());
		} catch (Exception e) {
			logger.info("推送发送错误:", e);
		}
		return result;
	}

}
