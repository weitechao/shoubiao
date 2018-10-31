package com.bracelet.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SocketServer implements InitializingBean, DisposableBean{
	 
	private Logger logger = LoggerFactory.getLogger(getClass());
	private EventLoopGroup bossGroup = null;
	private EventLoopGroup workerGroup = null;

	@Autowired
	private ServerChannelInitializer serverChannelInitializer;

	public void start(int port) {
		bossGroup = new NioEventLoopGroup(1);
		workerGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(serverChannelInitializer)
				.option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);

		// ChannelFuture future;
		try {
			logger.info("===start socket:" + port);
			b.bind(port).sync();
			// future = b.bind(port).sync();
			// System.out.println("????222");
			// future.channel();
			// System.out.println("????3333");
			// future.channel().closeFuture();
			// System.out.println("????4444");
			// future.channel().closeFuture().sync();
			// System.out.println("????5555");
		} catch (InterruptedException e) {
			logger.error("socket InterruptedException.", e);
		}
	}

	public void afterPropertiesSet() throws Exception {
		start(7780);
	}

	public void destroy() throws Exception {
		// 释放资源
		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}

		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}
	}
}
