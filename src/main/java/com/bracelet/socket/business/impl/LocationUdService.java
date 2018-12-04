package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.dto.WatchLatestLocation;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IVoltageService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.ChannelMap;
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

	@SuppressWarnings("unused")
	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo, Channel channel) {
		logger.info("位置数据上报:" + jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String cmd = shuzu[0];
		Integer locationStyle = 1;// 1正常2报警3天气4拍照
		String info = shuzu[4];

		if ("AL".equals(cmd)) {
			locationStyle = 2;
		}
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号

		chuliLocationInfo(imei, info, no, locationStyle);

		if ("AL".equals(cmd)) {
			return "[YW*" + imei + "*0001*0002*AL]";
		}
		return "";
	}

	public void chuliLocationInfo(String imei, String info, String no, Integer locationStyle) {
		String city="深圳市";

		logger.info("imei=" + imei + ",info=" + info + ",no=" + no);
		String[] infoshuzu = info.split(",");
		String locationis = infoshuzu[3];// A定位 V不定位
		if (locationis == null || "".equals(locationis)) {
			locationis = "V";
		}
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

						WatchLatestLocation watchlastlocation = new WatchLatestLocation();
						watchlastlocation.setImei(imei);
						watchlastlocation.setLat(locationsArr[1]);
						watchlastlocation.setLng(locationsArr[0]);
						watchlastlocation.setLocationType(1);
						watchlastlocation.setTimestamp(new Date().getTime());
						ChannelMap.addlocation(imei, watchlastlocation);
					}
				}
				voltageService.insertDianLiang(imei, Integer.valueOf(energy));
			} else {
				logger.info("GPS定位失败=" + lat + "," + lng);
			}
		} else if ("V".equals(locationis)) {
			Integer lbsCount = Integer.valueOf(infoshuzu[17]);
			Integer wifiCount = Integer.valueOf(infoshuzu[17 + 1 + 2 + 1 + 3 * lbsCount]);
			logger.info("lbsCount=" + lbsCount);
			logger.info("wifiCount=" + wifiCount);
			if (wifiCount == 0) {
				String aab = "460,0,";
				StringBuffer sbb = new StringBuffer();

				sbb.append("bts=");
				sbb.append(aab);
				sbb.append(infoshuzu[21]).append(",").append(infoshuzu[22]).append(",")
						.append((Integer.valueOf(infoshuzu[23]) * 2 - 113) + "");
				StringBuffer sb = new StringBuffer();
				if (lbsCount > 1) {
					sb.append("&nearbts=");
					for (int i = 0; i < lbsCount * 3; i = i + 3) {
						if (i > 1) {
							sb.append("|");
						}
						sb.append(aab);
						sb.append(infoshuzu[21 + i]).append(",").append(infoshuzu[22 + i]).append(",")
								.append((Integer.valueOf(infoshuzu[23 + i]) * 2 - 113) + "");
					}
				}

				String url = "http://apilocate.amap.com/position?key=" + Utils.SSRH_LOCATION_KEY
						+ "&output=json&accesstype=0&imsi=" + imei + "&cdma=0&tel=13537596170&network=GSM&"
						+ sbb.toString() + sb.toString();
				logger.info(url);
				String responseJsonString = HttpClientGet.urlReturnParams(url);

				if (responseJsonString != null) {
					JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonString);
					String locationstatus = responseJsonObject.getString("status");
					if ("1".equals(locationstatus)) {
						JSONObject resultJsonObject = responseJsonObject.getJSONObject("result");
						String location = resultJsonObject.getString("location");

						String[] arr = location.split(",");
						if (arr.length == 2) {
							String lat1 = arr[1];
							String lon = arr[0];

							WatchLatestLocation oldWatchLocation = ChannelMap.getlocation(imei);
							if (oldWatchLocation != null) {
								if (oldWatchLocation.getLocationType() == 2) {
									if (((oldWatchLocation.getTimestamp() - new Date().getTime()) / (60 * 1000)) >= 3) {
										locationService.insertUdInfo(imei, 2, lat1, lon, status, time, locationStyle);
									} else {
										double calcDistance = Utils.calcDistance(
												Double.valueOf(oldWatchLocation.getLng()),
												Double.valueOf(oldWatchLocation.getLat()), Double.valueOf(lon),
												Double.valueOf(lat1));
										if (calcDistance > 550) {
											locationService.insertUdInfo(imei, 2, lat1, lon, status, time,
													locationStyle);
										}
									}
								} else {
									locationService.insertUdInfo(imei, 2, lat1, lon, status, time, locationStyle);
								}
							} else {
								locationService.insertUdInfo(imei, 2, lat1, lon, status, time, locationStyle);
							}

							WatchLatestLocation watchlastlocation = new WatchLatestLocation();
							watchlastlocation.setImei(imei);
							watchlastlocation.setLat(lat1);
							watchlastlocation.setLng(lon);
							watchlastlocation.setLocationType(2);
							watchlastlocation.setTimestamp(new Date().getTime());
							ChannelMap.addlocation(imei, watchlastlocation);
						}
					}
				}
			} else {
				String mmac = "";
				String macs = "";
				if (wifiCount > 0) {
					mmac = infoshuzu[23 + 3 * lbsCount] + "," + infoshuzu[21 + 3 + 3 * lbsCount] + ","
							+ infoshuzu[22 + 3 * lbsCount];
					logger.info("mmac=" + mmac);
					if (wifiCount > 1) {
						for (int i = 0; i < wifiCount * 3; i = i + 3) {
							if (i > 1) {
								macs += "|";
							}
							macs += infoshuzu[23 + 3 * lbsCount + i] + "," + infoshuzu[21 + 3 + 3 * lbsCount + i] + ","
									+ infoshuzu[22 + 3 * lbsCount + i];
						}
					}

				}

				if (macs != null && !"".equals(macs) && mmac != null && !"".equals(mmac)) {
					String url = "http://apilocate.amap.com/position?key=" + Utils.SSRH_LOCATION_KEY
							+ "&output=json&accesstype=1&imei=" + imei + "&mmac=" + mmac + "&macs=" + macs;
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

								WatchLatestLocation oldWatchLocation = ChannelMap.getlocation(imei);

								if (oldWatchLocation != null) {
									if (oldWatchLocation.getLocationType() == 3) {
										if (((oldWatchLocation.getTimestamp() - new Date().getTime())
												/ (60 * 1000)) >= 3) {
											locationService.insertUdInfo(imei, 3, lat1, lon, status, time,
													locationStyle);
										} else {
											double calcDistance = Utils.calcDistance(
													Double.valueOf(oldWatchLocation.getLng()),
													Double.valueOf(oldWatchLocation.getLat()), Double.valueOf(lon),
													Double.valueOf(lat1));
											if (calcDistance > 550) {
												locationService.insertUdInfo(imei, 3, lat1, lon, status, time,
														locationStyle);
											}
										}
									} else {
										locationService.insertUdInfo(imei, 3, lat1, lon, status, time, locationStyle);
									}
								} else {
									locationService.insertUdInfo(imei, 3, lat1, lon, status, time, locationStyle);
								}

								WatchLatestLocation watchlastlocation = new WatchLatestLocation();
								watchlastlocation.setImei(imei);
								watchlastlocation.setLat(lat1);
								watchlastlocation.setLng(lon);
								watchlastlocation.setLocationType(3);
								watchlastlocation.setTimestamp(new Date().getTime());
								ChannelMap.addlocation(imei, watchlastlocation);
							}
						}
					}
				}
			}
		}
	}

}
