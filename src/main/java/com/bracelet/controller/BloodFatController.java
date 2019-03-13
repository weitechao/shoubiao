package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.LatestBloodFatDto;
import com.bracelet.dto.LatestBloodSugarDto;
import com.bracelet.dto.OpenDoorDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.BloodFat;
import com.bracelet.entity.BloodSugar;
import com.bracelet.service.IBloodFatService;
import com.bracelet.service.IBloodSugarService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.HostUtil;
import com.bracelet.util.HttpClientGet;
import com.bracelet.util.IOSPushUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/bloodFat")
public class BloodFatController extends BaseController {
	@Autowired
	IBloodFatService bloodFatService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@RequestMapping("/search/latest/{token}")
	public HttpBaseDto getLatestBloodFat(@PathVariable String token) {

		/*
		 * for(int i=0;i<3;i++){ String
		 * url="http://localhost:8080//bloodFat/search/latest/"+token;
		 * System.out.println("URL="+url); String respon=HttpClientGet.get(url);
		 * System.out.println("返回数据="+respon); }
		 */
		Long user_id = checkTokenAndUser(token);
		BloodFat bloodFat = bloodFatService.getLatest(user_id);
		LatestBloodFatDto latestBloodSugarDto = null;

		if (bloodFat != null) {
			latestBloodSugarDto = new LatestBloodFatDto();
			latestBloodSugarDto.setBloodFat(bloodFat.getBlood_fat());
			latestBloodSugarDto.setTimestamp(bloodFat.getUpload_time().getTime());
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(latestBloodSugarDto);
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/getHost", method = RequestMethod.GET)
	public String getHost(){
		return HostUtil.get();
	}
	
	@ResponseBody
	@RequestMapping(value = "/pushTest", method = RequestMethod.GET)
	public Integer pushTest(){
		
		 
		OpenDoorDto sosDto = new OpenDoorDto();
		sosDto.setName("11");
		sosDto.setImei("4568456456");
		sosDto.setTimestamp(System.currentTimeMillis());
		sosDto.setSide(1);
		sosDto.setWay(0);
		sosDto.setContent("123123");
	
		String title = "哈哈哈哈";
		String content = JSON.toJSONString(sosDto);
		IOSPushUtil.push("7E26A2307007D0A762FFDB08D9BA6096", title, content, "77777777777777");
		return 1;
	}
	

}
