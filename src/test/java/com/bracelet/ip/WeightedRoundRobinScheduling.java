package com.bracelet.ip;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 9 * 权重轮询调度算法(WeightedRound-RobinScheduling)-Java实现 10 * @author huligong 11 *
 */
class WeightedRoundRobinScheduling {
	private List<Server> serverList; // 服务器集合

	public Server GetBestServer() {
		Server server = null;
		Server best = null;
		int total = 0;
		for (int i = 0, len = serverList.size(); i < len; i++) {
			// 当前服务器对象
			server = serverList.get(i);

			// 当前服务器已宕机，排除
			if (server.down) {
				continue;
			}

			server.currentWeight += server.effectiveWeight;
			total += server.effectiveWeight;

			if (server.effectiveWeight < server.weight) {
				server.effectiveWeight++;
			}

			if (best == null || server.currentWeight > best.currentWeight) {
				best = server;
			}

		}

		if (best == null) {
			return null;
		}

		best.currentWeight -= total;
		best.checkedDate = new Date();
		return best;
	}

	public void init() {
		Server s1 = new Server("192.168.0.100", 3);// 3
		Server s2 = new Server("192.168.0.101", 3);// 2
		Server s3 = new Server("192.168.0.102", 3);// 6
		Server s4 = new Server("192.168.0.103", 4);// 4
		Server s5 = new Server("192.168.0.104", 1);// 1
		Server s6 = new Server("192.168.0.105", 0);// 0
		Server s7 = new Server("192.168.0.106", 0);// 0
		Server s8 = new Server("192.168.0.107", 0);// 0
		Server s9 = new Server("192.168.0.108", 0);// 0
		serverList = new ArrayList<Server>();
		serverList.add(s1);
		serverList.add(s2);
		serverList.add(s3);
		/*serverList.add(s4);
		serverList.add(s5);
		serverList.add(s6);
		serverList.add(s7);
		serverList.add(s8);
		serverList.add(s9);*/
	}

	public void add(int i) {
		Server s = new Server("192.168.0.1" + i, i - 15);
		serverList.add(s);
	}

	public Server getServer(int i) {
		if (i < serverList.size()) {
			return serverList.get(i);
		}
		return null;

	}
}
