package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.dto.TianQiLatest;
import com.bracelet.dto.WatchLatestLocation;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IVoltageService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.RadixUtil;
import com.bracelet.util.StringUtil;
import com.bracelet.util.Utils;

/**
 * 天气
 * 获取电信的
 * 平台回复: [YW*YYYYYYYYYY*NNNN*LEN* TQ,Result,city_name，info,temperate,lat,lng]
 * 实例: [YW*8800000015*0001*0002* TQ,0,”承德”,”天气晴朗”,”25.3”,22.571707,113.8613968]
 * 说明： city_name，info,temperate字段不管是否解析成功都用双引号号括起来 Result:
 * 0表示经纬度解析成功，此时如果城市天气温度等天气字段为””,则表示对应的信息字段解析失败。 1表示经纬度解析失败
 * 
 * 设备端将通过获取的经纬度信息，开启AGPS快速定位功能，实现快速定位。
 * 
 */
@Component("getDxTianQi")
public class DxTianQi extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IVoltageService voltageService;
	@Autowired
	ILocationService locationService;
	
	

	private static final String TianQiUrl = "https://restapi.amap.com/v3/weather/weatherInfo?key="
			+ Utils.SSRH_TIANQI_KEY + "&extensions=base&output=json" + "&city=";

/*	private static final String TianQiLocaiton = "http://apilocate.amap.com/position?key=" + Utils.SSRH_LOCATION_KEY
			+ "&output=json&accesstype=0&cdma=0&tel=13537596170&network=GSM&imsi=";
*/
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
		// String no = shuzu[2];// 流水号
		String info = shuzu[4];
		String city = "深圳";
		Integer locationStyle = 3;// 1正常2报警3天气4拍照
		String[] infoshuzu = info.split(",");
		String locationis = infoshuzu[3];// A定位 V不定位
		if(locationis ==null||"".equals(locationis)){
			locationis="V";
		}
		String time = infoshuzu[1] + "-" + infoshuzu[2];
		String lat = infoshuzu[4];// 纬度
		String lng = infoshuzu[6]; // 经度
		String status = infoshuzu[16];
		String energy = infoshuzu[13];
		String locationStatus="0";

		if ("A".equals(locationis)) {

			logger.info("imei=" + imei + ",lat=" + lat + ",lon=" + lng + ",time=" + time + ",status=" + status
					+ ",energy=" + energy);

			if (!"0.000000".equals(lat) && !"0.000000".equals(lng)) {
				String url = Utils.SSRH_GPS_URL + "?key=" + Utils.SSRH_TIANQI_KEY + "&coordsys=gps&locations=" + lng + "," + lat;
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
						lat = locationsArr[1];
						lng = locationsArr[0];
						locationService.insertUdInfo(imei, 1, lat, lng, status, time,
								locationStyle);

						String locationValue=lat+","+lng+",1"+","+System.currentTimeMillis();
						limitCache.addKey(imei+"_save",locationValue); 
						limitCache.addKey(imei+"_last",locationValue); 
					}
				}
			} else {
				logger.info("GPS定位失败=" + lat + "," + lng);
				locationStatus="1";
			}
		} else if ("V".equals(locationis)) {
			Integer lbsCount = Integer.valueOf(infoshuzu[17]);
			Integer wifiCount = Integer.valueOf(infoshuzu[17+1+2+1+3*lbsCount]);
			logger.info("lbsCount=" + lbsCount);
			logger.info("wifiCount=" + wifiCount);
			if (wifiCount == 0) {
			
				StringBuffer sbb = new StringBuffer();

				sbb.append("bts=");
				
				sbb.append(infoshuzu[21]).append(",").append(infoshuzu[22]).append(",")
				.append(infoshuzu[23]+",,,");
          
				

				String url = "http://apilocate.amap.com/position?key=" + Utils.SSRH_LOCATION_KEY
						+ "&output=json&accesstype=0&cdma=1&"
						+ sbb.toString();
				logger.info(url);
				String responseJsonString = HttpClientGet.urlReturnParams(url);

				if (responseJsonString != null) {
					JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonString);
					String locationstatus = responseJsonObject.getString("status");
					// String info = responseJsonObject.getString("info");
					if ("1".equals(locationstatus)) {
						JSONObject resultJsonObject = responseJsonObject.getJSONObject("result");
						String location = resultJsonObject.getString("location");
						city = resultJsonObject.getString("city");
						String[] arr = location.split(",");
						if (arr.length == 2) {
							lat = arr[1];
						    lng = arr[0];
							
						    String redisValue = lat+","+lng+",2"+","+System.currentTimeMillis();
						    
							String locationLastInfo = limitCache.getRedisKeyValue(imei+"_save");
							if (!StringUtil.isEmpty(locationLastInfo)) {
								
								String[] locationShuzu = locationLastInfo.split(",");
								//lat", "lng", "locationType", "timestamp"
								String latSave = locationShuzu[0];
								String lngSave = locationShuzu[1];
								//Integer locationTypeSave = Integer.valueOf(locationShuzu[2]);
								Long timeStampSave = Long.valueOf(locationShuzu[3]);
							
									
								if ((( System.currentTimeMillis() - timeStampSave ) / (60 * 1000)) >= 5) {
									locationService.insertUdInfo(imei, 2, lat, lng, status, time, locationStyle);
									limitCache.addKey(imei+"_save",redisValue); 
									
								} else {
									double calcDistance = Utils.calcDistance(Double.valueOf(lngSave),
											Double.valueOf(latSave), Double.valueOf(lng),
											Double.valueOf(lat));
									if (calcDistance > 550) {
										locationService.insertUdInfo(imei, 2, lat, lng, status, time, locationStyle);
										limitCache.addKey(imei+"_save",redisValue); 
									}
								}
								
							} else {
								locationService.insertUdInfo(imei, 2, lat, lng, status, time, locationStyle);
								limitCache.addKey(imei+"_save",redisValue); 
							}
							
							limitCache.addKey(imei+"_last",redisValue); 
						}
					}
				}else{
					locationStatus="1";
				}
			} else {
				String mmac = "";
				String macs = "";
				if (wifiCount > 0) {
					mmac =  infoshuzu[23+3*lbsCount] + "," + infoshuzu[21+3+3*lbsCount] + ","+infoshuzu[22+3*lbsCount];
//					mmac = infoshuzu[41] + "," + infoshuzu[42] + "," + infoshuzu[40];
					logger.info("mmac="+mmac);
					if (wifiCount > 1) {
						for (int i = 0; i < wifiCount*3; i=i+3) {
							if (i>1) {
								macs +=  "|";
							}
							macs +=   infoshuzu[23+3*lbsCount+ i]+ ","+infoshuzu[21+3+3*lbsCount + i]+ ","+infoshuzu[22+3*lbsCount + i];
						}
					}

				}

				if (macs != null && !"".equals(macs) && mmac != null && !"".equals(mmac)) {
					String url = "http://apilocate.amap.com/position?key=" + Utils.SSRH_LOCATION_KEY
							+ "&output=json&accesstype=1&imei="+imei+"&mmac=" + mmac + "&macs=" + macs;
					logger.info(url);
					String responseJsonString = HttpClientGet.urlReturnParams(url);
					if (responseJsonString != null) {
						JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonString);
						String locationstatus = responseJsonObject.getString("status");
						// String info = responseJsonObject.getString("info");
						if ("1".equals(locationstatus)) {
							JSONObject resultJsonObject = responseJsonObject.getJSONObject("result");
							String location = resultJsonObject.getString("location");
							city = resultJsonObject.getString("city");
							String[] arr = location.split(",");
							if (arr.length == 2) {
								lat = arr[1];
							    lng = arr[0];
							    
							    String redisValue = lat+","+lng+",3"+","+System.currentTimeMillis();
								
							    String locationLastInfo = limitCache.getRedisKeyValue(imei+"_save");
								if (!StringUtil.isEmpty(locationLastInfo)) {
									
									String[] locationShuzu = locationLastInfo.split(",");
									//lat", "lng", "locationType", "timestamp"
									//Integer locationTypeSave = Integer.valueOf(locationShuzu[2]);
									Long timeStampSave = Long.valueOf(locationShuzu[3]);
									String latSave = locationShuzu[0];
									String lngSave = locationShuzu[1];
									
									if (((System.currentTimeMillis() - timeStampSave) / (60 * 1000)) >= 5) {
										locationService.insertUdInfo(imei, 3, lat, lng, status, time, locationStyle);
										limitCache.addKey(imei+"_save",redisValue); 
									} else {
										double calcDistance = Utils.calcDistance(Double.valueOf(lngSave),
												Double.valueOf(latSave), Double.valueOf(lng),
												Double.valueOf(lat));
										if (calcDistance > 550) {
											locationService.insertUdInfo(imei, 3, lat, lng, status, time, locationStyle);
											limitCache.addKey(imei+"_save",redisValue); 
										}
									}
									
								} else {
									locationService.insertUdInfo(imei, 3, lat, lng, status, time, locationStyle);
									limitCache.addKey(imei+"_save",redisValue); 
								}
								limitCache.addKey(imei+"_last",redisValue); 
							}
						}
					}else{
						locationStatus="1";
					}
				}
			}
		}

		
		
		StringBuffer sbreponse = new StringBuffer("[YW*" + imei + "*0001*");

		TianQiLatest tianQi = ChannelMap.getCityTianQi(city+Utils.getRiQi());
		if (tianQi != null) {
			String message = "TQ," + locationStatus + ",\"" + city + "\",\"" + tianQi.getWeather() + "\",\""
					+ tianQi.getTemperature() + "\"," + lat + "," + lng;
			sbreponse.append(RadixUtil.changeRadix(message));
			sbreponse.append("*");
			sbreponse.append(message);
			sbreponse.append("]");

			logger.info("天气HashMap返回=" + sbreponse.toString());
			return sbreponse.toString();
		}

		String responseJsonStringTianQi = HttpClientGet.get(TianQiUrl + city);
		JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonStringTianQi);
		String statusTiQi = responseJsonObject.getString("status");
		if ("1".equals(statusTiQi)) {
			JSONArray jsonArray = responseJsonObject.getJSONArray("lives");
			JSONObject jsonObject2 = (JSONObject) jsonArray.get(0);
			String weather = jsonObject2.getString("weather");
			String temperature = jsonObject2.getString("temperature");
			String reporttime = jsonObject2.getString("reporttime");
			String message = "TQ," + locationStatus + ",\"" + city + "\",\"" + weather + "\",\"" + temperature + "\","
					+ lat + "," + lng;
			sbreponse.append(RadixUtil.changeRadix(message));
			sbreponse.append("*");
			sbreponse.append(message);
			sbreponse.append("]");

			TianQiLatest cityTi = new TianQiLatest();
			cityTi.setCity(city);
			cityTi.setReporttime(reporttime);
			cityTi.setTemperature(temperature);
			cityTi.setWeather(weather);

			ChannelMap.addCityQianQi(city+Utils.getRiQi(), cityTi);

			logger.info("天气返回=" + sbreponse.toString());
		}else{
			String weather = "多云";
			String temperature = "27";
			String message = "TQ," + locationStatus + ",\"" + city + "\",\"" + weather + "\",\"" + temperature + "\","
					+ lat + "," + lng;
			sbreponse.append(RadixUtil.changeRadix(message));
			sbreponse.append("*");
			sbreponse.append(message);
			sbreponse.append("]");
		}
		return sbreponse.toString();

	}

}
