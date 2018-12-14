package com.bracelet.service;

import com.bracelet.datasource.DataSourceChange;

public interface ITokenInfoService {

	Long getUserIdByToken(String token);
	
	String getTokenByUserId(Long userId);
	@DataSourceChange(slave = true)
	String genToken(Long userId);

}
