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
					String cmd = Utils.hexStringToString(hexString.substring(60, 64));

					if (len + 30 - receiveMsgBytes.length == 0) {
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
									len + 30 - receiveMsgBytes.length - 2);

							byte[] addLast = Utils.byteMerger(receiveMsgBytes, Utils.getRightLast());
							logger.info("加5D后长度剩余=" + (len + 30 - receiveMsgBytes.length - 2));

							ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", addLast);
							logger.info("byte长度" + ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
						}
					}
					// String hexString16To10 =
					// Utils.hexStringToString(hexString);//utf-8

					// byte[] parseHexStr2Byte =
					// Utils.hexStringToByte(hexString);

				} else {
					Integer syLength = ChannelMap.getInteger(ctx.channel().remoteAddress() + "_len")
							- receiveMsgBytes.length;
					logger.info("开头不是YW的剩余长度=" + syLength);

					if (syLength > 0) {
						// ChannelMap.addVoiceName(ctx.channel().remoteAddress()
						// +
						// "_voice",ChannelMap.getVoiceName(ctx.channel().remoteAddress()
						// + "_voice") + hexString + "5d");

						ChannelMap.addInteger(ctx.channel().remoteAddress() + "_len", syLength - 2);
						logger.info("减2剩余长度=" + ChannelMap.getInteger(ctx.channel().remoteAddress() + "_len"));

						byte[] addLast = Utils.byteMerger(receiveMsgBytes, Utils.getRightLast());

						logger.info("不是YW开头byte syLength > 0 未增加前的长度"
								+ ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
						ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte",
								Utils.byteMerger(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"), addLast));
						logger.info("不是YW开头byte syLength > 0   增加] 后的长度"
								+ ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);

					} else {
						ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", Utils.byteMerger(
								ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"), receiveMsgBytes));
						logger.info("不是YW开头byte syLength = 0  的长度"
								+ ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
						// super.channelRead(ctx,ChannelMap.getContent(ctx.channel().remoteAddress()
						// + "_voice") + hexString);

						// 移除map里的长度
						super.channelRead(ctx, ChannelMap.getContent(ctx.channel().remoteAddress() + "_voice"));
					}

				}

			} else {
				Integer syLength = ChannelMap.getInteger(ctx.channel().remoteAddress() + "_len")
						- receiveMsgBytes.length;
				logger.info("hexString.length() >= 8剩余长度=" + syLength);

				if (syLength > 0) {
					// ChannelMap.addContent(ctx.channel().remoteAddress() +
					// "_voice",ChannelMap.getContent(ctx.channel().remoteAddress()
					// + "_voice") + hexString + "5d");
					ChannelMap.addInteger(ctx.channel().remoteAddress() + "_len", syLength - 2);
					logger.info("减2剩余长度=" + ChannelMap.getInteger(ctx.channel().remoteAddress() + "_len"));

					byte[] addLast = Utils.byteMerger(receiveMsgBytes, Utils.getRightLast());

					logger.info("开头长度小于8 syLength > 0 未增加前的长度"
							+ ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
					ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte",
							Utils.byteMerger(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"), addLast));
					logger.info("开头长度小于8 syLength > 0   增加] 后的长度"
							+ ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);

				} else {

					ChannelMap.addbyte(ctx.channel().remoteAddress() + "_byte", Utils
							.byteMerger(ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte"), receiveMsgBytes));
					logger.info("不是YW开头byte syLength = 0  的长度"
							+ ChannelMap.getByte(ctx.channel().remoteAddress() + "_byte").length);
					// 移除map里的长度

					// super.channelRead(ctx,
					// ChannelMap.getContent(ctx.channel().remoteAddress() +
					// "_voice") + hexString);
					super.channelRead(ctx, ChannelMap.getContent(ctx.channel().remoteAddress() + "_voice"));

				}
			}
		} finally {
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
