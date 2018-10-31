package com.bracelet.service;

public interface IApilogService {

	boolean insert(String name, String req, String resp, String imei, Integer rstatus, String rmsg, Long time);

}
