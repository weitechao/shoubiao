package com.bracelet.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bracelet.socket.business.IBusinessHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.Utils;

@Component
@Sharable
public class BaseChannelHandler extends SimpleChannelInboundHandler<String> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IBusinessHandler businessHandler;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		Channel incoming = ctx.channel();
		logger.info("[" + incoming.remoteAddress() + "]原始16进制文本数据:" + msg);
		businessHandler.process(msg, incoming);
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception { // (2)
		Channel incoming = ctx.channel();
		logger.info("add connect:" + incoming.remoteAddress());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
		Channel incoming = ctx.channel();
		logger.info("remove connect:" + incoming.remoteAddress());
		ChannelMap.removeChannel(incoming);
	}

	/**
	 * 当从Channel中读数据时被调用
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		ByteBuf buf = (ByteBuf) msg;
		byte[] receiveMsgBytes = new byte[buf.readableBytes()];
		logger.info("receiveMsgBytes长度=" + receiveMsgBytes.length);
		buf.readBytes(receiveMsgBytes);
		// receiveMsgBytes 就收到了.
		String hexString = Hex.encodeHexString(receiveMsgBytes);
		logger.info("channelRead  16 hexString =" + hexString);
		
		
		if (hexString.length() >= 8) {
			String kaiTou = Utils.hexStringToString(hexString.substring(0, 8));
			logger.info("开头=" + kaiTou);

			if ("[YW*".equals(kaiTou)) {

				Integer len = Integer.parseInt(Utils.hexStringToString(hexString.substring(50, 58)), 16);
				logger.info("len=" + len);

				if (len + 30 - receiveMsgBytes.length == 0) {
					ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", receiveMsgBytes);
					super.channelRead(ctx, hexString);
				} else {
					String cmd = Utils.hexStringToString(hexString.substring(60, 64));
					logger.info("CMD=" + cmd);
					if ("TK".equals(cmd)) {
						ChannelMap.addVoiceName(ctx.channel().remoteAddress() + "_voice", hexString + "5d");
						ChannelMap.addVoiceName(ctx.channel().remoteAddress() + "_len",
								(len + 30 - receiveMsgBytes.length - 2) + "");
						
						byte[] addLast = Utils.byteMerger(receiveMsgBytes,Utils.getRightLast());
						logger.info("加5D后长度剩余=" + (len + 30 - receiveMsgBytes.length - 2));
						
						ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", addLast);
						logger.info("byte长度" + ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
					}
				}
				// String hexString16To10 =
				// Utils.hexStringToString(hexString);//utf-8

				// byte[] parseHexStr2Byte = Utils.hexStringToByte(hexString);

			} else {
				int syLength = Integer.valueOf(ChannelMap.getVoiceName(ctx.channel().remoteAddress() + "_len"))
						- receiveMsgBytes.length;
				logger.info("开头不是YW的剩余长度=" + syLength);
				
				
				if (syLength > 0) {
					ChannelMap.addVoiceName(ctx.channel().remoteAddress() + "_voice",
							ChannelMap.getVoiceName(ctx.channel().remoteAddress() + "_voice") + hexString + "5d");
					ChannelMap.addVoiceName(ctx.channel().remoteAddress() + "_len", (syLength - 2) + "");
					logger.info("减2剩余长度=" + ChannelMap.getVoiceName(ctx.channel().remoteAddress() + "_len"));
					
					byte[] addLast = Utils.byteMerger(receiveMsgBytes,Utils.getRightLast());
					
					logger.info("不是YW开头byte syLength > 0 未增加前的长度" + ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
					ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", Utils.byteMerger(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"),addLast));
					logger.info("不是YW开头byte syLength > 0   增加] 后的长度" + ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
					
				}else{
					ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", Utils.byteMerger(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"),receiveMsgBytes));
					logger.info("不是YW开头byte syLength = 0  的长度" + ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
					super.channelRead(ctx,
							ChannelMap.getVoiceName(ctx.channel().remoteAddress() + "_voice") + hexString);
					
					
				}
				
				
				
				/*else if (syLength == 0) {
					ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", Utils.byteMerger(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"),receiveMsgBytes));
					logger.info("不是YW开头byte syLength = 0  的长度" + ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
					super.channelRead(ctx,
							ChannelMap.getVoiceName(ctx.channel().remoteAddress() + "_voice") + hexString);
					
					
				} else if(syLength < 0) {
					logger.info("语音长度异常 开头不是YW的剩余长度");
					ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", Utils.byteMerger(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"),receiveMsgBytes));
					logger.info("不是YW开头byte syLength = 0  的长度" + ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
					
					super.channelRead(ctx,
							ChannelMap.getVoiceName(ctx.channel().remoteAddress() + "_voice") + hexString);
				
				}*/
			}

		} else {
			int syLength = Integer.valueOf(ChannelMap.getVoiceName(ctx.channel().remoteAddress() + "_len"))
					- receiveMsgBytes.length;
			logger.info("hexString.length() >= 8剩余长度=" + syLength);
			if (syLength > 0) {
				ChannelMap.addVoiceName(ctx.channel().remoteAddress() + "_voice",
						ChannelMap.getVoiceName(ctx.channel().remoteAddress() + "_voice") + hexString + "5d");
				ChannelMap.addVoiceName(ctx.channel().remoteAddress() + "_len", (syLength - 2) + "");
				logger.info("减2剩余长度=" + ChannelMap.getVoiceName(ctx.channel().remoteAddress() + "_len"));
				
				byte[] addLast = Utils.byteMerger(receiveMsgBytes,Utils.getRightLast());
				
				logger.info("开头长度小于8 syLength > 0 未增加前的长度" + ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
				ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", Utils.byteMerger(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"),addLast));
				logger.info("开头长度小于8 syLength > 0   增加] 后的长度" + ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
				
			}else{

				ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", Utils.byteMerger(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"),receiveMsgBytes));
				logger.info("不是YW开头byte syLength = 0  的长度" + ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
				
				super.channelRead(ctx, ChannelMap.getVoiceName(ctx.channel().remoteAddress() + "_voice") + hexString);
			
			}
			/* else if (syLength == 0) {
				ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", Utils.byteMerger(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"),receiveMsgBytes));
				logger.info("不是YW开头byte syLength = 0  的长度" + ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
				
				super.channelRead(ctx, ChannelMap.getVoiceName(ctx.channel().remoteAddress() + "_voice") + hexString);
			} else {
				logger.info("语音获取异常hexString.length() >= 8");
			}*/
		}

	}

	/**
	 * 当Channel变成活跃状态时被调用；Channel是连接/绑定、就绪的
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		logger.info("channelActive:" + incoming.remoteAddress() + "在线");
	}

	/**
	 * Channel未连接到远端
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		Channel incoming = ctx.channel();
		logger.info("channelInactive:" + incoming.remoteAddress() + "掉线");
		incoming.close();
		ctx.close();
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		super.channelReadComplete(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Channel incoming = ctx.channel();
		ctx.close();
		logger.error("exceptioncaught," + incoming.remoteAddress(), cause);
	}

	public static void main(String[] args) {
		String a = "5b59572a3837323031383032303134323136392a303033392a303144412a55442c31363132323031382c3039333732382c562c302e3030303030302c4e2c302e3030303030302c452c302e30302c302e302c302e302c302c38352c3130302c302c303a302c30303030303030302c362c312c3436302c302c31303137332c333836302c32392c31303137332c343934322c33322c31303137332c343933312c32392c31303137332c343934312c32382c31303137332c343236332c32382c31303137332c343934342c32362c31302c52482d322c66633a64373a33333a62313a34323a30322c2d35362c2c66633a64373a33333a63623a37333a61632c2d35382c535a2d524d4e322e34472c30343a35663a61373a35383a61633a66632c2d36352c54502d4c494e4b5f324339372c62633a34363a39393a30653a32633a39372c2d37362c434d43432d5745422c30303a32333a38393a39353a38613a37302c2d37372c434d43432d465245452c30303a32333a38393a39353a35623a65332c2d37372c434d43432d465245452c30303a32333a38393a39353a38613a37322c2d37382c434d43432d5745422c30303a32333a38393a39353a35623a65302c2d37382c52482d312c63303a36313a31383a35643a35393a37342c2d38302c434d43432c30303a32333a38393a39353a38613a37312c2d3830";
		String b = "30314441";
		System.out.println(a.indexOf(b));
		System.out.println(a.substring(50, 58));
		String kaiTou = Utils.hexStringToString(a.substring(0, 8));
		System.out.println(kaiTou);
		String hexString16To10 = Utils.hexStringToString(a.substring(50, 58));
		System.out.println(hexString16To10);
		Integer len = Integer.parseInt(hexString16To10, 16);
		System.out.println(len);
		String test = "[YW*872018020142169*0039*01DA*UD,16122018,093728,V,0.000000,N,0.000000,E,0.00,0.0,0.0,0,85,100,0,0:0,00000000,6,1,460,0,10173,3860,29,10173,4942,32,10173,4931,29,10173,4941,28,10173,4263,28,10173,4944,26,10,RH-2,fc:d7:33:b1:42:02,-56,,fc:d7:33:cb:73:ac,-58,SZ-RMN2.4G,04:5f:a7:58:ac:fc,-65,TP-LINK_2C97,bc:46:99:0e:2c:97,-76,CMCC-WEB,00:23:89:95:8a:70,-77,CMCC-FREE,00:23:89:95:5b:e3,-77,CMCC-FREE,00:23:89:95:8a:72,-78,CMCC-WEB,00:23:89:95:5b:e0,-78,RH-1,c0:61:18:5d:59:74,-80,CMCC,00:23:89:95:8a:71,-80]";
		System.out.println(test.length());
	}

}
