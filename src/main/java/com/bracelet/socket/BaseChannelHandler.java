package com.bracelet.socket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bracelet.redis.LimitCache;
import com.bracelet.socket.business.IBusinessHandler;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.StringUtil;
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
		try {
			byte[] receiveMsgBytes = new byte[buf.readableBytes()];
			
			buf.readBytes(receiveMsgBytes);
			String hexString = Hex.encodeHexString(receiveMsgBytes);
		
			Integer hexStringLength = hexString.length();

			if (hexStringLength >= 8) {
				String kaiTou = Utils.hexStringToString(hexString.substring(0, 8));
				logger.info("开头=" + kaiTou);

				if ("[YW*".equals(kaiTou) && hexStringLength>=58) {

					Integer len = Integer.parseInt(Utils.hexStringToString(hexString.substring(50, 58)), 16);
					logger.info("len=" + len);
					String cmd = Utils.hexStringToString(hexString.substring(60, 64));
					logger.info("cmd=" + cmd);
					if ((len + 30) == receiveMsgBytes.length) {
						if ("TK".equals(cmd) || "TP".equals(cmd)) {
							// 需要使用原始byte去write file
							ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", receiveMsgBytes);
						}
						super.channelRead(ctx, hexString);
					} else {

						logger.info("不等于0的CMD=" + cmd);
						if ("TK".equals(cmd) || "TP".equals(cmd)) {
							ChannelMap.addContent(ctx.channel().remoteAddress() + "_voice", hexString + "5d");
							ChannelMap.addInteger(ctx.channel().remoteAddress() + "_len",
									len + 30 - receiveMsgBytes.length - 1);
							byte[] addLast = Utils.byteMerger(receiveMsgBytes, Utils.getRightLast());
							ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", addLast);
							
						}
					}
					// String hexString16To10 =
					// Utils.hexStringToString(hexString);//utf-8

					// byte[] parseHexStr2Byte =
					// Utils.hexStringToByte(hexString);

				} else {
					
				//if(hexStringLength!=50){
					
						Integer syLength=0;
					if(StringUtil.isEmpty(ChannelMap.getInteger(ctx.channel().remoteAddress() + "_len"))){
						syLength = 0 - receiveMsgBytes.length;
					}else{
						syLength = ChannelMap.getInteger(ctx.channel().remoteAddress() + "_len") - receiveMsgBytes.length;
					}
					//syLength = ChannelMap.getInteger(ctx.channel().remoteAddress() + "_len") - receiveMsgBytes.length;
					logger.info("开头不是YW的剩余长度=" + syLength);

					if (syLength > 0) {

						byte[] addLast = Utils.byteMerger(receiveMsgBytes, Utils.getRightLast());
						ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte",
								Utils.byteMerger(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"), addLast));
						syLength = syLength-1;
						if(syLength<=0){
							super.channelRead(ctx, ChannelMap.getContent(ctx.channel().remoteAddress() + "_voice"));
						}else{
							ChannelMap.addInteger(ctx.channel().remoteAddress() + "_len", syLength);
						}
					} else {
						ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", Utils.byteMerger(
								ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"), receiveMsgBytes));
						// 移除map里的长度
						super.channelRead(ctx, ChannelMap.getContent(ctx.channel().remoteAddress() + "_voice"));
				//	}
					
				}

				}

			} else if(hexStringLength != 0){
				Integer syLength = 0;
				if(StringUtil.isEmpty(ChannelMap.getInteger(ctx.channel().remoteAddress() + "_len"))){
					syLength = 0 - receiveMsgBytes.length;
				}else{
					syLength = ChannelMap.getInteger(ctx.channel().remoteAddress() + "_len")  - receiveMsgBytes.length;
				}
				if (syLength > 0) {
					byte[] addLast = Utils.byteMerger(receiveMsgBytes, Utils.getRightLast());
					ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte",
							Utils.byteMerger(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"), addLast));
					syLength = syLength-1;
					if(syLength<=0){
						super.channelRead(ctx, ChannelMap.getContent(ctx.channel().remoteAddress() + "_voice"));
					}else{
						ChannelMap.addInteger(ctx.channel().remoteAddress() + "_len", syLength);
					}
					
				} else {
					ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", Utils
							.byteMerger(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"), receiveMsgBytes));
					super.channelRead(ctx, ChannelMap.getContent(ctx.channel().remoteAddress() + "_voice"));
				}
			}else{
				 if(!StringUtil.isEmpty(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"))){
					 ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte",Utils.byteMerger(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"), Utils.getRightLast()));
						if(!StringUtil.isEmpty(ChannelMap.getInteger(ctx.channel().remoteAddress() + "_len"))){
							Integer length = ChannelMap.getInteger(ctx.channel().remoteAddress() + "_len") - 1;
							ChannelMap.addInteger(ctx.channel().remoteAddress() + "_len", length);
							if(length<=0){
								super.channelRead(ctx, ChannelMap.getContent(ctx.channel().remoteAddress() + "_voice"));
							}
						}
				 }
				
			}
		}finally {
			ReferenceCountUtil.release(msg);
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
}
