package com.bracelet.service;

import java.sql.Timestamp;
import java.util.List;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.Location;
import com.bracelet.entity.LocationOld;
import com.bracelet.entity.LocationWatch;
import com.bracelet.entity.OldBindDevice;

public interface ILocationService {
	
	@DataSourceChange(slave = true)
	Location getLatest(Long user_id);
	@DataSourceChange(slave = true)
	List<Location> getFootprint(Long user_id, String type);

	boolean insert(Long user_id, String location_type, String lat, String lng, Integer accuracy, Integer status,
			Timestamp timestamp);
	@DataSourceChange(slave = true)
	Location getRealtimeLocation(Long user_id, Integer status);

	boolean insertOldLocation(String phone, String lat, String lng);
	@DataSourceChange(slave = true)
	LocationOld getOldLocationLatest(String phone);
	@DataSourceChange(slave = true)
	List<LocationOld> getOldPhoneFootprint(String imei, String startime,
			String endtime);


	boolean insertUdInfo(String imei, Integer locationType,String lat, String lon, String status,
			String time,Integer locationStyle);
	@DataSourceChange(slave = true)
	LocationWatch getLatest(String imei);
	@DataSourceChange(slave = true)
	List<LocationWatch> getWatchFootprint(String imei, String starttime, String endtime);

	boolean insertUdPhotoInfo(String imei, Integer locationType, String lat,
			String lon, String status, String time,Integer locationStyle, String photoName);

}
