package com.bracelet.service;

import java.sql.Timestamp;

import com.bracelet.datasource.DataSourceChange;
import com.bracelet.entity.BloodFat;


public interface IBloodFatService {
    boolean insert(Long user_id, Integer bloodFat, Timestamp timsTimestamp);
  
    @DataSourceChange(slave = true)
	BloodFat getLatest(Long user_id);
}
