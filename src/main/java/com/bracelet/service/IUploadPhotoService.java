package com.bracelet.service;

import java.util.List;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.DownLoadFileInfo;
import com.bracelet.entity.WatchUploadPhotoInfo;

public interface IUploadPhotoService {

	boolean insert(String imei, String photoName, String source, int thisNumber,
			int allNumber);
	@DataSourceChange(slave = true)
	List<DownLoadFileInfo> getphotoInfo(String imei,Integer status);

	boolean insertPhoto(String imei, String source,String photoName, String dataInfo);
	@DataSourceChange(slave = true)
	WatchUploadPhotoInfo getByPhotoNameAndImei(String imei, String photoName);

	boolean updateById(Long id, String string);

	boolean updateStatusById(Long id, Integer status);
	
}
