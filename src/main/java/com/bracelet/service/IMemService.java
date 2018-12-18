package com.bracelet.service;

import java.util.List;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.MemberInfo;
import com.bracelet.entity.WatchPhoneBook;

public interface IMemService {

	boolean insert(Long user_id, String tel, String name, String imei, String head);

	@DataSourceChange(slave = true)
	List<MemberInfo> getMemberInfo(Long user_id, String imei);

	boolean delete(Long user_id, Long id);

	@DataSourceChange(slave = true)
	MemberInfo getMemberInfobyTel(Long user_id, String imei, String tel);

	boolean deleteAll(Long user_id);

	boolean deleteByImei(String imei);

	MemberInfo getMemberInfo(String username, String imei);

	WatchPhoneBook getPhoneBookByImeiAndPhone(String imei, String phone);

	boolean insertPhoneBookInfo(String imei, String name, String phone, String cornet, String headType, Integer status);

	List<WatchPhoneBook> getPhoneBookByImei(String imei);

	boolean deletePhonebookById(Long id);

	boolean updatePhonebookById(Long id, String name, String phone, String cornet, String headType);

	boolean updatePhonebookHeadImgById(Long deviceContactId, String photoImg);

}