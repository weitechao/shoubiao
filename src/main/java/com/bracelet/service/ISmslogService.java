package com.bracelet.service;

public interface ISmslogService {

	boolean insert(String name, String mobile, String tpl_code, String tpl_param, Integer rstatus, String rmsg);
	
	boolean insertWatch(String imei, String cmd, String info);

}
