package com.bracelet.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.LoggerFactory;

import com.bracelet.dto.SocketLoginDto;
import com.bracelet.dto.TianQiLatest;
import com.bracelet.dto.WatchLatestLocation;

import ch.qos.logback.classic.Logger;

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

	private static Map<String, TianQiLatest> channelMap5 = new HashMap<String, TianQiLatest>();

	private static Map<String, byte[]> byteMap = new HashMap<String, byte[]>();

	private static Map<String, Integer> IntegerMap = new HashMap<String, Integer>();

	private static Map<String, String> contentMap = new HashMap<String, String>();
	
	//app 发送的 语音map
	private static Map<String, byte[]> appVoiceByteMap = new HashMap<String, byte[]>();
	// 设备语音map 和图片map
	private static Map<String, byte[]> deviceVoiceByteMap = new HashMap<String, byte[]>();
	
	private static Map<String, byte[]> devicePhotoByteMap = new HashMap<String, byte[]>();
	
	
	public static void addDeviceVoiceByte(String deviceVoiceName, byte[] voiceByte) {
		deviceVoiceByteMap.put(deviceVoiceName, voiceByte);
	}

	public static byte[] getDeviceVoiceByte(String deviceVoiceName) {
		return deviceVoiceByteMap.get(deviceVoiceName);
	}

	public static void removeDeviceVoiceByte(String deviceVoiceName) {
		deviceVoiceByteMap.remove(deviceVoiceName);
	}
	
	
	public static void addDevicePhtotByte(String devicePhotoName, byte[] voiceByte) {
		devicePhotoByteMap.put(devicePhotoName, voiceByte);
	}

	public static byte[] getDevicePhtotByte(String devicePhotoName) {
		return devicePhotoByteMap.get(devicePhotoName);
	}

	public static void removeDevicePhtotByte(String devicePhotoName) {
		devicePhotoByteMap.remove(devicePhotoName);
	}
	

	/**
	 * 
	 * add channelInfo
	 * 
	 * @param imei
	 * @param dto
	 * @return void @exception
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
			String imei = dto.getImei();
			SocketLoginDto socketLoginDto = getChannel(imei);
			if (socketLoginDto != null) {
				if ((socketLoginDto.getChannel().remoteAddress() + "").equals(channel.remoteAddress() + "")) {
					channelMap2.remove(imei);
				}
			}
		}
		channelMap3.remove(channel);
	}

	public static void addCityQianQi(String city, TianQiLatest dto) {
		channelMap5.put(city, dto);
	}

	public static TianQiLatest getCityTianQi(String city) {
		return channelMap5.get(city);
	}

	public static void addContent(String imei, String voiceName) {
		contentMap.put(imei, voiceName);
	}

	public static String getContent(String imei) {
		return contentMap.get(imei);
	}

	public static void removeContent(String imei) {
		contentMap.remove(imei);
	}

	public static void addbyte(String imei, byte[] voiceName) {
		byteMap.put(imei, voiceName);
	}

	public static byte[] getByte(String imei) {
		return byteMap.get(imei);
	}

	public static void removeByte(String imei) {
		byteMap.remove(imei);
	}

	public static void addInteger(String imei, Integer cout) {
		IntegerMap.put(imei, cout);
	}

	public static Integer getInteger(String imei) {
		return IntegerMap.get(imei);
	}

	public static void removeInteger(String imei) {
		IntegerMap.remove(imei);
	}

	public static void removeAll(String remoteAddress) {
		IntegerMap.remove(remoteAddress + "_len");
		byteMap.remove(remoteAddress + "_byte");
		contentMap.remove(remoteAddress + "_voice");
	}

	/*
	 * public static void addEnergy(String imei, String energy) {
	 * energyMap.put(imei, energy); }
	 * 
	 * public static void getEnergy(String imei) { energyMap.get(imei); }
	 */
	
	public static void addvoicebyte(String voiceName, byte[] voiceByte) {
		appVoiceByteMap.put(voiceName, voiceByte);
	}

	public static byte[] getAppVoiceByte(String voiceName) {
		return appVoiceByteMap.get(voiceName);
	}

	public static void removeVoiceByte(String voiceName) {
		appVoiceByteMap.remove(voiceName);
	}


	

}
