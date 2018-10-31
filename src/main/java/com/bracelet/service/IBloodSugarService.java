package com.bracelet.service;

import java.sql.Timestamp;

import com.bracelet.entity.BloodSugar;


public interface IBloodSugarService {
    boolean insert(Long user_id, Integer bloodSugar, Timestamp timsTimestamp);

	BloodSugar getLatest(Long user_id);
}
