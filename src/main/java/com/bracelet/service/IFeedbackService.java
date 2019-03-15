package com.bracelet.service;

import java.util.List;

import com.bracelet.entity.Feedback;

public interface IFeedbackService {

	boolean insert(Long user_id, String content, String contact);

	List<Feedback> getFeedBackList(Long userId);

	boolean deleteInfoById(Long id);

}
