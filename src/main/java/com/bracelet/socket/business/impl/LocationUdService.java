package com.bracelet.socket.business.impl;


import io.netty.channel.Channel;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.service.ILocationService;
import com.bracelet.service.IVoltageService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.HttpClientGet;

@Service("locationUdService")
//public class LocationUdService extends AbstractBizService {
	public class LocationUdService implements IService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	ILocationService locationService;
	@Autowired
	IVoltageService voltageService;
	/*@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {
		
		logger.info("位置数据上报:" +jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String cmd = shuzu[0];
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		String infoo = shuzu[4];
        logger.info("imei="+imei+",info="+infoo+",no="+no);
		String[] infoshuzu = infoo.split(",");
		String locationis = infoshuzu[3];//A定位 V不定位
		String time = infoshuzu[1]+"-"+infoshuzu[2];
		if("A".equals(locationis)){
			String lat=infoshuzu[4];//纬度
			String lon=infoshuzu[6];  //经度
			String status = infoshuzu[16];
			String energy = infoshuzu[13];
			 logger.info("imei="+imei+",lat="+lat+",lon="+lon+",time="+time+",status="+status+",energy="+energy);	
			locationService.insertUdInfo(imei,1,lat,lon,status,time);	
			voltageService.insertDianLiang(imei, Integer.valueOf(energy));
		}else if("V".equals(locationis)){
			UD,11102018,142013,V,0.000000,N,
			0.000000,E,0.00,0.0,0.0,0,100,100,0,0:0,00000000,6,1,
			460,0,          19
			10173,4934,49,
			10173,4263,34,
			10173,4941,34,
			10173,4931,31,
			10173,4943,27,
			10173,4582,27,
			0
		
			String aab="460,0,";
			StringBuffer sbb=new StringBuffer();
			
			if(Integer.valueOf(infoshuzu[23])>30){
				sbb.append("bts=");
				sbb.append(aab);
				sbb.append(infoshuzu[21]).append(",").append(infoshuzu[22]).append(",").append((Integer.valueOf(infoshuzu[23])*2-113)+"");
			}
			StringBuffer sb=new StringBuffer();
			sb.append("&nearbts=");
			if(Integer.valueOf(infoshuzu[26])>30){
				sb.append(aab);
				sb.append(infoshuzu[24]).append(",").append(infoshuzu[25]).append(",").append((Integer.valueOf(infoshuzu[26])*2-113)+"");
			}
			if(Integer.valueOf(infoshuzu[29])>30){
				sb.append("|");
				sb.append(aab);
				sb.append(infoshuzu[27]).append(",").append(infoshuzu[28]).append(",").append((Integer.valueOf(infoshuzu[29])*2-113)+"");
			}
			if(Integer.valueOf(infoshuzu[32])>30){
				sb.append("|");
				sb.append(aab);
				sb.append(infoshuzu[30]).append(",").append(infoshuzu[31]).append(",").append((Integer.valueOf(infoshuzu[32])*2-113)+"");
			}
			if(Integer.valueOf(infoshuzu[35])>30){
				sb.append("|");
				sb.append(aab);
				sb.append(infoshuzu[33]).append(",").append(infoshuzu[34]).append(",").append((Integer.valueOf(infoshuzu[35])*2-113)+"");
			}
			if(Integer.valueOf(infoshuzu[38])>30){
				sb.append("|");
				sb.append(aab);
				sb.append(infoshuzu[36]).append(",").append(infoshuzu[37]).append(",").append((Integer.valueOf(infoshuzu[38])*2-113)+"");
			} 
			
			String url="http://apilocate.amap.com/position?key=b4a2748e41314ae117645aa9589c6723&output=json&accesstype=0&imsi=872018040204007&cdma=0&tel=13537596170&network=GSM&"+sbb.toString()+sb.toString();
			logger.info(url);
			String responseJsonString = HttpClientGet.urlReturnParams(url);
			
			if (responseJsonString != null) {
				JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonString);
				String status = responseJsonObject.getString("status");
				String info = responseJsonObject.getString("info");
				if ("1".equals(status)) {
					JSONObject resultJsonObject = responseJsonObject.getJSONObject("result");
					String location = resultJsonObject.getString("location");

					String[] arr = location.split(",");
					if (arr.length == 2) {
					String	lat = arr[1];
					String	lon = arr[0];
					locationService.insertUdInfo(imei,1,lat,lon,status,time);	
					}
				}
			}
		}
		if("AL".equals(cmd)){
			return "[YW*"+imei+"*0001*0002*AL]";
		}
		return "";
	}*/

	/*@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Override
	public SocketBaseDto process(JSONObject jsonObject, Channel incoming) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(String jsonInfo, Channel channel) {
		logger.info("位置数据上报:" +jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String cmd = shuzu[0];
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		String infoo = shuzu[4];
        logger.info("imei="+imei+",info="+infoo+",no="+no);
		String[] infoshuzu = infoo.split(",");
		String locationis = infoshuzu[3];//A定位 V不定位
		String time = infoshuzu[1]+"-"+infoshuzu[2];
		if("A".equals(locationis)){
			String lat=infoshuzu[4];//纬度
			String lon=infoshuzu[6];  //经度
			String status = infoshuzu[16];
			String energy = infoshuzu[13];
			 logger.info("imei="+imei+",lat="+lat+",lon="+lon+",time="+time+",status="+status+",energy="+energy);	
			locationService.insertUdInfo(imei,0,lat,lon,status,time);	
			voltageService.insertDianLiang(imei, Integer.valueOf(energy));
		}else if("V".equals(locationis)){
			/*UD,11102018,142013,V,0.000000,N,
			0.000000,E,0.00,0.0,0.0,0,100,100,0,0:0,00000000,6,1,
			460,0,          19
			10173,4934,49,
			10173,4263,34,
			10173,4941,34,
			10173,4931,31,
			10173,4943,27,
			10173,4582,27,
			0*/
		
			String aab="460,0,";
			StringBuffer sbb=new StringBuffer();
			
			//if(Integer.valueOf(infoshuzu[23])>30){
				sbb.append("bts=");
				sbb.append(aab);
				sbb.append(infoshuzu[21]).append(",").append(infoshuzu[22]).append(",").append((Integer.valueOf(infoshuzu[23])*2-113)+"");
			//}
			StringBuffer sb=new StringBuffer();
			sb.append("&nearbts=");
			if(Integer.valueOf(infoshuzu[26])>30){
				sb.append(aab);
				sb.append(infoshuzu[24]).append(",").append(infoshuzu[25]).append(",").append((Integer.valueOf(infoshuzu[26])*2-113)+"");
			}
			if(Integer.valueOf(infoshuzu[29])>30){
				sb.append("|");
				sb.append(aab);
				sb.append(infoshuzu[27]).append(",").append(infoshuzu[28]).append(",").append((Integer.valueOf(infoshuzu[29])*2-113)+"");
			}
			if(Integer.valueOf(infoshuzu[32])>30){
				sb.append("|");
				sb.append(aab);
				sb.append(infoshuzu[30]).append(",").append(infoshuzu[31]).append(",").append((Integer.valueOf(infoshuzu[32])*2-113)+"");
			}
			if(Integer.valueOf(infoshuzu[35])>30){
				sb.append("|");
				sb.append(aab);
				sb.append(infoshuzu[33]).append(",").append(infoshuzu[34]).append(",").append((Integer.valueOf(infoshuzu[35])*2-113)+"");
			}
			if(Integer.valueOf(infoshuzu[38])>30){
				sb.append("|");
				sb.append(aab);
				sb.append(infoshuzu[36]).append(",").append(infoshuzu[37]).append(",").append((Integer.valueOf(infoshuzu[38])*2-113)+"");
			} 
			
			String url="http://apilocate.amap.com/position?key=b4a2748e41314ae117645aa9589c6723&output=json&accesstype=0&imsi=872018040204007&cdma=0&tel=13537596170&network=GSM&"+sbb.toString()+sb.toString();
			logger.info(url);
			String responseJsonString = HttpClientGet.urlReturnParams(url);
			
			if (responseJsonString != null) {
				JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonString);
				String status = responseJsonObject.getString("status");
				//String info = responseJsonObject.getString("info");
				if ("1".equals(status)) {
					JSONObject resultJsonObject = responseJsonObject.getJSONObject("result");
					String location = resultJsonObject.getString("location");

					String[] arr = location.split(",");
					if (arr.length == 2) {
					String	lat = arr[1];
					String	lon = arr[0];
					locationService.insertUdInfo(imei,1,lat,lon,status,time);	
					}
				}
			}
		}
		
		
		String mmac="";
		String macs="";
		logger.info("info数组的长度="+infoshuzu.length);
		if(infoshuzu.length>39){
		
			Integer wifiCount =Integer.valueOf(infoshuzu[39]);
			logger.info("wifi长度="+wifiCount);
			if(wifiCount>0){
				for(int i=0;i<wifiCount*3;i=i+3){
					if(i==0){
						mmac=infoshuzu[39+2]+","+infoshuzu[39+3]+","+infoshuzu[39+1];
					}else{
						if(i>3){
							macs=macs+"|";
						}
						macs=macs+infoshuzu[39+2+i]+","+infoshuzu[39+3+i]+","+infoshuzu[39+1+i];
					}
				}
				String url="http://apilocate.amap.com/position?key=b4a2748e41314ae117645aa9589c6723&output=json&accesstype=1&mmac="+mmac+"&macs="+macs;
				logger.info(url);
				String responseJsonString = HttpClientGet.urlReturnParams(url);
				if (responseJsonString != null) {
					JSONObject responseJsonObject = (JSONObject) JSON.parse(responseJsonString);
					String status = responseJsonObject.getString("status");
					if ("1".equals(status)) {
						JSONObject resultJsonObject = responseJsonObject.getJSONObject("result");
						String location = resultJsonObject.getString("location");

						String[] arr = location.split(",");
						if (arr.length == 2) {
						String	lat = arr[1];
						String	lon = arr[0];
						locationService.insertUdInfo(imei,2,lat,lon,status,time);	
						}
					}
				}
				logger.info("mmac="+mmac);
				logger.info("macs="+macs);
			}
		}
		if("AL".equals(cmd)){
			return "[YW*"+imei+"*0001*0002*AL]";
		}
		return "";
		// TODO Auto-generated method stub
	}
	
	
	/*
	 * [YW*111111111111111*0002*008f*UD,220414,134652,A,22.571707,N,113.8613968,E,0.1,0.0,100,7,60,90,1000,50,0000,4,1,460,0,9360,4082,131,9360,4092,148,9360,4091,143,9360,4153,141] 

	[YW*111111111111111*0002*0066*UD,230516,123715,V,0.000000,N,0.000000,E,0.00,0.0,0.0,0,100,58,1279,0,00000000,1,1,460,0,9331,4770,1,0]
	[YW*111111111111111*0002*0066*UD,230516,123741,V,0.000000,N,0.000000,E,0.00,0.0,0.0,0,52,58,1279,0,00000000,1,1,460,0,9331,4740,22,0]
		
	 * */
	
}
