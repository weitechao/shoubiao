package com.bracelet.service;

import java.util.List;

import com.bracelet.entity.WatchFriend;

public interface WatchFriendService {

	boolean insertFriend(String imei, String role, String phone, String cornet, String headType);

	List<WatchFriend> getFriendByImei(String imei);

	boolean deleteFriendById(Long id);


	boolean updateFriendById(Long id, String role, String phone, String cornet,String headType);


}
