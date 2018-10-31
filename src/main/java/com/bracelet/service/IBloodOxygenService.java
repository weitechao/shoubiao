package com.bracelet.service;

import java.sql.Timestamp;

import com.bracelet.entity.BloodOxygen;


public interface IBloodOxygenService {
    boolean insert(Long user_id, Integer pulseRate,Integer bloodOxygen, Timestamp timsTimestamp);

	BloodOxygen getLatest(Long user_id);
}
