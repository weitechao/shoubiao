package com.bracelet.socket.business.impl;

import io.netty.channel.Channel;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.WatchUploadPhotoInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IUploadPhotoService;
import com.bracelet.service.IVoltageService;
import com.bracelet.socket.business.IService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

/**
 * 照片上传
 * 终端发送: 
[YW*YYYYYYYYYY*NNNN*LEN*TPBK，source，文件名，当前包，总包个数，位置数据(见附录一)] 
实例:[YW*8800000015*0001*000E*TPBK, 15986634630，2012122512556.jpg,1,6,] 
平台回复: 
[YW*YYYYYYYYYY*NNNN*LEN*TPCF, 文件名，当前包，总包个数，1] 
实例:
[YW*8800000015*0001*0002*TPCF,2012122512556.jpg,1,6,1]
说明：source为NULL或者号码，数据有可能为空
 * 
 */
@Component("guardService")
public class GuardService extends AbstractBizService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IUploadPhotoService iUploadPhotoService;
	
	@Override
	protected SocketBaseDto process1(SocketLoginDto socketLoginDto,
			JSONObject jsonObject, Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String process2(SocketLoginDto socketLoginDto, String jsonInfo,
			Channel channel) {
        
		logger.info("设备监控指令回传数据="+jsonInfo);
		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		String info = shuzu[4];
		String[] infoshuzu = info.split(",");
		Integer type = Integer.valueOf(infoshuzu[1]);//操作码：1为录音，格式amr；2为录像，格式mp4；3为拍照，格式jpg。录音，录像默认10秒，拍照默认一张。同时只能启用一项。
		String photoName = infoshuzu[2];//文件名
		int bytelength = Integer.valueOf(infoshuzu[3]);//字节总长度
		int thisNumber = Integer.valueOf(infoshuzu[4]);//当前包
		int allNumber = Integer.valueOf(infoshuzu[5]);//总包个数
		String dataInfo = infoshuzu[6];//数据
		if(type==1){//1为录音，格式amr
			
		}else if(type==2){//2为录像，格式mp4
			
		}else if(type==3){//3为拍照，格式jpg
			
		}
		if(thisNumber!=0){
			if(thisNumber==1){
				//iUploadPhotoService.insertPhoto( imei,"0", photoName,dataInfo);
			}else{
				WatchUploadPhotoInfo waUpInfo = iUploadPhotoService.getByPhotoNameAndImei(imei,photoName);
				if(waUpInfo!=null){
					iUploadPhotoService.updateById(waUpInfo.getId(),waUpInfo.getData()+dataInfo);
				}
			}
		}else{
			logger.info("上传照片的定位数据="+dataInfo);
		}
	//[YW*YYYYYYYYYY*NNNN*LEN*TPCF, 文件名，当前包，总包个数，1] 
		String resp = "[YW*"+imei+"*0001*001D*GUARD,"+type+","+photoName+","+bytelength+","+thisNumber+","+allNumber+","+"1]";
		return resp;
		}

	/*@Override
	public SocketBaseDto process(JSONObject jsonObject, Channel channel) {
		logger.info("===系统心跳：" + jsonObject.toJSONString());
		SocketBaseDto dto = new SocketBaseDto();
		dto.setType(jsonObject.getIntValue("type"));
		dto.setNo(jsonObject.getString("no"));
		dto.setTimestamp(new Date().getTime());
		dto.setStatus(0);

		return dto;
	}

	@Override
	public String process(String jsonInfo, Channel channel) {

		String[] shuzu = jsonInfo.split("\\*");
		String imei = shuzu[1];// 设备imei
		String no = shuzu[2];// 流水号
		String info = shuzu[4];

		String[] infoshuzu = info.split(",");
		String energy = infoshuzu[1];
		//还需要保存下电量
	
		logger.info("链路保持imei:" + imei + "," + ",no:" + no + ",电量:" + energy);

	
		String resp = "[YW*8800000015*0001*0002*LK ,"+Utils.getTime()+"]";
		return resp;
	}*/

}
