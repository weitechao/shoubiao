package com.bracelet.socket.business.impl;

import com.bracelet.service.IFenceService;

import io.netty.channel.Channel;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.service.ILocationService;
import com.bracelet.service.ISensitivePointService;
import com.bracelet.util.HttpClientGet;

@Service("locationService")
public class LocationService extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ILocationService locationService;
	@Autowired
	IFenceService fenceService;
	@Autowired
	ISensitivePointService sensitivePointService;

	/**
	 * 高德地图key，基站查询
	 */
	private static final String KEY = "8ae0a1e668fdcd49bb95bf597f7e5b34";
	private static final String GPS_KEY = "c6a272fdecf96b343c31719d6b8cb0be";
	private static final String URL = "http://apilocate.amap.com/position";
	private static final String GPS_URL = "http://restapi.amap.com/v3/assistant/coordinate/convert";

	public SocketBaseDto process1(SocketLoginDto socketLoginDto, JSONObject jsonObject, Channel channel) {
		// 0gps 1基站
		String locationType = jsonObject.getString("locationType");
		String lat = null; // 纬度
		String lng = null; // 经度
		int accuracy = 0;
		Long user_id = socketLoginDto.getUser_id();
		logger.info("[LocationService]处理位置信息,user_id:" + user_id + ",imei:" + socketLoginDto.getImei() + ",no:"
				+ jsonObject.getString("no"));
		Integer statusType = Integer.valueOf(0);
		statusType = jsonObject.getInteger("a");

		if ("0".equals(locationType)) {
			// GPS
			logger.info("[LocationService]处理GPS位置信息：" + jsonObject.toJSONString());
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for (int i = 0; jsonArray != null && i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
				JSONObject jsonArray2 = jsonObject2.getJSONObject("gps");
				lng = jsonArray2.getString("lng");
				lat = jsonArray2.getString("lat");
				accuracy = jsonArray2.getIntValue("accuracy");
				long timestamp = jsonObject2.getLongValue("timestamp");
				try {
					if (!"0.000000".equals(lat) && !"0.000000".equals(lng)) {
						String url = GPS_URL + "?key=" + GPS_KEY + "&coordsys=gps&locations=" + lng + "," + lat;
						//http://restapi.amap.com/v3/assistant/coordinate/convert?key=c6a272fdecf96b343c31719d6b8cb0be&coordsys=gps&locations=114.0231567,22.5351085
						logger.info("[LocationService]请求高德GPS位置转换,URL:" + url);
						String responseJsonString = HttpClientGet.get(url);
						logger.info("[LocationService]请求高德坐标转换，应答数据:" + responseJsonString);

						JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonString);
						String status = responseJsonObject.getString("status");
						// String info = responseJsonObject.getString("info");
						String locations = responseJsonObject.getString("locations");
						if ("1".equals(status) && locations != null) {
							locations = locations.split(";")[0];
							String[] locationsArr = locations.split(",");
							if (locationsArr.length == 2) {
								locationService.insert(user_id, locationType, locationsArr[1], locationsArr[0], accuracy, statusType,
										new Timestamp(timestamp * 1000));
								// 电子围栏检查
								fenceService.checkFenceArea(user_id, socketLoginDto.getImei(), locationsArr[1], locationsArr[0],
										timestamp * 1000);
								// 敏感区域检查
								sensitivePointService.checkSensitivePointArea(user_id, socketLoginDto.getImei(), locationsArr[1],
										locationsArr[0], timestamp * 1000);
							} else {
								logger.warn("[LocationService]请求高德转换坐标信息发生错误:" + jsonObject2.toJSONString());
							}
						} else {
							logger.warn("[LocationService]请求高德转换坐标信息发生错误:" + jsonObject2.toJSONString());
						}
					} else {
						logger.warn("[LocationService]收到0.0错误位置信息，请通知设备端修正:" + jsonObject2.toJSONString());
					}
				} catch (Exception e) {
					logger.warn("[LocationService]保存位置信息发生错误:" + jsonObject2.toJSONString(), e);
				}
			}
		} else if ("1".equals(locationType)) {
			// 基站
			logger.info("[LocationService]处理基站位置信息：" + jsonObject.toJSONString());
			JSONArray jsonArray = jsonObject.getJSONArray("data");
			for (int i = 0; jsonArray != null && i < jsonArray.size(); i++) {
				JSONObject jsonObject2 = (JSONObject) jsonArray.get(i);
				JSONObject lbsJsonObject = jsonObject2.getJSONObject("lbs");
				Long timestamp = jsonObject.getLong("timestamp");
				if (lbsJsonObject != null) {
					String accesstype = lbsJsonObject.getString("accesstype");
					accesstype = accesstype == null ? lbsJsonObject.getString("accesstypet") : accesstype;
					String imsi = lbsJsonObject.getString("imsi");// 手机 imei 号
					String smac = lbsJsonObject.getString("smac");
					String serverip = lbsJsonObject.getString("serverip");
					String cdma = lbsJsonObject.getString("cdma");
					String network = lbsJsonObject.getString("network");
					String tel = lbsJsonObject.getString("tel");
					String bts = lbsJsonObject.getString("bts");
					String nearbts = lbsJsonObject.getString("nearbts");
					String mmac = lbsJsonObject.getString("mmac");
					String macs = lbsJsonObject.getString("macs");
					if (imsi == null) {
						imsi = socketLoginDto != null ? socketLoginDto.getImei() : null;
					}

					StringBuilder myurlBuilder = new StringBuilder(URL);
					myurlBuilder.append("?key=").append(KEY).append("&output=json");
					if (accesstype != null && accesstype.length() != 0) {
						myurlBuilder.append("&accesstype=").append(accesstype);
					}
					if (imsi != null && imsi.length() != 0) {
						myurlBuilder.append("&imsi=").append(imsi);
					}
					if (smac != null && smac.length() != 0) {
						myurlBuilder.append("&smac=").append(smac);
					}
					if (serverip != null && serverip.length() != 0) {
						myurlBuilder.append("&serverip=").append(serverip);
					}
					if (cdma != null && cdma.length() != 0) {
						myurlBuilder.append("&cdma=").append(cdma);
					}
					if (network != null && network.length() != 0) {
						myurlBuilder.append("&network=").append(network);
					}
					if (tel != null && tel.length() != 0) {
						myurlBuilder.append("&tel=").append(tel);
					}
					if (bts != null && bts.length() != 0) {
						myurlBuilder.append("&bts=").append(bts);
					}
					if (nearbts != null && nearbts.length() != 0) {
						myurlBuilder.append("&nearbts=").append(nearbts);
					}
					if (mmac != null && mmac.length() != 0) {
						myurlBuilder.append("&mmac=").append(mmac);
					}
					if (macs != null && mmac.length() != 0) {
						myurlBuilder.append("&macs=").append(macs);
					}

					try {
						logger.info("[LocationService]根据基站查询位置,URL:" + myurlBuilder.toString());
						String responseJsonString = HttpClientGet.get(myurlBuilder.toString());
						logger.info("[LocationService]高德返回数据:" + responseJsonString);
						if (responseJsonString != null) {
							JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonString);
							String status = responseJsonObject.getString("status");
							String info = responseJsonObject.getString("info");

							if ("1".equals(status)) {
								JSONObject resultJsonObject = responseJsonObject.getJSONObject("result");
								if (resultJsonObject == null) {
									logger.warn("[LocationService] 返回的result为空");
									continue;
								}
								String location = resultJsonObject.getString("location");
								if (location == null) {
									logger.warn("[LocationService] 返回的location为空");
									continue;
								}

								String[] arr = location.split(",");
								if (arr.length == 2) {
									lat = arr[1];
									lng = arr[0];
								} else {
									logger.warn("[LocationService] 返回的location数据错误:" + location);
								}
								locationService.insert(user_id, locationType, lat, lng, Integer.valueOf(0), statusType,
										new Timestamp(timestamp * 1000));
								// 电子围栏检查
								fenceService.checkFenceArea(user_id, socketLoginDto.getImei(), lat, lng, timestamp * 1000);
								// 敏感区域检查
								sensitivePointService.checkSensitivePointArea(user_id, socketLoginDto.getImei(), lat, lng,
										timestamp * 1000);
							} else {
								logger.warn("[LocationService]查询定位失败,status：" + status + ", info:" + info);
							}
						} else {
							logger.warn("[LocationService]高德返回应答为空!");
						}
					} catch (Exception e) {
						logger.warn("[LocationService]处理高德返回数据并保存数据发生异常:", e);
					}
				} else {
					logger.warn("[LocationService]错误基站位置信息，请通知设备端修正:" + jsonObject2.toJSONString());
				}
			}
		} else {
			logger.warn("[LocationService]未知的locationType请求，请通知设备端修正:" + locationType);
		}

		SocketBaseDto dto = new SocketBaseDto();
		dto.setType(jsonObject.getIntValue("type"));
		dto.setNo(jsonObject.getString("no"));
		dto.setTimestamp(System.currentTimeMillis());
		dto.setStatus(0);
		return dto;
	}

	@Override
	public String process(String jsonInfo, Channel incoming) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		//wifi 定位
		String imei = "";
		String smac ="";
		String mmac = "";
		String macs = "";
		String serverip = "";

		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

		map.put("accesstype", "1");
		map.put("imei", imei);
		map.put("smac", smac);
		map.put("mmac", mmac);
		map.put("macs", macs);
		map.put("key", "1c1c642bd81c287fd16239ddc0eb85c6");
		if (serverip != null && !"".equals(serverip)) {
			map.put("serverip", serverip);
		} else {
			map.put("serverip", "121.196.232.11");
		}

		String jsonToString = HttpClientGet.sendGetToGaoDe("http://apilocate.amap.com/position", map);
	}
}
