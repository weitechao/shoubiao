package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.GPS;
import com.bracelet.entity.Location;
import com.bracelet.entity.LocationOld;
import com.bracelet.entity.LocationRequest;
import com.bracelet.entity.LocationWatch;
import com.bracelet.entity.OldBindDevice;
import com.bracelet.entity.Step;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.VersionInfo;
import com.bracelet.entity.WatchFriend;
import com.bracelet.exception.BizException;
import com.bracelet.redis.LimitCache;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IStepService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.WatchFriendService;
import com.bracelet.service.WatchSetService;
import com.bracelet.service.WatchTkService;
import com.bracelet.socket.BaseChannelHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.GPSConverterUtils;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.RanomUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class PosController extends BaseController {
	@Autowired
	WatchTkService watchtkService;
	@Autowired
	ILocationService locationService;

	@Autowired
	LimitCache limitCache;

	private Logger logger = LoggerFactory.getLogger(getClass());

	
	//@RequestMapping(value = "/position/query", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	
	@ResponseBody
	@RequestMapping(value = "/position/query", method = RequestMethod.GET, produces="text/html;charset=UTF-8")
	public String getAuthCode(@RequestParam(value = "lbs") String lbs,
			@RequestParam(value = "deviceId") String deviceId, @RequestParam(value = "wifi") String wifi) {
		logger.info("lbs=" + lbs);
		logger.info("wifi=" + wifi);
		logger.info("deviceId=" + deviceId);
		JSONObject bb = new JSONObject();
		JSONObject dataMap = new JSONObject();
		if (!"1".equals(wifi) && wifi != null && !"".equals(wifi)) {
			String mmac = "";
			String macs = "";
			String[] wifis = StringUtils.split(wifi, '|');
			for (int i = 0; i < wifis.length; i++) {
				if (i == 0) {
					mmac = wifis[0];
				} else {
					if (i > 1) {
						macs = macs + "|";
					}
					macs = macs + wifis[i];
				}
			}
			String url = "http://apilocate.amap.com/position?key=93b4cf92ab27576506c6ea1edbe8bb54&output=json&accesstype=1&mmac="
					+ mmac + "&macs=" + macs;
			logger.info("wifiURL=" + url);

			String res = HttpClientGet.get(url);
			logger.info("第三方wifi定位结果：{}", res);
			JSONObject json = JSON.parseObject(res);

			if (json != null && json.getIntValue("status") == 1) {
				JSONObject jo = json.getJSONObject("result");
				String loc = jo.getString("location");
				String[] its = StringUtils.split(loc, ',');
				GPS gps = GPSConverterUtils.gcj_To_Gps84(new BigDecimal(its[1]).doubleValue(),
						new BigDecimal(its[0]).doubleValue());
				
				dataMap.put("lat", gps.getLat());
				dataMap.put("lng", gps.getLon());
				dataMap.put("radius", jo.getIntValue("radius"));
				dataMap.put("address", jo.getString("desc"));
			
			}
		} else {
			String[] lbss = StringUtils.split(lbs, '|');
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < lbss.length; i++) {
				if (i == 0) {
					String[] bts = StringUtils.split(lbss[0], ',');
					if (Integer.valueOf(bts[4]) > 0) {
						bts[4] = (Integer.valueOf(bts[4]) * 2 - 113) + "";
					}
					sb.append(bts[0] + "," + bts[1] + "," + bts[2] + "," + bts[3] + "," + bts[4]);
					sb.append("&nearbts=");
				} else {
					/*
					 * if(lbs.length>1){ sb.append("|"); }
					 */
					String[] bts = StringUtils.split(lbss[i], ',');
					if (Integer.valueOf(bts[4]) > 0) {
						bts[4] = (Integer.valueOf(bts[4]) * 2 - 113) + "";
					}
					sb.append(bts[0] + "," + bts[1] + "," + bts[2] + "," + bts[3] + "," + bts[4]);
				}
			}
			sb.append("&imei=" + deviceId);
			String res = HttpClientGet.get(
					"http://apilocate.amap.com/position?key=93b4cf92ab27576506c6ea1edbe8bb54&accesstype=0&cdma=0&network=GSM&bts="
							+ sb.toString());
			logger.info("第三方定位结果：{}", res);
			JSONObject json = JSON.parseObject(res);
			if (json != null && json.getIntValue("status") == 1) {
				JSONObject jo = json.getJSONObject("result");
				String loc = jo.getString("location");

				String[] its = StringUtils.split(loc, ',');

				GPS gps = GPSConverterUtils.gcj_To_Gps84(new BigDecimal(its[1]).doubleValue(),
						new BigDecimal(its[0]).doubleValue());
				
				dataMap.put("lat", gps.getLat());
				dataMap.put("lng", gps.getLon());
				dataMap.put("radius", jo.getIntValue("radius"));
				dataMap.put("address", jo.getString("desc"));
			}

		}
		bb.put("code", 1);
		bb.put("msg", "成功!");
		bb.put("body", dataMap);
		return bb.toString();
	}


	@ResponseBody
	@RequestMapping(value = "/getLocation", method = RequestMethod.GET)
	public String getlocationInfo() {
		JSONObject bb = new JSONObject();
		LocationWatch lo = locationService.getLatest("872018040204007");
		bb.put("lng", lo.getLng());
		bb.put("lat", lo.getLat());
		bb.put("updatetime", Utils.format14DateString(lo.getUpload_time().getTime()));
		return bb.toString();
	}

	@ResponseBody
	@RequestMapping(value = "/redisTest", method = RequestMethod.GET)
	public String redisTest() {
		boolean e = limitCache.addKey("e", "1");
		boolean f = limitCache.addKey("f", "1");
		boolean b = limitCache.addKey("a", "1");
		logger.info("b=" + b);
		b = limitCache.deleteKey("a");
		logger.info("b=" + b);
		Long size = limitCache.getSize();
		logger.info("size=" + size);
		return "1";
	}

}
