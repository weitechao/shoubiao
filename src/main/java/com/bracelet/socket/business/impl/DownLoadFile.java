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
import com.bracelet.entity.DownLoadFileInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IUploadPhotoService;
import com.bracelet.service.IVoltageService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

/**
 * 照片下载 终端发送: [YW*YYYYYYYYYY*NNNN*LEN*FILE,文件大小]
 * 实例:[YW*8800000015*0001*0004*FILE,4000] 文件大小为十进制值的字符串，单个包文件大小建议为1024-4000字节。
 * 平台回复: [YW*YYYYYYYYYY*NNNN*LEN*FILE, 文件名，当前包，总包个数，数据内容] 实例:
 * [YW*8800000015*0001*0002*FILE,2012122512556.jpg,1,1,数据内容]
 * 
 */
@Component("downloadFile")
public class DownLoadFile extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IUploadPhotoService iUploadPhotoService;

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {

		logger.info("设备下载文件=" + jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		String info = shuzu[4];

		List<DownLoadFileInfo> doinfo = iUploadPhotoService.getphotoInfo(imei);
		String resp = "";
		if (doinfo.size() > 0) {
			resp = "[YW*" + imei + "*NNNN*LEN*FILE,"
					+ doinfo.get(0).getPhoto_name() + ","
					+ doinfo.get(0).getThis_number() + ","
					+ doinfo.get(0).getAll_number() + ","
					+ doinfo.get(0).getSource() + "]";
		} else {
			resp = "[YW*" + imei + "*NNNN*LEN*FILE]";
		}
		return resp;
	}
}
