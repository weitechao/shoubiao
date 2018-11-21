package com.bracelet.service;

import java.sql.Timestamp;
import java.util.List;

import com.bracelet.entity.Location;
import com.bracelet.entity.LocationOld;
import com.bracelet.entity.LocationWatch;
import com.bracelet.entity.OldBindDevice;

public interface ILocationService {

	Location getLatest(Long user_id);

	List<Location> getFootprint(Long user_id, String type);

	boolean insert(Long user_id, String location_type, String lat, String lng, Integer accuracy, Integer status,
			Timestamp timestamp);

	Location getRealtimeLocation(Long user_id, Integer status);

	boolean insertOldLocation(String phone, String lat, String lng);

	LocationOld getOldLocationLatest(String phone);

	List<LocationOld> getOldPhoneFootprint(String imei, String startime,
			String endtime);


	boolean insertUdInfo(String imei, Integer locationType,String lat, String lon, String status,
			String time,Integer locationStyle);

	LocationWatch getLatest(String imei);

	List<LocationWatch> getWatchFootprint(String imei, String starttime, String endtime);

}
