package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.WatchUploadPhotoInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IUploadPhotoService;
import com.bracelet.service.IVoltageService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

/**
 * 照片上传 终端发送: [YW*YYYYYYYYYY*NNNN*LEN*TPBK，source，文件名，当前包，总包个数，位置数据(见附录一)]
 * 实例:[YW*8800000015*0001*000E*TPBK, 15986634630，2012122512556.jpg,1,6,] 平台回复:
 * [YW*YYYYYYYYYY*NNNN*LEN*TPCF, 文件名，当前包，总包个数，1] 实例:
 * [YW*8800000015*0001*0002*TPCF,2012122512556.jpg,1,6,1]
 * 说明：source为NULL或者号码，数据有可能为空
 * 
 */
@Component("uploadPhoto")
public class UploadPhoto extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IUploadPhotoService iUploadPhotoService;
	@Autowired
	ILocationService locationService;

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto, JSONObject jsonObject, Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo, Channel channel) {

		logger.info("设备照片上传=" + jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		// String no = shuzu[2];// 流水号
		String info = shuzu[4];
		String[] infoshuzuMsg = info.split(",");
		String source = infoshuzuMsg[1];// 文件-来源 如果是设备就填0，如果是App就填发的人的手机号码
		String photoName = infoshuzuMsg[2];// 文件名
		int thisNumber = Integer.valueOf(infoshuzuMsg[3]);// 当前包 如果是0就是定位
															// 1就是照片数据
		int allNumber = Integer.valueOf(infoshuzuMsg[4]);// 总包个数
		String dataInfo = "ud," + infoshuzuMsg[5];// thisNumber0位置数据--其他照片数据

		String[] infoshuzu = dataInfo.split(",");
		String locationis = infoshuzu[3];// A定位 V不定位
		String time = infoshuzu[1] + "-" + infoshuzu[2];
		if ("A".equals(locationis)) {
			String lat = infoshuzu[4];// 纬度
			String lon = infoshuzu[6]; // 经度
			String status = infoshuzu[16];
			String energy = infoshuzu[13];
			logger.info("imei=" + imei + ",lat=" + lat + ",lon=" + lon + ",time=" + time + ",status=" + status
					+ ",energy=" + energy);
			locationService.insertUdInfo(imei, 1, lat, lon, status, time, 4);
		} else if ("V".equals(locationis)) {
			Integer wifiCount = Integer.valueOf(infoshuzu[39]);
			if (wifiCount == 0) {
				/*
				 * UD,11102018,142013,V,0.000000,N,
				 * 0.000000,E,0.00,0.0,0.0,0,100,100,0,0:0,00000000,6,1, 460,0,
				 * 19 10173,4934,49, 10173,4263,34, 10173,4941,34,
				 * 10173,4931,31, 10173,4943,27, 10173,4582,27, 0
				 */

				String aab = "460,0,";
				StringBuffer sbb = new StringBuffer();

				// if(Integer.valueOf(infoshuzu[23])>30){
				sbb.append("bts=");
				sbb.append(aab);
				sbb.append(infoshuzu[21]).append(",").append(infoshuzu[22]).append(",")
						.append((Integer.valueOf(infoshuzu[23]) * 2 - 113) + "");
				// }
				StringBuffer sb = new StringBuffer();
				sb.append("&nearbts=");
				if (Integer.valueOf(infoshuzu[26]) > 30) {
					sb.append(aab);
					sb.append(infoshuzu[24]).append(",").append(infoshuzu[25]).append(",")
							.append((Integer.valueOf(infoshuzu[26]) * 2 - 113) + "");
				}
				if (Integer.valueOf(infoshuzu[29]) > 30) {
					sb.append("|");
					sb.append(aab);
					sb.append(infoshuzu[27]).append(",").append(infoshuzu[28]).append(",")
							.append((Integer.valueOf(infoshuzu[29]) * 2 - 113) + "");
				}
				if (Integer.valueOf(infoshuzu[32]) > 30) {
					sb.append("|");
					sb.append(aab);
					sb.append(infoshuzu[30]).append(",").append(infoshuzu[31]).append(",")
							.append((Integer.valueOf(infoshuzu[32]) * 2 - 113) + "");
				}
				if (Integer.valueOf(infoshuzu[35]) > 30) {
					sb.append("|");
					sb.append(aab);
					sb.append(infoshuzu[33]).append(",").append(infoshuzu[34]).append(",")
							.append((Integer.valueOf(infoshuzu[35]) * 2 - 113) + "");
				}
				if (Integer.valueOf(infoshuzu[38]) > 30) {
					sb.append("|");
					sb.append(aab);
					sb.append(infoshuzu[36]).append(",").append(infoshuzu[37]).append(",")
							.append((Integer.valueOf(infoshuzu[38]) * 2 - 113) + "");
				}

				String url = "http://apilocate.amap.com/position?key=" + Utils.SSRH_LOCATION_KEY
						+ "&output=json&accesstype=0&imsi=" + imei + "&cdma=0&tel=13537596170&network=GSM&"
						+ sbb.toString() + sb.toString();
				logger.info(url);
				String responseJsonString = HttpClientGet.urlReturnParams(url);

				if (responseJsonString != null) {
					JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonString);
					String status = responseJsonObject.getString("status");
					// String info = responseJsonObject.getString("info");
					if ("1".equals(status)) {
						JSONObject resultJsonObject = responseJsonObject.getJSONObject("result");
						String location = resultJsonObject.getString("location");

						String[] arr = location.split(",");
						if (arr.length == 2) {
							String lat = arr[1];
							String lon = arr[0];
							locationService.insertUdInfo(imei, 2, lat, lon, status, time, 4);
						}
					}
				}
			} else {
				String mmac = infoshuzu[41] + "," + infoshuzu[42] + "," + infoshuzu[40];
				String macs = "";
				if (wifiCount > 1) {
					for (int i = 1; i < wifiCount; i++) {
						if (i == 0) {
							mmac = infoshuzu[41 + i] + "," + infoshuzu[42 + i] + "," + infoshuzu[40 + i];
						} else {
							if (i > 1) {
								macs = macs + "|";
							}
							macs = macs + infoshuzu[41 + i] + "," + infoshuzu[42 + i] + "," + infoshuzu[40 + i];
						}
					}
				}
				String url = "http://apilocate.amap.com/position?key=" + Utils.SSRH_LOCATION_KEY
						+ "&output=json&accesstype=1&mmac=" + mmac + "&macs=" + macs;
				logger.info(url);
				String responseJsonString = HttpClientGet.urlReturnParams(url);
				if (responseJsonString != null) {
					JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonString);
					String status = responseJsonObject.getString("status");
					// String info = responseJsonObject.getString("info");
					if ("1".equals(status)) {
						JSONObject resultJsonObject = responseJsonObject.getJSONObject("result");
						String location = resultJsonObject.getString("location");

						String[] arr = location.split(",");
						if (arr.length == 2) {
							String lat = arr[1];
							String lon = arr[0];
							locationService.insertUdInfo(imei, 3, lat, lon, status, time, 4);
						}
					}
				}
			}
		}

		if (thisNumber != 0) {
			if (thisNumber == 1) {
				iUploadPhotoService.insertPhoto(imei, source, photoName, dataInfo);
			} else {
				WatchUploadPhotoInfo waUpInfo = iUploadPhotoService.getByPhotoNameAndImei(imei, photoName);
				if (waUpInfo != null) {
					iUploadPhotoService.updateById(waUpInfo.getId(), waUpInfo.getData() + dataInfo);
				}
			}
		}
		iUploadPhotoService.insert(imei, photoName, source, thisNumber, allNumber);

		// [YW*YYYYYYYYYY*NNNN*LEN*TPCF, 文件名，当前包，总包个数，1]
		String resp = "TPCF," + photoName + "," + thisNumber + "," + allNumber + ",1";
		StringBuffer sb = new StringBuffer("[YW*" + imei + "*NNNN*");
		sb.append(RadixUtil.changeRadix(resp));
		sb.append("*");
		sb.append(resp);
		sb.append("]");
		logger.info("设备拍照返回数据=" + sb.toString());
		return sb.toString();
	}

	/*
	 * @Override public SocketBaseDto process(JSONObject jsonObject, Channel
	 * channel) { logger.info("===系统心跳：" + jsonObject.toJSONString());
	 * SocketBaseDto dto = new SocketBaseDto();
	 * dto.setType(jsonObject.getIntValue("type"));
	 * dto.setNo(jsonObject.getString("no")); dto.setTimestamp(new
	 * Date().getTime()); dto.setStatus(0);
	 * 
	 * return dto; }
	 * 
	 * @Override public String process(String jsonInfo, Channel channel) {
	 * 
	 * String[] shuzu = jsonInfo.split("\\*"); String imei = shuzu[1];// 设备imei
	 * String no = shuzu[2];// 流水号 String info = shuzu[4];
	 * 
	 * String[] infoshuzu = info.split(","); String energy = infoshuzu[1];
	 * //还需要保存下电量
	 * 
	 * logger.info("链路保持imei:" + imei + "," + ",no:" + no + ",电量:" + energy);
	 * 
	 * 
	 * String resp = "[YW*8800000015*0001*0002*LK ,"+Utils.getTime()+"]"; return
	 * resp; }
	 */

}
