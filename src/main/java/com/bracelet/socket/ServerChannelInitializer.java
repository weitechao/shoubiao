package com.bracelet.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.ReadTimeoutHandler;

@Service
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
	
	private static final int MAX_FRAME_LENGTH = 1024 * 1024;
    private static final int LENGTH_FIELD_LENGTH = 4;
    private static final int LENGTH_FIELD_OFFSET = 2;
    private static final int LENGTH_ADJUSTMENT = 0;
    private static final int INITIAL_BYTES_TO_STRIP = 0;

	@Autowired
	BaseChannelHandler baseChannelHandler;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
	   // pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
	//	pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Unpooled.wrappedBuffer(new byte[] { ']'})));
	  //	pipeline.addLast("decoder", new StringDecoder());
		//pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4));
		/*
		 *  int maxFrameLength,
            int lengthFieldOffset, int lengthFieldLength,
            int lengthAdjustment, int initialBytesToStrip
		 * */
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Unpooled.wrappedBuffer(new byte[] { ']'})));
		//pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());
		pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(130));
		pipeline.addLast("handler", baseChannelHandler);
	}
	
	

	
}
