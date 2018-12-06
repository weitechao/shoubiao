package com.bracelet.socket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bracelet.socket.business.IBusinessHandler;
import com.bracelet.util.ChannelMap;

@Component
@Sharable
public class BaseChannelHandler extends SimpleChannelInboundHandler<String> {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IBusinessHandler businessHandler;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		Channel incoming = ctx.channel();
		logger.info("[" + incoming.remoteAddress() + "]原始发送信息:" + msg+"]");
		logger.info("[" + incoming.remoteAddress() + "]原始信息总长度:" + (msg+"]").length());
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
		
		ByteBuf body = (ByteBuf)msg;
		if(body.readableBytes() <= 0){
		ctx.fireChannelRead(msg);
		}
	
		int nLength = body.readInt();
		int nType = body.readInt();
		int nDataSize = body.readableBytes();
		byte [] aa = new byte[nDataSize];
		body.readBytes(aa);
		String myMsg = new String(aa,Charset.forName("utf-8"));
		 
		System.out.println("channelRead="+myMsg);
		
		super.channelRead(ctx, msg);
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
