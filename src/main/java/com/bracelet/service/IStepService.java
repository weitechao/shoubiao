package com.bracelet.service;

import java.sql.Timestamp;
import com.bracelet.entity.Step;

public interface IStepService {

	Step getLatest(Long user_id);

	boolean insert(Long user_id, String imei, Integer step_number, Timestamp timestamp);

}
