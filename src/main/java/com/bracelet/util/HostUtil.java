package com.bracelet.util;

import java.net.InetAddress;

public class HostUtil {
	public static String get() {
		String hostAddress = null;
		String hostName = null;
		try {
			InetAddress myip = InetAddress.getLocalHost();
			hostAddress = myip.getHostAddress();
			hostName = myip.getHostName();
		} catch (Exception e) {

			e.printStackTrace();

		}
		return hostAddress + "," + hostName;

	}
}
