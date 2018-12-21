package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.dto.WatchLatestLocation;
import com.bracelet.entity.Location;
import com.bracelet.entity.LocationOld;
import com.bracelet.entity.LocationRequest;
import com.bracelet.entity.LocationWatch;
import com.bracelet.entity.OldBindDevice;
import com.bracelet.entity.Step;
import com.bracelet.entity.UserInfo;
import com.bracelet.exception.BizException;
import com.bracelet.redis.LimitCache;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IStepService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.socket.BaseChannelHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.RanomUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/location")
public class LocationController extends BaseController {

	@Autowired
	ILocationService locationService;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IStepService stepService;
	@Resource
	BaseChannelHandler baseChannelHandler;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/* app查询手表最新定位 */
	@ResponseBody
	@RequestMapping(value = "/getlast/search/{token}/{imei}", method = RequestMethod.GET)
	public String getLastLocation(@PathVariable String token, @PathVariable String imei) {
		JSONObject bb = new JSONObject();

		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}

		WatchLatestLocation watchlocaiton = ChannelMap.getlocation(imei);
		if (watchlocaiton != null) {
			bb.put("lat", watchlocaiton.getLat());
			bb.put("lng", watchlocaiton.getLng());
			bb.put("locationType", watchlocaiton.getLocationType());
			bb.put("uploadtime", watchlocaiton.getTimestamp());
			bb.put("Code", 1);

			JSONObject dataMap = new JSONObject();
			dataMap.put("DeviceID", "");
			dataMap.put("Message", 0);
			dataMap.put("Voice", 0);
			dataMap.put("SMS", 0);
			dataMap.put("Photo", 0);
			JSONArray jsonArray = new JSONArray();
			jsonArray.add(dataMap);
			bb.put("NewList", jsonArray);

			JSONObject dataMap1 = new JSONObject();
			dataMap1.put("DeviceID", "");
			dataMap1.put("Altitude", 0);
			dataMap1.put("Course", 0);
			dataMap1.put("Course", 0);
			dataMap1.put("LocationType", watchlocaiton.getLocationType());
			dataMap1.put("wifi", "");
			dataMap1.put("CreateTime", "");
			dataMap1.put("Electricity", 100);

			String energy = limitCache.getRedisKeyValue(imei + "_energy");
			if (energy != null) {
				dataMap1.put("Electricity", energy);
			}
			dataMap1.put("GSM", 94);
			dataMap1.put("Step", 0);
			dataMap1.put("Health", "0.0");
			dataMap1.put("Latitude", watchlocaiton.getLat());
			dataMap1.put("Longitude", watchlocaiton.getLng());
			dataMap1.put("Online", 0);
			SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
			if (socketLoginDto != null) {
				dataMap1.put("Online", 1);
			}
			dataMap1.put("SatelliteNumber", 0);
			dataMap1.put("ServerTime", "");
			dataMap1.put("Speed", 0);
			dataMap1.put("UpdateTime", "");

			JSONArray jsonArray1 = new JSONArray();
			jsonArray1.add(dataMap1);
			bb.put("DeviceState", jsonArray1);

		} else {
			LocationWatch locationWatch = locationService.getLatest(imei);
			if (locationWatch != null) {

				WatchLatestLocation watchlastlocation = new WatchLatestLocation();
				watchlastlocation.setImei(imei);
				watchlastlocation.setLat(locationWatch.getLat());
				watchlastlocation.setLng(locationWatch.getLng());
				watchlastlocation.setLocationType(locationWatch.getLocation_type());
				watchlastlocation.setTimestamp(locationWatch.getUpload_time().getTime());
				ChannelMap.addlocation(imei, watchlastlocation);

				bb.put("lat", locationWatch.getLat());
				bb.put("lng", locationWatch.getLng());
				bb.put("locationType", locationWatch.getLocation_type());
				bb.put("uploadtime", locationWatch.getUpload_time().getTime());
				bb.put("Code", 1);

				JSONObject dataMap = new JSONObject();
				dataMap.put("DeviceID", "");
				dataMap.put("Message", 0);
				dataMap.put("Voice", 0);
				dataMap.put("SMS", 0);
				dataMap.put("Photo", 0);
				JSONArray jsonArray = new JSONArray();
				jsonArray.add(dataMap);
				bb.put("NewList", jsonArray);

				JSONObject dataMap1 = new JSONObject();
				dataMap1.put("DeviceID", "");
				dataMap1.put("Altitude", 0);
				dataMap1.put("Course", 0);
				dataMap1.put("Course", 0);
				dataMap1.put("LocationType", locationWatch.getLocation_type());
				dataMap1.put("wifi", "");
				dataMap1.put("CreateTime", "");
				dataMap1.put("Electricity", 100);

				String energy = limitCache.getRedisKeyValue(imei + "_energy");
				if (energy != null) {
					dataMap1.put("Electricity", energy);
				}
				dataMap1.put("GSM", 94);
				dataMap1.put("Step", 0);
				dataMap1.put("Health", "0.0");
				dataMap1.put("Latitude", locationWatch.getLat());
				dataMap1.put("Longitude", locationWatch.getLng());
				dataMap1.put("Online", 0);
				SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
				if (socketLoginDto != null) {
					dataMap1.put("Online", 1);
				}
				dataMap1.put("SatelliteNumber", 0);
				dataMap1.put("ServerTime", "");
				dataMap1.put("Speed", 0);
				dataMap1.put("UpdateTime", "");

				JSONArray jsonArray1 = new JSONArray();
				jsonArray1.add(dataMap1);
				bb.put("DeviceState", jsonArray1);

			} else {
				bb.put("Code", 0);
			}
		}

