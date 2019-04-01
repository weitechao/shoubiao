package com.bracelet.service.impl;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.DownLoadFileInfo;
import com.bracelet.entity.InsertFriend;
import com.bracelet.entity.Sys;
import com.bracelet.entity.WatchUploadPhotoInfo;
import com.bracelet.service.IUploadPhotoService;
import com.bracelet.util.Utils;
@Service
public class UploadPhotoServiceImpl implements IUploadPhotoService{
	@Autowired
	JdbcTemplate jdbcTemplate;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean insert(String imei, String photoName, String source,
			int thisNumber, int allNumber) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate
				.update("insert into upload_photo (imei, photo_name, source, this_number, all_number,createtime,status ,updatetime ) values (?,?,?,?,?,?,?,?)",
						new Object[] { imei, photoName, source, thisNumber, allNumber, now, 0, now},
						new int[] { Types.VARCHAR, Types.VARCHAR,
								Types.VARCHAR, Types.INTEGER, Types.INTEGER, Types.TIMESTAMP, Types.INTEGER, Types.TIMESTAMP});
		return i == 1;
	}

	@Override
	@DataSourceChange(slave = true)
	public List<DownLoadFileInfo> getphotoInfo(String imei,Integer status) {
		String sql = "select * from watch_upload_photo where imei=? and status = ? order by id desc ";
		List<DownLoadFileInfo> list = jdbcTemplate
				.query(sql, new Object[] { imei, status},
						new BeanPropertyRowMapper<DownLoadFileInfo>(DownLoadFileInfo.class));
		return list;
	}

	@Override
	public boolean insertPhoto(String imei,  String source,String photoName, String dataInfo) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate
				.update("insert into watch_upload_photo (imei, photo_name, source, data, createtime,updatetime) values (?,?,?,?,?,?)",
						new Object[] { imei, photoName, source,dataInfo, now,now}, 
						new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
								Types.VARCHAR, Types.TIMESTAMP, Types.TIMESTAMP});
		return i == 1;
	}

	@Override
	@DataSourceChange(slave = true)
	public WatchUploadPhotoInfo getByPhotoNameAndImei(String imei,
			String photoName) {
		String sql = "select * from watch_upload_photo where imei=? and photo_name=? order by id desc LIMIT 1";
		List<WatchUploadPhotoInfo> list = jdbcTemplate.query(sql, new Object[] {imei,photoName}, new BeanPropertyRowMapper<WatchUploadPhotoInfo>(WatchUploadPhotoInfo.class));
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		} else {
			logger.info("getOne reutrn null");
		}

		return null;
	}

	@Override
	public boolean updateById(Long id, String string) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate
				.update("update watch_upload_photo set data=?, createtime=? where id = ?",
						new Object[] { string ,now, id }, new int[] {
								Types.VARCHAR,Types.TIMESTAMP,Types.INTEGER });
		return i == 1;
	}

	@Override
	public boolean updateStatusById(Long id, Integer status) {
		Timestamp now = Utils.getCurrentTimestamp();
		int i = jdbcTemplate
				.update("update watch_upload_photo set status=?, updatetime=? where id = ?",
						new Object[] { status ,now, id }, new int[] {
								Types.INTEGER,Types.TIMESTAMP,Types.INTEGER });
		return i == 1;
	}
	

}
