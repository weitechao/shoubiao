package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.InsertFriend;
import com.bracelet.entity.PwdMomentInfo;
import com.bracelet.entity.WatchDevice;
import com.bracelet.entity.WhiteListInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IDeviceService;
import com.bracelet.service.IVoltageService;
import com.bracelet.service.IinsertFriendService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

/**
 * 拉取好友列表
 * add_friend_info  这个表
 * 
 */
@Component("getFriendInfo")
public class GetFriendService extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IinsertFriendService insertFriendService;
	@Autowired
	IDeviceService ideviceService;

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {

		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		StringBuffer sb = new StringBuffer("[YW*"+imei+"*0001*0002*FDL,");
		List<InsertFriend> list = insertFriendService.getInfoList(imei, 2);
		//对方同意则更改状态为2，所有用2查询
		int count = list.size();
		if (count != 0) {
			sb.append(count);
			for (InsertFriend info : list) {
				sb.append(",");
				WatchDevice watchd = ideviceService.getDeviceInfo(info
						.getAdd_imei());
				if (watchd != null) {
					sb.append(info.getAdd_imei());
					sb.append(",");
					sb.append(watchd.getNickname());
					sb.append(",");
					sb.append(watchd.getPhone());
				}
			}
		} else {
			sb.append(count);
		}

		sb.append("]");

		return sb.toString();

	}

}
