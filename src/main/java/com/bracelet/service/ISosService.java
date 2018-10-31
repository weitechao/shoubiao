package com.bracelet.service;

import java.util.List;
import com.bracelet.entity.WhiteListInfo;

public interface ISosService {

	boolean insert(Long user_id, String phone, String name);

	boolean delete(Long userId, Long id);

	WhiteListInfo getByPhone(Long userId, String phone);

	List<WhiteListInfo> find(Long userId);

	boolean insertSingleCallByTxt(Long userId, String imei, String phone, String name, String msg);

}