		return bb.toString();
	}

	/* 查询手表的轨迹 */
	@ResponseBody
	@RequestMapping(value = "/watchtrack/{token}/{imei}/{starttime}/{endtime}", method = RequestMethod.GET)
	public String watchtrack(@PathVariable String token, @PathVariable String imei, @PathVariable String starttime,
			@PathVariable String endtime) {
		JSONObject bb = new JSONObject();
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("code", -1);
			return bb.toString();
		}

		List<LocationWatch> locationList = locationService.getWatchFootprint(imei, starttime, endtime);
		JSONArray jsonArray = new JSONArray();
		if (locationList != null) {
			bb.put("Total", locationList.size() + "");
			for (LocationWatch location : locationList) {
				JSONObject dataMap = new JSONObject();
				dataMap.put("lat", location.getLat());
				dataMap.put("lng", location.getLng());
				dataMap.put("timestamp", location.getUpload_time().getTime());
				dataMap.put("locationType", location.getLocation_type());

				dataMap.put("time", location.getUpload_time().getTime() + "");
				dataMap.put("Status", 0);
				dataMap.put("Latitude", location.getLat());
				dataMap.put("Longitude", location.getLng());
				dataMap.put("LocationType", location.getLocation_type());
				dataMap.put("CreateTime", location.getUpload_time().getTime() + "");
				dataMap.put("UpdateTime", location.getUpload_time().getTime() + "");
				jsonArray.add(dataMap);
			}
			bb.put("Code", 1);
			// bb.put("list", jsonArray.toString());
		} else {
			bb.put("Code", 0);

		}

		bb.put("List", jsonArray);
		return bb.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/search/latest/{token}", method = RequestMethod.GET)
	public HttpBaseDto getLatestLocation(@PathVariable String token) {
		Long user_id = checkTokenAndUser(token);
		Location location = locationService.getLatest(user_id);
		Map<String, Object> dataMap = new HashMap<>();
		if (location != null) {
			dataMap.put("lat", location.getLat());
			dataMap.put("lng", location.getLng());
			dataMap.put("timestamp", location.getUpload_time().getTime());
		}
		Step step = stepService.getLatest(user_id);
		if (step != null) {
			dataMap.put("step", step.getStep_number());
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(dataMap);
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/search/realtime/{token}", method = RequestMethod.GET)
	public HttpBaseDto getRealtimeLocation(@PathVariable String token) {
		Long user_id = checkTokenAndUser(token);
		Location location = locationService.getRealtimeLocation(user_id, Integer.valueOf(1));
		Map<String, Object> dataMap = new HashMap<>();
		if (location != null) {
			dataMap.put("lat", location.getLat());
			dataMap.put("lng", location.getLng());
			dataMap.put("timestamp", location.getUpload_time().getTime());
		}

		Step step = stepService.getLatest(user_id);
		if (step != null) {
			dataMap.put("step", step.getStep_number());
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(dataMap);
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/ask/location/{token}", method = RequestMethod.GET)
	public HttpBaseDto askLocation(@PathVariable String token) {
		Long user_id = checkTokenAndUser(token);
		UserInfo userInfo = userInfoService.getUserInfoById(user_id);
		if (userInfo == null) {
			logger.info("askLocation error.no login.token:" + token);
			throw new BizException(RespCode.U_NOT_EXIST);
		}
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(userInfo.getImei());
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			logger.info("socketLoginDto error.no login.token:" + token);
			throw new BizException(RespCode.DEV_NOT_LOGIN);
		}

		LocationRequest re = new LocationRequest();
		re.setA(0);
		re.setTimestamp(System.currentTimeMillis() / 1000);
		re.setType(30);
		re.setNo(RanomUtil.getFixLenthString(10));

		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(JSON.toJSONString(re) + "\r\n");
			logger.info("===request getLocation...ip:" + socketLoginDto.getChannel().remoteAddress().toString()
					+ ",data:" + JSON.toJSONString(re));
		} else {
			logger.info("socketLoginDto error.no login.not active.token:" + token);
			throw new BizException(RespCode.DEV_NOT_LOGIN);
		}

		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/search/footprint/{token}", method = RequestMethod.GET)
	public HttpBaseDto getLocationFootprint(@PathVariable String token,
			@RequestParam(value = "type", required = false) String type) {
		Long user_id = checkTokenAndUser(token);
		List<Location> locationList = locationService.getFootprint(user_id, type);
		List<Map<String, Object>> dataList = new LinkedList<Map<String, Object>>();
		if (locationList != null) {
			for (Location location : locationList) {
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("lat", location.getLat());
				dataMap.put("lng", location.getLng());
				dataMap.put("timestamp", location.getUpload_time().getTime());
				dataList.add(dataMap);
			}
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(dataList);
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/getlocationInfo", method = RequestMethod.POST)
	public String getlocationInfo(@RequestBody String body) {
		JSONObject bb = new JSONObject();

		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String userId = checkTokenWatchAndUser(token);

		// loginId，mapType, lat,lng
		if ("0".equals(userId)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		String mapType = jsonObject.getString("mapType");
		String lat = jsonObject.getString("lat");
		String lng = jsonObject.getString("lng");
		
		if (!"0.000000".equals(lat) && !"0.000000".equals(lng)) {
			String url = "https://restapi.amap.com/v3/geocode/regeo?key=7d92f6b57a23743f6939c24714731a6a&location=" + lng + "," + lat;
			String res = HttpClientGet.get(url);
			/*

{"status":"1",
"regeocode":{"addressComponent": 
               {"city":"深圳市","province":"广东省","adcode":"440306","district":"宝安区","towncode":"440306003000",
               "streetNumber":{"number":"27号","location":"113.873493,22.5679314","direction":"西南","distance":"7.67614","street":"华侨新村西堤一巷"},
               "country":"中国","township":"西乡街道",
               "businessAreas":[{"location":"113.8702516298224,22.58332089537708","name":"西乡","id":"440306"},
               {"location":"113.89640616362594,22.570097072405947","name":"新安","id":"440306"},
               {"location":"113.89536002134635,22.560183159277475","name":"翻身路","id":"440306"}],
               "building":{"name":[],"type":[]},"neighborhood":{"name":"安泰花园","type":"商务住宅;住宅区;住宅小区"},
               "citycode":"0755"},
               "formatted_address":"广东省深圳市宝安区西乡街道安泰花园金华大酒店"},"info":"OK","infocode":"10000"}
               
               
               地址信息返回格式：{ "Code":"1",
               "Province":"广东省",
               "City":"深圳市","District":"宝安区",
               "Road":"华侨新村西堤一巷27号",
               "Nearby":[{"POI":"广东省深圳市宝安区西乡街道安泰花园金华大酒店"}],
               "Address":"广东省深圳市宝安区华侨新村西堤一巷27号,广东省深圳市宝安区西乡街道安泰花园金华大酒店" }
               
               
               
               {"Road":"华侨新村西堤一巷27号","Nearby":"","City":"深圳市","POI":"","Code":0,"District":"宝安区","Province":"广东省"}
*/
			JSONArray jsonArray = new JSONArray();
			JSONObject bb1 = new JSONObject();
			
			JSONObject json = JSON.parseObject(res);
	        if (json == null || json.getIntValue("status") != 1) {
	        	logger.error("调用逆地理编码接口失败, 坐标：{},{} 内容：{}"+ lat+ ","+lng+ ","+res);
	        	bb.put("Code", 0);
	        	bb.put("Province", "");
	        	bb.put("City", "");
	        	bb.put("District", "");
	        	bb.put("Road", "");
	        	
	        	
	        	bb1.put("POI", "");
	        	jsonArray.add(bb1);
	        	bb.put("Nearby", jsonArray);
	        	bb.put("Address", "");
	        	
	        }
	        JSONObject regeoCode = json.getJSONObject("regeocode");
	        JSONObject area = regeoCode.getJSONObject("addressComponent");
	        
	        bb.put("Code", 1);
	        bb.put("Province", cleanContent(area.getString("province")));
        	bb.put("City", cleanContent(area.getString("city")));
        	bb.put("District", cleanContent(area.getString("district")));
        	 JSONObject street = area.getJSONObject("streetNumber");
        	 
        	bb.put("Road", cleanContent(street.getString("street") + street.getString("number")));
        	bb.put("Nearby", jsonArray);
        	
        	bb1.put("POI", cleanContent(regeoCode.getString("formatted_address")));
        	jsonArray.add(bb1);
        	bb.put("Nearby", jsonArray);
        	
        	bb.put("Address", cleanContent(area.getString("province"))+cleanContent(area.getString("city"))+cleanContent(area.getString("district"))+cleanContent(street.getString("street") + street.getString("number"))+","+cleanContent(regeoCode.getString("formatted_address")));
		} else {
			bb.put("Code", 5);
		}

		return bb.toString();
	}

	 private String cleanContent(String cont) {
	        return cont == null ? cont : cont.replaceAll("[\\{\\}\\[\\] ,\"']+", "");
	    }
	 
	/*--下面是老人功能机的接口-------------------------------------------------------------------------------------------------*/
	/* 上传定位 */
	@ResponseBody
	@RequestMapping(value = "/oldlocation", method = RequestMethod.POST)
	public String oldLocation(@RequestBody String json) {
		JSONObject bb = new JSONObject();

		logger.info(json);
		// 手机号|bts
		String[] aa = json.toString().split("\\|");

		String phone = aa[0];
		String bts = aa[1];
		logger.info(phone);
		logger.info(bts);
		String lat = null;
		String lng = null;

		StringBuilder myurlBuilder = new StringBuilder(
				"http://apilocate.amap.com/position?key=b4a2748e41314ae117645aa9589c6723&output=json&accesstype=0&cdma=0&network=0&bts=");
		myurlBuilder.append(bts);
		myurlBuilder.append("&nearbts=0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0");

		logger.info(myurlBuilder.toString());
		String responseJsonString = HttpClientGet.urlReturnParamsAs(myurlBuilder.toString());
		if (responseJsonString != null) {
			JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonString);
			String status = responseJsonObject.getString("status");
			String info = responseJsonObject.getString("info");

			if ("1".equals(status)) {
				JSONObject resultJsonObject = responseJsonObject.getJSONObject("result");
				if (resultJsonObject != null) {
					String location = resultJsonObject.getString("location");
					if (location != null) {
						String[] arr = location.split(",");
						if (arr.length == 2) {
							lat = arr[1];
							lng = arr[0];

							locationService.insertOldLocation(phone, lat, lng);
							bb.put("resultCode", 1);
						}
					}
				}
			} else {
				bb.put("resultCode", 0);
			}
		} else {
			bb.put("resultCode", -1);
		}

		return bb.toString();
	}

	/* app查询最新定位 */
	@ResponseBody
	@RequestMapping(value = "/searchOldLocation", method = RequestMethod.POST)
	public String searchOldLocation(@RequestBody String phone) {
		logger.info(phone);

		LocationOld locationOld = locationService.getOldLocationLatest(phone);

		JSONObject bb = new JSONObject();
		if (locationOld != null) {
			bb.put("lat", locationOld.getLat());
			bb.put("lng", locationOld.getLng());
			bb.put("uploadtime", locationOld.getUpload_time());
			bb.put("codes", 1);
		} else {
			bb.put("codes", 0);
		}
		return bb.toString();
	}

	/* 查询轨迹 */
	@ResponseBody
	@RequestMapping(value = "/searchLocationTrack/{phone}/{starttime}/{endtime}", method = RequestMethod.GET)
	public String searchLocationTrack(@PathVariable String phone, @PathVariable String starttime,
			@PathVariable String endtime) {
		JSONObject bb = new JSONObject();
		List<LocationOld> locationList = locationService.getOldPhoneFootprint(phone, starttime, endtime);
		JSONArray jsonArray = new JSONArray();
		if (locationList != null) {
			for (LocationOld location : locationList) {
				JSONObject dataMap = new JSONObject();
				dataMap.put("lat", location.getLat());
				dataMap.put("lng", location.getLng());
				dataMap.put("timestamp", location.getUpload_time().getTime());
				jsonArray.add(dataMap);
			}
			bb.put("codes", 1);
		} else {
			bb.put("codes", 0);
		}

		bb.put("result", jsonArray);
		return bb.toString();
	}

	/* 绑定，解绑，更改绑定昵称，查询轨迹，查询绑定设备 */
	@ResponseBody
	@RequestMapping(value = "/oldphone/bind", method = RequestMethod.POST)
	public String oldphoneBind(@RequestBody String body) {

		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String name = jsonObject.getString("name");
		String phone = jsonObject.getString("phone");
		String imei = jsonObject.getString("imei");
		logger.info("绑定名称=" + name);
		JSONObject bb = new JSONObject();

		OldBindDevice olddevice = userInfoService.getOldDevice(phone, imei);
		if (olddevice != null) {
			bb.put("codes", 0);
		} else {
			userInfoService.insertBindOldDevice(phone, imei, name);
			bb.put("codes", 1);
		}
		return bb.toString();
	}

	/* 查询绑定设备 */
	@ResponseBody
	@RequestMapping(value = "/searchBindDevice/{phone}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String searchLocationTrack(@PathVariable String phone) {
		JSONObject bb = new JSONObject();
		List<OldBindDevice> locationList = userInfoService.getOldPhoneDeviceBind(phone);
		JSONArray jsonArray = new JSONArray();
		if (locationList != null) {
			for (OldBindDevice location : locationList) {
				JSONObject json = new JSONObject();
				json.put("id", location.getId());
				json.put("imei", location.getImei());
				json.put("name", location.getName());
				json.put("timestamp", location.getUpload_time().getTime());
				logger.info(json + "");
				jsonArray.add(json);
			}
			bb.put("codes", 1);
		} else {
			bb.put("codes", 0);
		}
		bb.put("result", jsonArray);
		return bb.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/unOldBindDevice/{id}", method = RequestMethod.GET)
	public String unOldBindDevice(@PathVariable Long id) {
		JSONObject bb = new JSONObject();
		userInfoService.deleteDeviceBind(id);
		bb.put("codes", 1);

		return bb.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/oldPhoneUpdate", method = RequestMethod.POST)
	public String oldPhoneUpdate(@RequestBody String body) {
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String name = jsonObject.getString("name");
		Long id = jsonObject.getLong("id");
		logger.info("更改绑定名称=" + name);
		JSONObject bb = new JSONObject();
		userInfoService.updateOldBindDeviceInfo(id, name);
		bb.put("codes", 1);
		return bb.toString();
	}

}
