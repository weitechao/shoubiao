package com.bracelet.socket.business;

import io.netty.channel.Channel;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;

public interface IService {

	SocketBaseDto process(JSONObject jsonObject, Channel incoming);
	
	String process(String jsonInfo, Channel channel);
	
}
