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
		/*
		 * NioEventLoop由于聚合了多路复用器Selector，可以同时并发处理成百上千个客户端Channel，由于读写操作都是非阻塞的，这就可以充分提升IO线程的运行效
		 * BossGroup和WorkerGroup都是NioEventLoopGroup，BossGroup用来处理nio的Accept，Worker处理nio的Read和Write事件
		 * */
		
		bossGroup = new NioEventLoopGroup(1);
		workerGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(serverChannelInitializer)
				.option(ChannelOption.SO_BACKLOG, 128).
				option(ChannelOption.SO_SNDBUF, 32 * 1024).
				option(ChannelOption.SO_RCVBUF, 32*1024).
				childOption(ChannelOption.SO_KEEPALIVE, true);
		
		
		//  .option(ChannelOption.SO_SNDBUF, 32 * 1024) //设置发送数据缓冲大小.option(ChannelOption.SO_RCVBUF, 32 * 1024) //设置接受数据缓冲大小
        
		
		/**
         * 对于ChannelOption.SO_BACKLOG的解释：
         * 服务器端TCP内核维护有两个队列，我们称之为A、B队列。客户端向服务器端connect时，会发送带有SYN标志的包（第一次握手），服务器端
         * 接收到客户端发送的SYN时，向客户端发送SYN ACK确认（第二次握手），此时TCP内核模块把客户端连接加入到A队列中，然后服务器接收到
         * 客户端发送的ACK时（第三次握手），TCP内核模块把客户端连接从A队列移动到B队列，连接完成，应用程序的accept会返回。也就是说accept
         * 从B队列中取出完成了三次握手的连接。
         * A队列和B队列的长度之和就是backlog。当A、B队列的长度之和大于ChannelOption.SO_BACKLOG时，新的连接将会被TCP内核拒绝。
         * 所以，如果backlog过小，可能会出现accept速度跟不上，A、B队列满了，导致新的客户端无法连接。要注意的是，backlog对程序支持的
         * 连接数并无影响，backlog影响的只是还没有被accept取出的连接
         
        .option(ChannelOption.SO_BACKLOG, 128) //设置TCP缓冲区
        .option(ChannelOption.SO_SNDBUF, 32 * 1024) //设置发送数据缓冲大小
        .option(ChannelOption.SO_RCVBUF, 32 * 1024) //设置接受数据缓冲大小
        .childOption(ChannelOption.SO_KEEPALIVE, true); //保持连
         
         */

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
