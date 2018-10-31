package com.bracelet;

import java.net.InetAddress;

public class GetIp {
	public static void main(String[] args) {
		try{

			InetAddress myip= InetAddress.getLocalHost();

			System.out.println("你的IP地址是："+myip.getHostAddress());

			System.out.println("主机名为："+myip.getHostName()+"。");
		

			}catch(Exception e){

			e.printStackTrace();

			}
	}

}
