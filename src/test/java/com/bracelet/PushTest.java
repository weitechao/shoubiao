package com.bracelet;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.bracelet.dto.OpenDoorDto;
import com.bracelet.dto.SosDto;
import com.bracelet.util.PushUtil;

public class PushTest {
    public static void main(String[] args) {
        // PushRequest pushRequest = new PushRequest();
        // pushRequest.setAppKey(24620906L);
        // pushRequest.setTargetValue("");
    /*    SosDto dto = new SosDto();
        dto.setLat("22.22222");
        dto.setLng("138.13838");
        dto.setTimestamp(System.currentTimeMillis());
        System.out.println(JSON.toJSONString(dto));

        PushUtil.push("013EBDFEBB939779125038F8B4015DD3", "66666666666", JSON.toJSONString(dto), "测试");*/
        
		OpenDoorDto sosDto = new OpenDoorDto();
		sosDto.setName("11");
		sosDto.setImei("4568456456");
		sosDto.setTimestamp(new Date().getTime());
		sosDto.setSide(1);
		sosDto.setWay(0);
		sosDto.setContent("123123");
	
		String title = "开锁";
		String content = JSON.toJSONString(sosDto);
		PushUtil.push("013EBDFEBB939779125038F8B4015DD3", title, content, "66666666666");
    }
}
