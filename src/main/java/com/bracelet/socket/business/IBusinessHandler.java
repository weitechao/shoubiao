package com.bracelet.socket.business;

import io.netty.channel.Channel;

public interface IBusinessHandler {
	
	void process(String json, Channel incoming);
	
}
