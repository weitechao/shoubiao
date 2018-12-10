package com.bracelet.util;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

import com.bracelet.dto.SocketLoginDto;
import com.bracelet.dto.TianQiLatest;
import com.bracelet.dto.WatchLatestLocation;

public class ChannelMap {
	private ChannelMap() {
	}

	/**
	 * key:imei,value:SocketLoginDto
	 */
	private static Map<String, SocketLoginDto> channelMap2 = new HashMap<String, SocketLoginDto>();

	/**
	 * key:channel,value:SocketLoginDto
	 */
	private static Map<Channel, SocketLoginDto> channelMap3 = new HashMap<Channel, SocketLoginDto>();
	
	
	private static Map<String, WatchLatestLocation> channelMap4 = new HashMap<String, WatchLatestLocation>();
	
	private static Map<String, TianQiLatest> channelMap5 = new HashMap<String, TianQiLatest>();
	
	private static Map<String, String> voiceNameMap = new HashMap<String, String>();
	
	private static Map<String, byte[]> byteMap = new HashMap<String, byte[]>();
	
	//private static Map<String, String> photoNameMap = new HashMap<String, String>();

	/**
	 * 
	 * add channelInfo
	 * 
	 * @param imei
	 * @param dto
	 * @return void
	 * @exception
	 */
	public static void addChannel(String imei, SocketLoginDto dto) {
		channelMap2.put(imei, dto);
	}

	public static void addChannel(Channel channel, SocketLoginDto dto) {
		channelMap3.put(channel, dto);
	}

	public static SocketLoginDto getChannel(Channel channel) {
		return channelMap3.get(channel);
	}

	public static void removeChannel(String imei) {
		SocketLoginDto dto = channelMap2.get(imei);
		if (dto != null) {
			channelMap3.remove(dto.getChannel());
		}
		channelMap2.remove(imei);
	}

	public static SocketLoginDto getChannel(String imei) {
		return channelMap2.get(imei);
	}

	public static void removeChannel(Channel channel) {
		SocketLoginDto dto = channelMap3.get(channel);
		if (dto != null) {
			channelMap2.remove(dto.getImei());
		}

		channelMap3.remove(channel);
	}
	
	
	public static void addlocation(String imei, WatchLatestLocation dto) {
		channelMap4.put(imei, dto);
	}


	public static WatchLatestLocation getlocation(String imei) {
		return channelMap4.get(imei);
	}
	
	
	public static void addCityQianQi(String city, TianQiLatest dto) {
		channelMap5.put(city, dto);
	}


	public static TianQiLatest getCityTianQi(String city) {
		return channelMap5.get(city);
	}
	
	public static void addVoiceName(String imei, String voiceName) {
		voiceNameMap.put(imei, voiceName);
	}

	public static String getVoiceName(String imei) {
		return voiceNameMap.get(imei);
	}
	
	
	
	public static void addbyte(String imei, byte[] voiceName) {
		byteMap.put(imei, voiceName);
	}

	public static byte[] getByte(String imei) {
		return byteMap.get(imei);
	}
	
	/*public static void addPhotoName(String imei, String photoName) {
		photoNameMap.put(imei, photoName);
	}

	public static String getPhotoName(String imei) {
		return photoNameMap.get(imei);
	}*/
}
