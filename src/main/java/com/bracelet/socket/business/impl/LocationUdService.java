package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IVoltageService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.Utils;

@Service("locationUdService")
public class LocationUdService extends AbstractBizService {
	// public class LocationUdService implements IService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	ILocationService locationService;
	@Autowired
	IVoltageService voltageService;
	private static final String GPS_URL = "http://restapi.amap.com/v3/assistant/coordinate/convert";

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto, JSONObject jsonObject, Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo, Channel channel) {
		logger.info("位置数据上报:" + jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String cmd = shuzu[0];
		Integer locationStyle = 1;// 1正常2报警3天气4拍照
		if ("AL".equals(cmd)) {
			locationStyle = 2;
		}
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		String info = shuzu[4];
		logger.info("imei=" + imei + ",info=" + info + ",no=" + no);
		String[] infoshuzu = info.split(",");
		String locationis = infoshuzu[3];// A定位 V不定位
		String time = infoshuzu[1] + "-" + infoshuzu[2];
		String lat = infoshuzu[4];// 纬度
		String lng = infoshuzu[6]; // 经度
		String status = infoshuzu[16];
		String energy = infoshuzu[13];

		if ("A".equals(locationis)) {

			logger.info("imei=" + imei + ",lat=" + lat + ",lon=" + lng + ",time=" + time + ",status=" + status
					+ ",energy=" + energy);

			if (!"0.000000".equals(lat) && !"0.000000".equals(lng)) {
				String url = GPS_URL + "?key=" + Utils.SSRH_TIANQI_KEY + "&coordsys=gps&locations=" + lng + "," + lat;
				// http://restapi.amap.com/v3/assistant/coordinate/convert?key=c6a272fdecf96b343c31719d6b8cb0be&coordsys=gps&locations=114.0231567,22.5351085
				logger.info("[LocationService]请求高德GPS位置转换,URL:" + url);
				String responseJsonString = HttpClientGet.get(url);
				logger.info("[LocationService]请求高德坐标转换，应答数据:" + responseJsonString);

				JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonString);
				String locationstatus = responseJsonObject.getString("status");
				// String info = responseJsonObject.getString("info");
				String locations = responseJsonObject.getString("locations");
				if ("1".equals(locationstatus) && locations != null) {
					locations = locations.split(";")[0];
					String[] locationsArr = locations.split(",");
					if (locationsArr.length == 2) {
						locationService.insertUdInfo(imei, 1, locationsArr[1], locationsArr[0], status, time,
								locationStyle);
					}
				}
				voltageService.insertDianLiang(imei, Integer.valueOf(energy));
			} else {
				logger.info("GPS定位失败=" + lat + "," + lng);
			}
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
					String locationstatus = responseJsonObject.getString("status");
					// String info = responseJsonObject.getString("info");
					if ("1".equals(locationstatus)) {
						JSONObject resultJsonObject = responseJsonObject.getJSONObject("result");
						String location = resultJsonObject.getString("location");

						String[] arr = location.split(",");
						if (arr.length == 2) {
							String lat1 = arr[1];
							String lon = arr[0];
							locationService.insertUdInfo(imei, 2, lat1, lon, status, time, locationStyle);
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
					String locationstatus = responseJsonObject.getString("status");
					// String info = responseJsonObject.getString("info");
					if ("1".equals(locationstatus)) {
						JSONObject resultJsonObject = responseJsonObject.getJSONObject("result");
						String location = resultJsonObject.getString("location");

						String[] arr = location.split(",");
						if (arr.length == 2) {
							String lat1 = arr[1];
							String lon = arr[0];
							locationService.insertUdInfo(imei, 3, lat1, lon, status, time, locationStyle);
						}
					}
				}
			}
		}
		if ("AL".equals(cmd)) {
			return "[YW*" + imei + "*0001*0002*AL]";
		}
		return "";
		// TODO Auto-generated method stub
	}

}
