package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.dto.WatchLatestLocation;
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
	
	@Autowired
	IVoltageService voltageService;

	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto, JSONObject jsonObject, Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo, Channel channel) {

		
		logger.info("设备照片上传=" + jsonInfo);
		
		String imei = socketLoginDto.getImei();
		
		try{
			if (jsonInfo.contains("YW*")) {
			
			String[] shuzu = jsonInfo.split("\\*");
			//String imei = shuzu[1];// 设备imei
			String no = shuzu[2];// 流水号
			String info = shuzu[4];
			Integer locationStyle = 4;// 1正常2报警3天气4拍照
			String[] infoshuzuMsg = info.split(",");
			String source = infoshuzuMsg[1];// 文件-来源 如果是设备就填0，如果是App就填发的人的手机号码
			String photoName = infoshuzuMsg[2];// 文件名
			int thisNumber = Integer.valueOf(infoshuzuMsg[3]);// 当前包 如果是0就是定位
																// 1就是照片数据
			int allNumber = Integer.valueOf(infoshuzuMsg[4]);// 总包个数
			//String dataInfo = "ud," + infoshuzuMsg[5];// thisNumber0位置数据--其他照片数据
	
            int insertNumber = 0;
            if(allNumber>9 && thisNumber>9 ){
            	insertNumber=2;
            }else if(allNumber>9 && thisNumber<9 ){
            	insertNumber=1;
            }
            		
		if (thisNumber != 0) {// 当前包 如果是0就是定位  1就是照片数据
			photoName = imei+"_"+ photoName;
			ChannelMap.addVoiceName(imei, photoName);
			
			int intIndex = jsonInfo.indexOf(".jpg");
			   logger.info("insertNumber="+insertNumber);
			logger.info("jpg=" + jsonInfo.substring(intIndex + 9+insertNumber, jsonInfo.length()));
			byte[] voiceData = jsonInfo.substring(intIndex+9+insertNumber, jsonInfo.length()).getBytes("UTF-8");
			Utils.createFileContent(Utils.PHOTO_FILE_lINUX, photoName, voiceData);
			
		//	Utils.base64StringToJpg(jsonInfo.substring(intIndex + 9, jsonInfo.length()),Utils.PHOTO_FILE_lINUX+"/"+imei+photoName);
			
			if(thisNumber == allNumber&& allNumber!=0){
				iUploadPhotoService.insertPhoto(imei, Utils.PHOTO_URL+ photoName, photoName, "1");
			}
			
			String resp = "TPCF," + photoName + "," + thisNumber + "," + allNumber + ",1";
			StringBuffer sb = new StringBuffer("[YW*" + imei + "*0002*");
			sb.append(RadixUtil.changeRadix(resp));
			sb.append("*");
			sb.append(resp);
			sb.append("]");
			logger.info("设备拍照返回数据=" + sb.toString());
			return sb.toString();
			/*String photoInfo = infoshuzuMsg[5];
			if (thisNumber == 1) {
				iUploadPhotoService.insertPhoto(imei, source, photoName, photoInfo);
			} else {
				WatchUploadPhotoInfo waUpInfo = iUploadPhotoService.getByPhotoNameAndImei(imei, photoName);
				if (waUpInfo != null) {
					iUploadPhotoService.updateById(waUpInfo.getId(), waUpInfo.getData() + photoInfo);
				}
			}*/
		}else{
			chuliLocationInfo(imei, info, no, locationStyle);
			
			
			String resp = "TPCF," + photoName + "," + thisNumber + "," + allNumber + ",1";
			StringBuffer sb = new StringBuffer("[YW*" + imei + "*0002*");
			sb.append(RadixUtil.changeRadix(resp));
			sb.append("*");
			sb.append(resp);
			sb.append("]");
			logger.info("设备拍照返回数据=" + sb.toString());
			return sb.toString();
		}
		
		}else{
			String photoName = ChannelMap.getVoiceName(imei);
			logger.info("photoName=" + photoName);
		//	byte[] voiceData = Base64.decodeBase64(jsonInfo);
			byte[] phtotData = jsonInfo.getBytes("UTF-8");
			Utils.createFileContent(Utils.PHOTO_FILE_lINUX, photoName, phtotData);
			
			//Utils.base64StringToJpg(jsonInfo,Utils.PHOTO_FILE_lINUX+"/"+imei+photoName);
			
			return "";
		}
		//iUploadPhotoService.insert(imei, photoName, source, thisNumber, allNumber);
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		// [YW*YYYYYYYYYY*NNNN*LEN*TPCF, 文件名，当前包，总包个数，1]
		/*String resp = "TPCF," + photoName + "," + thisNumber + "," + allNumber + ",1";
		StringBuffer sb = new StringBuffer("[YW*" + imei + "*0002*");
		sb.append(RadixUtil.changeRadix(resp));
		sb.append("*");
		sb.append(resp);
		sb.append("]");
		logger.info("设备拍照返回数据=" + sb.toString());*/
		return "";
	}


	
	public void chuliLocationInfo(String imei, String info, String no, Integer locationStyle) {

		logger.info("imei=" + imei + ",info=" + info + ",no=" + no);
		String[] infoshuzu = info.split(",");
		String locationis = infoshuzu[3+4];// A定位 V不定位
		if (locationis == null || "".equals(locationis)) {
			locationis = "V";
		}
		String time = infoshuzu[1+4] + "-" + infoshuzu[2+4];
		String lat = infoshuzu[4+4];// 纬度
		String lng = infoshuzu[6+4]; // 经度
		String status = infoshuzu[16+4];
		String energy = infoshuzu[13+4];

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
			Integer lbsCount = Integer.valueOf(infoshuzu[17+4]);
			Integer wifiCount = Integer.valueOf(infoshuzu[17 + 1 + 2 + 1 + 3 * lbsCount+4]);
			logger.info("lbsCount=" + lbsCount);
			logger.info("wifiCount=" + wifiCount);
			if (wifiCount == 0) {
				String aab = "460,0,";
				StringBuffer sbb = new StringBuffer();

				sbb.append("bts=");
				sbb.append(aab);
				sbb.append(infoshuzu[21+4]).append(",").append(infoshuzu[22+4]).append(",")
						.append((Integer.valueOf(infoshuzu[23+4]) * 2 - 113) + "");
				StringBuffer sb = new StringBuffer();
				if (lbsCount > 1) {
					sb.append("&nearbts=");
					for (int i = 0; i < lbsCount * 3; i = i + 3) {
						if (i > 1) {
							sb.append("|");
						}
						sb.append(aab);
						sb.append(infoshuzu[21 + i+4]).append(",").append(infoshuzu[22 + i+4]).append(",")
								.append((Integer.valueOf(infoshuzu[23 + i+4]) * 2 - 113) + "");
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
					mmac = infoshuzu[23 + 3 * lbsCount+4] + "," + infoshuzu[21 + 3 + 3 * lbsCount+4] + ","
							+ infoshuzu[22 + 3 * lbsCount+4];
					logger.info("mmac=" + mmac);
					if (wifiCount > 1) {
						for (int i = 0; i < wifiCount * 3; i = i + 3) {
							if (i > 1) {
								macs += "|";
							}
							macs += infoshuzu[23 + 3 * lbsCount + i+4] + "," + infoshuzu[21 + 3 + 3 * lbsCount + i+4] + ","
									+ infoshuzu[22 + 3 * lbsCount + i+4];
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
