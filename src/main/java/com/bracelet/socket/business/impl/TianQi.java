package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IVoltageService;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.Utils;

/**
 * 天气
 * 
 * 平台回复: [YW*YYYYYYYYYY*NNNN*LEN* TQ,Result,city_name，info,temperate,lat,lng]
 * 实例: [YW*8800000015*0001*0002* TQ,0,”承德”,”天气晴朗”,”25.3”,22.571707,113.8613968]
 * 说明： city_name，info,temperate字段不管是否解析成功都用双引号号括起来 Result:
 * 0表示经纬度解析成功，此时如果城市天气温度等天气字段为””,则表示对应的信息字段解析失败。 1表示经纬度解析失败
 * 
 * 设备端将通过获取的经纬度信息，开启AGPS快速定位功能，实现快速定位。
 * 
 */
@Component("getTianQi")
public class TianQi extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IVoltageService voltageService;
	@Autowired
	ILocationService locationService;

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto, JSONObject jsonObject, Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo, Channel channel) {
		logger.info("天气获取=" + jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		//String no = shuzu[2];// 流水号
		String info = shuzu[4];
		String[] infoshuzu = info.split(",");
		String time = infoshuzu[1] + "-" + infoshuzu[2];
		//Integer wifiCount = Integer.valueOf(infoshuzu[39]);
		String city = "深圳";
		/*
		 * UD,11102018,142013,V,0.000000,N,
		 * 0.000000,E,0.00,0.0,0.0,0,100,100,0,0:0,00000000,6,1, 460,0, 19
		 * 10173,4934,49, 10173,4263,34, 10173,4941,34, 10173,4931,31,
		 * 10173,4943,27, 10173,4582,27, 0
		 */

		String aab = "460,0,";
		StringBuffer sbb = new StringBuffer();

		sbb.append("bts=");
		sbb.append(aab);
		sbb.append(infoshuzu[21]).append(",").append(infoshuzu[22]).append(",")
				.append((Integer.valueOf(infoshuzu[23]) * 2 - 113) + "");
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
				+ "&output=json&accesstype=0&imsi=" + imei + "&cdma=0&tel=13537596170&network=GSM&" + sbb.toString()
				+ sb.toString();
		logger.info(url);
		String responseJsonString = HttpClientGet.urlReturnParams(url);

		String lat = "0.000000";
		String lon = "0.000000";
		String locationStatus = "0";
		if (responseJsonString != null) {
			JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonString);
			String status = responseJsonObject.getString("status");
			// String info = responseJsonObject.getString("info");
			if ("1".equals(status)) {
				JSONObject resultJsonObject = responseJsonObject.getJSONObject("result");
				city = resultJsonObject.getString("city");
				String location = resultJsonObject.getString("location");
				String[] arr = location.split(",");
				if (arr.length == 2) {
					lat = arr[1];
					lon = arr[0];
					locationService.insertUdInfo(imei, 2, lat, lon, status, time, 3);
				}
			} else {
				locationStatus = "1";
			}
		}
		StringBuffer sbreponse = new StringBuffer("[YW*" + imei + "*0001*");

		// api 地址 https://lbs.amap.com/api/webservice/guide/api/weatherinfo/
		String responseJsonStringTianQi = HttpClientGet.get("https://restapi.amap.com/v3/weather/weatherInfo?key="
				+ Utils.SSRH_TIANQI_KEY + "&city=" + city + "&extensions=base&output=json");
		JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonStringTianQi);
		// {"status":"1","count":"1","info":"OK","infocode":"10000","lives":[{"province":"广东","city":"深圳市","adcode":"440300","weather":"晴","temperature":"25","winddirection":"东北","windpower":"6","humidity":"77","reporttime":"2018-11-16
		// 16:00:00"}]}
		String status = responseJsonObject.getString("status");
		if ("1".equals(status)) {
			JSONObject resultJsonObject = responseJsonObject.getJSONObject("lives");
			String weather = resultJsonObject.getString("weather");
			String temperature = resultJsonObject.getString("temperature");
			String reporttime = resultJsonObject.getString("reporttime");
			String message = "TQ," + locationStatus + ",“" + city + "”,“" + weather + "”,”" + temperature + "”," + lat
					+ "," + lon;
			sbreponse.append(RadixUtil.changeRadix(message));
			sbreponse.append("*");
			sbreponse.append(message);
			sbreponse.append("]");

			// String resp = "[YW*" + imei + "*0001*0002*
			// TQ,0,”承德”,”天气晴朗”,”25.3”,22.571707,113.8613968]";
		}
		/*
		 * {"status":"1","count":"1","info":"OK","infocode":"10000",
		 * "forecasts":[{"city":"北京市","adcode":"110000","province":"北京",
		 * "reporttime":"2018-09-21 11:00:00",
		 * "casts":[{"date":"2018-09-21","week":"5","dayweather":"晴",
		 * "nightweather":"晴","daytemp":"25",
		 * "nighttemp":"14","daywind":"西北","nightwind":"西北","daypower":"5",
		 * "nightpower":"≤3"},
		 * {"date":"2018-09-22","week":"6","dayweather":"晴","nightweather":"晴",
		 * "daytemp":"23",
		 * "nighttemp":"13","daywind":"西北","nightwind":"西","daypower":"4",
		 * "nightpower":"≤3"},
		 * {"date":"2018-09-23","week":"7","dayweather":"晴","nightweather":"晴",
		 * "daytemp":"24","nighttemp":"12",
		 * "daywind":"西北","nightwind":"北","daypower":"5","nightpower":"≤3"},
		 * {"date":"2018-09-24","week":"1","dayweather":"晴","nightweather":"晴",
		 * "daytemp":"23","nighttemp":"11","daywind":"东南","nightwind":"西南",
		 * "daypower":"≤3","nightpower":"≤3"}]}]}
		 */
		// 天气查询API

		return sbreponse.toString();

	}

}
