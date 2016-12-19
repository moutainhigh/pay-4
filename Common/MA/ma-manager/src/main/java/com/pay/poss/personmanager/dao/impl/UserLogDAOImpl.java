package com.pay.poss.personmanager.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.personmanager.dao.UserLogDAO;
import com.pay.poss.personmanager.dto.UserLogDto;

public class UserLogDAOImpl extends BaseDAOImpl<UserLogDto> implements
		UserLogDAO {

	@Override
	public List<UserLogDto> selectUserLogList(UserLogDto userLogDto) {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("selectPossUserLog"), userLogDto);
	}

}
