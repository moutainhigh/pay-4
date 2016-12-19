package com.pay.poss.personmanager.service.impl;

import java.util.List;

import com.pay.poss.personmanager.dao.UserLogDAO;
import com.pay.poss.personmanager.dto.UserLogDto;
import com.pay.poss.personmanager.service.UserLogService;

public class UserLogServiceImpl implements UserLogService {
	private UserLogDAO possUserLogDAO;

	@Override
	public List<UserLogDto> queryUserLogByMemberCode(UserLogDto userLogDto) {
		List<UserLogDto> list = possUserLogDAO.selectUserLogList(userLogDto);
		return list;
	}
	
	public void setPossUserLogDAO(UserLogDAO possUserLogDAO) {
		this.possUserLogDAO = possUserLogDAO;
	}

}
