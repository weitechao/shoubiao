package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.Location;
import com.bracelet.entity.LocationOld;
import com.bracelet.entity.LocationRequest;
import com.bracelet.entity.OldBindDevice;
import com.bracelet.entity.Step;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.VersionInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IStepService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.WatchSetService;
import com.bracelet.socket.BaseChannelHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.RadixUtil;
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
@RequestMapping("/watchuser")
public class WatchAppMakeDeviceUpdateVersionController extends BaseController {

	@Autowired
	ILocationService locationService;
	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IStepService stepService;
	@Autowired
	WatchSetService  watchSetService;
	@Resource
	BaseChannelHandler baseChannelHandler;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
//, produces = "text/html;charset=UTF-8"
	/* 远程升级 */
	@ResponseBody
	@RequestMapping(value = "/controllerVersion/{token}/{imei}", method = RequestMethod.GET)
	public String controllerVersion(@PathVariable String token,
			@PathVariable String imei) {
		Integer setStatus=null;
		JSONObject bb = new JSONObject();
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 0);
			setStatus = 0;
			watchSetService.insertInfo(imei,setStatus,1);
			return bb.toString();
		}
		String reps = "[YW*"+imei+"*0001*0039*UPGRADE,";
		if (socketLoginDto.getChannel().isActive()) {
			VersionInfo vinfo = userInfoService.getVersionInfo();
			if (vinfo != null) {
				reps = reps + vinfo.getVersion() + ","
						+ vinfo.getDownload_path() + "]";
			} else {
				reps = reps + "1234,http://www.baidu.com/updata.bin]";
			}
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
			setStatus=1;
		} else {
			setStatus=0;
			bb.put("Code", 0);
		}
		watchSetService.insertInfo(imei,setStatus,1);
		return bb.toString();
	}

	/* 短信指令 */
	@ResponseBody
	@RequestMapping(value = "/smsCmd", method = RequestMethod.POST)
	public String smsCmd(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		
		String imei = jsonObject.getString("imei");
		String operatorNumber = jsonObject.getString("operatorNumber");
		String content = jsonObject.getString("content");
		
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			watchSetService.insertSmsSetLogInfo(imei, 0,operatorNumber, content);//0表示未发送
			//String imei, Integer setStatus,String operatorNumber, String content
			bb.put("Code", 4);
			return bb.toString();
		}
		String reps = "[YW*"+imei+"*0001*";
		if (socketLoginDto.getChannel().isActive()) {
			String msg = "COST1,"+ operatorNumber + "," + content ;
			reps=reps+RadixUtil.changeRadix(msg)+"*"+msg+ "]";
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
			watchSetService.insertSmsSetLogInfo(imei, 1,operatorNumber, content);
		} else {
			watchSetService.insertSmsSetLogInfo(imei, 0,operatorNumber, content);
			bb.put("Code", 0);
		}
		return bb.toString();
	}
	
	/* IP端口设置 */
	@ResponseBody
	@RequestMapping(value = "/setIp", method = RequestMethod.POST)
	public String setIp(@RequestBody String body) {
		JSONObject bb = new JSONObject();
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String token = jsonObject.getString("token");
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		String imei = jsonObject.getString("imei");
		String ip = jsonObject.getString("ip");
		String port = jsonObject.getString("port");
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 0);
			return bb.toString();
		}
		String reps = "[YW*"+imei+"*0001*0014*IP,";
		if (socketLoginDto.getChannel().isActive()) {
			reps = reps + ip + "," + port + "]";
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
		} else {
			bb.put("Code", 2);
		}
		return bb.toString();
	}
	
	//恢复出厂设置
	@ResponseBody
	@RequestMapping(value = "/factory/{token}/{imei}", method = RequestMethod.GET)
	public String factory(@PathVariable String token,
			@PathVariable String imei) {
		JSONObject bb = new JSONObject();
		
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 4);
			return bb.toString();
		}
		String reps = "[YW*"+imei+"*0001*0007*FACTORY]";
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}
	
	//恢复出厂设置不用token的
		@ResponseBody
		@RequestMapping(value = "/factoryNoToken/{imei}", method = RequestMethod.GET)
		public String factoryNoToken(@PathVariable String imei) {
			JSONObject bb = new JSONObject();
			SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
			if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
				bb.put("Code", 4);
				return bb.toString();
			}
			String reps = "[YW*"+imei+"*0001*0007*FACTORY]";
			if (socketLoginDto.getChannel().isActive()) {
				socketLoginDto.getChannel().writeAndFlush(reps);
				bb.put("Code", 1);
			} else {
				bb.put("Code", 0);
			}
			return bb.toString();
		}
	
	/* APN设置 */
	@ResponseBody
	@RequestMapping(value = "/setApnInfo", method = RequestMethod.POST)
	public String setApn(@RequestBody String body) {
		JSONObject jsonObject = (JSONObject) JSON.parse(body);
		String imei = jsonObject.getString("imei");
		String apnName = jsonObject.getString("apn_name");
		String username = jsonObject.getString("username");
		String password = jsonObject.getString("password");
		String data = jsonObject.getString("data");
		JSONObject bb = new JSONObject();
		Integer setStatus = null;
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			setStatus = 0;
			bb.put("Code", 0);
			watchSetService.insertApnSetLog(imei,apnName,username,password,data,setStatus);
			return bb.toString();
		}
		String reps = "[YW*"+imei+"*0001*0011*APN,";
		if (socketLoginDto.getChannel().isActive()) {
			reps = reps + apnName + Utils.dou + username + Utils.dou +password + Utils.dou + data + Utils.you;
			socketLoginDto.getChannel().writeAndFlush(reps);
			setStatus = 1;
			bb.put("Code", 1);
		} else {
			setStatus = 0;
			bb.put("Code", 0);
		}
		watchSetService.insertApnSetLog(imei,apnName,username,password,data,setStatus);
		return bb.toString();
	}
	
	/* 参数查询 */
	@ResponseBody
	@RequestMapping(value = "/queryParameter/{token}/{imei}", method = RequestMethod.GET)
	public String queryParameter(@PathVariable String token,@PathVariable String imei){
		JSONObject bb = new JSONObject();
		
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 4);
			return bb.toString();
		}
		String reps = "[YW*"+imei+"*0001*0002*TS]";
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
	}
	
	
	//重启
	@ResponseBody
	@RequestMapping(value = "/reset/{token}/{imei}", method = RequestMethod.GET)
	public String reset(@PathVariable String token,
			@PathVariable String imei) {
		JSONObject bb = new JSONObject();
		
		String user_id = checkTokenWatchAndUser(token);
		if ("0".equals(user_id)) {
			bb.put("Code", -1);
			return bb.toString();
		}
		
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(imei);
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			bb.put("Code", 2);
			return bb.toString();
		}
		String reps = "[YW*"+imei+"*0001*0005*RESET]";
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(reps);
			bb.put("Code", 1);
		} else {
			bb.put("Code", 0);
		}
		return bb.toString();
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
		myurlBuilder
				.append("&nearbts=0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0|0,0,0,0,0");

		logger.info(myurlBuilder.toString());
		String responseJsonString = HttpClientGet
				.urlReturnParamsAs(myurlBuilder.toString());
		if (responseJsonString != null) {
			JSONObject responseJsonObject = (JSONObject) JSON
					.parse(responseJsonString);
			String status = responseJsonObject.getString("status");
			String info = responseJsonObject.getString("info");

			if ("1".equals(status)) {
				JSONObject resultJsonObject = responseJsonObject
						.getJSONObject("result");
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
	public String searchLocationTrack(@PathVariable String phone,
			@PathVariable String starttime, @PathVariable String endtime) {
		JSONObject bb = new JSONObject();
		List<LocationOld> locationList = locationService.getOldPhoneFootprint(
				phone, starttime, endtime);
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
		List<OldBindDevice> locationList = userInfoService
				.getOldPhoneDeviceBind(phone);
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
