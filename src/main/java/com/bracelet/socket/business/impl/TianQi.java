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
import com.bracelet.service.IVoltageService;
import com.bracelet.util.HttpClientGet;

/**
 * 天气
 * 
 * 平台回复: 
[YW*YYYYYYYYYY*NNNN*LEN* TQ,Result,city_name，info,temperate,lat,lng] 
实例:
[YW*8800000015*0001*0002* TQ,0,”承德”,”天气晴朗”,”25.3”,22.571707,113.8613968]
说明：
city_name，info,temperate字段不管是否解析成功都用双引号号括起来
Result:
0表示经纬度解析成功，此时如果城市天气温度等天气字段为””,则表示对应的信息字段解析失败。
1表示经纬度解析失败 

设备端将通过获取的经纬度信息，开启AGPS快速定位功能，实现快速定位。
 * 
 */
@Component("getTianQi")
public class TianQi extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IVoltageService voltageService;
	
	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {
		logger.info("天气获取="+jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		String info = shuzu[4];
		String responseJsonString = HttpClientGet.get("https://restapi.amap.com/v3/weather/weatherInfo?key=4586b592107f5ac5faa8ab898f3af023&city=%E5%8C%97%E4%BA%AC&extensions=all&output=json");
		JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonString);
		String status = responseJsonObject.getString("status");
		/*
		 * {"status":"1","count":"1","info":"OK","infocode":"10000",
		 * "forecasts":[{"city":"北京市","adcode":"110000","province":"北京","reporttime":"2018-09-21 11:00:00",
		 * "casts":[{"date":"2018-09-21","week":"5","dayweather":"晴","nightweather":"晴","daytemp":"25",
		 * "nighttemp":"14","daywind":"西北","nightwind":"西北","daypower":"5","nightpower":"≤3"},
		 * {"date":"2018-09-22","week":"6","dayweather":"晴","nightweather":"晴","daytemp":"23",
		 * "nighttemp":"13","daywind":"西北","nightwind":"西","daypower":"4","nightpower":"≤3"},
		 * {"date":"2018-09-23","week":"7","dayweather":"晴","nightweather":"晴","daytemp":"24","nighttemp":"12",
		 * "daywind":"西北","nightwind":"北","daypower":"5","nightpower":"≤3"},
		 * {"date":"2018-09-24","week":"1","dayweather":"晴","nightweather":"晴",
		 * "daytemp":"23","nighttemp":"11","daywind":"东南","nightwind":"西南","daypower":"≤3","nightpower":"≤3"}]}]}
		 * */
		//天气查询API
		String resp = "[YW*"+imei+"*0001*0002* TQ,0,”承德”,”天气晴朗”,”25.3”,22.571707,113.8613968]";
		return resp;
		
		}



}
