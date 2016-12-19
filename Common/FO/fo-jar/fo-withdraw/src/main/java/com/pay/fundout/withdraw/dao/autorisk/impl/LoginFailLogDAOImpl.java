package com.pay.fundout.withdraw.dao.autorisk.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pay.fundout.withdraw.dao.autorisk.LoginFailLogDAO;
import com.pay.fundout.withdraw.dto.autorisk.LoginFailLogDto;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class LoginFailLogDAOImpl extends BaseDAOImpl<LoginFailLogDto> implements
		LoginFailLogDAO {

	@Override
	public Long createLoginFailLog(LoginFailLogDto dto) {
		return (Long) this.create("loginfaillog.create", dto);
	}

	@Override
	public int queryLoginFailNum(Long memberCode, Date beginDate, Date endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberCode", memberCode);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		Integer result = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("loginfaillog.queryLoginFailNum", params);
		if (result == null) {
			return 0;
		}
		return result;
	}
}
