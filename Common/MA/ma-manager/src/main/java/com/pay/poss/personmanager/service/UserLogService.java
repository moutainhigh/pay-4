package com.pay.poss.personmanager.service;

import java.util.List;

import com.pay.poss.personmanager.dto.UserLogDto;

public interface UserLogService {

	/**
	 * 查询会员的操作日志
	 * 
	 * @param memberCode
	 * @return
	 */
	List<UserLogDto> queryUserLogByMemberCode(UserLogDto userLogDto);
}
