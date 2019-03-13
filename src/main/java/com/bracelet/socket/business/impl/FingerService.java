package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.FingerDto;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.MemberInfo;
import com.bracelet.entity.NoticeInfo;
import com.bracelet.entity.UserInfo;
import com.bracelet.exception.BizException;
import com.bracelet.util.AndroidPushUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.SmsUtil;
import com.bracelet.service.IFingerService;
import com.bracelet.service.IMemService;
import com.bracelet.service.IOpenDoorService;
import com.bracelet.service.IPushlogService;
import com.bracelet.service.ISmslogService;
import com.bracelet.service.ITokenInfoService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.socket.business.IService;
import com.taobao.api.ApiException;

@Component("fingerService")
public class FingerService implements IService {
	@Autowired
	IOpenDoorService opendoorService;
	@Autowired
	IFingerService fingerService;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	ISmslogService smslogService;
	@Autowired
	ITokenInfoService tokenInfoService;
	@Autowired
	IPushlogService pushlogService;

	@Autowired
	IMemService memService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public SocketBaseDto process(JSONObject jsonObject, Channel channel) {
		logger.info("===报警指纹开锁记录：" + jsonObject.toJSONString());
		JSONObject jsonObject2 = (JSONObject) jsonObject.get("data");
		if (jsonObject2 == null) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		Long userid = jsonObject2.getLong("userid");
		int isadmin = jsonObject2.getInteger("isadmin");

		String no = jsonObject.getString("no");
		String imei = jsonObject.getString("imei");
		  long timestamp = jsonObject.getLongValue("timestamp");
		String name = null;
		String nickName = "";
		// Integer register = jsonObject2.getInteger("register");// 0未注册 1注册
		UserInfo userinfo = userInfoService.getUserInfoById(userid);
		String openName ="";
		if (userinfo != null) {
			name = userinfo.getNickname();
			nickName = userinfo.getNickname();
			openName = nickName;
		}
		
	//	if(isadmin == 1){
		if(nickName !=null &&!"".equals(nickName)){
			opendoorService.insert(2, userid, 4, 2, imei, openName,new Timestamp(timestamp * 1000));
		}else{
			opendoorService.insert(2, userid, 4, 2, imei, userinfo.getUsername(),new Timestamp(timestamp * 1000));
		}
		/*}else if(isadmin == 0){
			MemberInfo member = memService.getMemberInfo(userinfo.getUsername(), imei);
			opendoorService.insert(2, userid, 4, 2, imei, member.getName());
		}*/
		
		try {
			BindDevice binde = userInfoService.getBindInfoByImeiAndStatus(imei,1);

			name = binde.getName();

			List<BindDevice> list = userInfoService.getBindInfoByImei(imei);
			if (list.size() > 0) {
				for (BindDevice info : list) {
					Long userId = info.getUser_id();

					UserInfo userinfoo = userInfoService
							.getUserInfoById(userId);

					if (userinfo != null && userId != userid) {
						String tel = userinfoo.getUsername();

						FingerDto sosDto = new FingerDto();
						sosDto.setName(name);
						sosDto.setImei(imei);
						sosDto.setTimestamp(System.currentTimeMillis());
						String target = tokenInfoService
								.getTokenByUserId(userId);

						String title = "指纹报警";
						String content = JSON.toJSONString(sosDto);
						String notifyContent = "您绑定的门锁" + name;
					//	if (isadmin == 1) {
						if(userinfo.getNickname() != null && !"".equals(userinfo.getNickname())){
							notifyContent = notifyContent + "被"
									+ userinfo.getNickname() + "使用报警指纹打开,请知悉!";
						}else{
							notifyContent = notifyContent + "被手机号为"
									+ userinfo.getUsername()+ "使用报警指纹打开,请知悉!";
						}
						
							

							String msg = SmsUtil.fingerSosSendMsg(
									name, userinfo.getNickname(), tel);

							smslogService.insert("报警指纹", tel, "SMS_125735073",
									"imei:" + imei + "-tel:" + tel, 0, msg);

					/*	} else if (isadmin == 0) {
							MemberInfo member = memService.getMemberInfo(
									userinfo.getUsername(), imei);
							if (member != null) {
								notifyContent = notifyContent + "被"
										+ member.getName() + "使用报警指纹打开,请知悉!";
							

								String msg = SmsUtil.fingerSosSendMsg(
										name, member.getName(), tel);

								smslogService.insert("报警指纹", tel,
										"SMS_125735073", "imei:" + imei
												+ "-tel:" + tel, 0, msg);

							} else {
								notifyContent = notifyContent + "被手机号为"
										+ userinfo.getUsername()
										+ "使用报警指纹打开,请知悉!";
							

								String msg = SmsUtil.fingerSosSendMsg(
										name,  userinfo.getUsername() , tel);

								smslogService.insert("报警指纹", tel,
										"SMS_125735073", "imei:" + imei
												+ "-tel:" + tel, 0, msg);
							}
						}*/

						// xx 使用报警指纹开锁，请注意！
						NoticeInfo vinfo = userInfoService.getNoticeSet(userId);
						if(vinfo == null || vinfo.getMemberunlockswitch() == 1 ){
							AndroidPushUtil.push(target, title, content, notifyContent);
							// save push log
							this.pushlogService.insert(userId, imei, 0, target,title, content);
						}

					}
				}
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * List<FingerInfo> pwdInfoList = fingerService
		 * .getFingerInfoByFingerId(userid, imei); if (pwdInfoList !=
		 * null&&pwdInfoList.size()>0) { //for (FingerInfo info : pwdInfoList) {
		 * Long userId = pwdInfoList.get(0).getUser_id(); name =
		 * pwdInfoList.get(0).getName(); userinfo =
		 * userInfoService.getUserInfoById(userId); if (userinfo != null) {
		 * String tel = userinfo.getUsername(); String msg =
		 * SmsUtil.useFingerprintOpenDoorSendMsg( imei, name, tel);
		 * smslogService.insert("指纹报警", tel, "SMS_91985064", "imei:" + imei +
		 * "-name:" + name + "-tel:" + tel, 0, msg);
		 * 
		 * FingerDto sosDto = new FingerDto(); sosDto.setName(name);
		 * sosDto.setImei(imei); sosDto.setTimestamp(new Date().getTime());
		 * String target = tokenInfoService .getTokenByUserId(userid); String
		 * title = "指纹报警"; String content = JSON.toJSONString(sosDto); String
		 * notifyContent = "收到指纹报警，请点击查看!"; PushUtil.push(target, title,
		 * content, notifyContent); // save push log
		 * this.pushlogService.insert(userId, imei, 0, target, title, content);
		 * } //} }
		 */

		SocketBaseDto dto = new SocketBaseDto();
		dto.setType(jsonObject.getIntValue("type"));
		dto.setNo(no);
		dto.setTimestamp(System.currentTimeMillis());
		dto.setStatus(0);

		return dto;

	}

	@Override
	public String process(String jsonInfo, Channel incoming) {
		// TODO Auto-generated method stub
		return null;
	}
}
