package com.bracelet.service;

import com.bracelet.datasource.DataSourceChange;

public interface ITokenInfoService {
	@DataSourceChange(slave = true)
	Long getUserIdByToken(String token);
	@DataSourceChange(slave = true)
	String getTokenByUserId(Long userId);
	
	String genToken(Long userId);

}
