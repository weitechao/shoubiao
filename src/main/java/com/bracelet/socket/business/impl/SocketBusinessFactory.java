package com.bracelet.socket.business.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bracelet.exception.BizException;
import com.bracelet.util.RespCode;
import com.bracelet.socket.business.IService;

/**
 * 业务类型工厂类,根据type返回对应的业务处理对象
 * 
 */
@Component
public class SocketBusinessFactory {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 根据名称注入对应的业务
     */
    @Resource
    private IService loginService;
    @Resource
    private IService heartCheck;
    @Resource
    private IService locationUdService;
    @Resource
    private IService locationDdService;
    @Resource
    private IService costService;
    @Resource
    private IService insertFriendService; 
    @Resource
    private IService getFriendInfo;
    @Resource
    private IService getIpService;
    @Resource
    private IService getTianQi;
    @Resource
    private IService getDxTianQi;
    @Resource
    private IService smsToGetLocation; 
    @Resource
    private IService uploadPhoto; 
    @Resource
    private IService downloadFile;
    @Resource
    private IService tsService;
    @Resource
    private IService resetService;
    @Resource
    private IService guardService; 
    @Resource
    private IService tkService; 
    @Resource
    private IService tkqService; 
    @Resource
    private IService tk2Service;
    @Resource
    private IService avatarqService;
    @Resource
    private IService disMissService;
    @Resource
    private IService bindCardService;
    
    public IService getService(String cmd) throws BizException {
        logger.info("*****cmd:" + cmd);
        switch (cmd) {
        case "INIT":
            // 1.初始化
            return loginService;
        case "LK":
            // 2.链路保持
            return heartCheck;
        case "UD":
            // 3.位置数据上报  移动联通
            return locationUdService;
        case "DD":
            // 3.位置数据上报  电信
            return locationDdService;
        case "UD2":
            // 4.盲点补传数据
            return locationUdService;
        case "AL":
            // 5.报警数据上报
            return locationUdService;
        case "COST2":
            // 6.短信回复
            return costService;
        case "MFD":
            // 7.交友
            return insertFriendService;
        case "FDL":
            // 8.拉好友列表
            return getFriendInfo;
        case "IPREQ":
            // 9.获取服务器连接IP
            return getIpService;
        case "TQ":
            // 10.获取天气
            return getTianQi;
        case "DQ":
            // 10.电信获取天气
            return getDxTianQi;
        case "DWREQ":
            // 11.设备通过短信获取位置
            return smsToGetLocation;
        case "TPBK":
        	// 12.设备上传照片--远程监拍业务相似
            return uploadPhoto;
        case "FILE":
        	// 12.设备下载文件
            return downloadFile;
        case "COST1":
            // 6.短信回复
            return costService;
        case "TS":
            // 参数查询
            return tsService;
        case "RESET":
            // 重启
            return resetService;
        case "CR":
            // 定位指令
            return resetService;
        case "POWEROFF":
            // 关机指令
            return resetService;
        case "FIND":
            // 找手表
            return resetService;
        case "PHB":
            // 通讯录设置
            return resetService;
        case "KB":
            // 拨号键盘
            return resetService;
        case "ET":
            // 进入退出
            return resetService;
        case "FDLN":
            //好友列表
            return resetService;
        case "TK":
            // 对讲群聊
            return tkService;
        case "TKQ":
            // 终端请求语音下发
            return tkqService;
        case "TK2":
            //好友微聊
            return tk2Service;
        case "SET":
            //设置全部参数
            return resetService;
        case "MONITOR":
            //监听
            return resetService;
        case "MESSAGE":
            //文字推送
            return resetService;
        case "GUARD":
            //监控指令回传数据
            return guardService;
        case "AVATARQ":
            //下发通讯录真人头像
            return avatarqService;
        case "DISMISS":
            //设备上的解绑
            return disMissService;
        case "BINDCARD":
            //固定绑卡
            return bindCardService;
        
       
        default:
            logger.error("找不到对应的类型:" + cmd);
            throw new BizException(RespCode.DEV_REQ_TYPE_ERR);
        }
    }
    
    public IService getService(int type) throws BizException {
        logger.info("*****type:" + type);
        switch (type) {
        case 1:
            // 登录
            return loginService;
        default:
            logger.info("找不到对应的类型:" + type);
            throw new BizException(RespCode.DEV_REQ_TYPE_ERR);
        }
    }
    
   
}
