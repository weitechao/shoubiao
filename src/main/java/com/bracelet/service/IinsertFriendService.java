package com.bracelet.service;

import java.util.List;

import com.bracelet.entity.InsertFriend;

public interface IinsertFriendService {

	InsertFriend getInfo(String imei, String addimei);

	boolean insertFriendInfo(String imei, String addimei);

	List<InsertFriend> getInfoList(String imei, Integer status);

}
