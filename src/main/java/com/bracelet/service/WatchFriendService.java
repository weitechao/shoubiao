package com.bracelet.service;

import java.util.List;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.WatchFriend;

public interface WatchFriendService {

	boolean insertFriend(String imei, String role, String phone, String cornet, String headType);

	@DataSourceChange(slave = true)
	List<WatchFriend> getFriendByImei(String imei);

	boolean deleteFriendById(Long id);

	boolean updateFriendById(Long id, String role, String phone, String cornet, String headType);

	WatchFriend getFriendByImeiAndPhone(String imei, String phone, Long deviceFriendId);

	boolean updateFriendNameById(Long id, String nickname);

	boolean insertFriend(String imei, String nickname, String phone, String string, String string2, Long deviceFriendId);

}
