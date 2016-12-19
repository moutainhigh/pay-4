/**
 *  File: UserhabitServiceImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.app.service.user.impl;

import java.util.Map;

import com.pay.app.dao.user.UserhabitDAO;
import com.pay.app.dto.UserhabitDTO;
import com.pay.app.model.Userhabit;
import com.pay.app.service.user.UserhabitService;
import com.pay.inf.dao.BaseDAO;
import com.pay.util.BeanConvertUtil;

/**
 * 
 */
public class UserhabitServiceImpl implements UserhabitService {

	// 设置class
	private Map<String, String> bankLabelClassConvert;

	private BaseDAO mainDao;

	public void setMainDao(BaseDAO mainDao) {
		this.mainDao = mainDao;
	}

	public Class getDtoClass() {

		return UserhabitDTO.class;
	}

	/**
	 * 获取用户最后一次的行为习惯
	 * 
	 * @param memberCode
	 * @return
	 */
	public UserhabitDTO findLastLoginByMemberCode(final String memberCode) {
		Userhabit model = ((UserhabitDAO) mainDao)
				.findLastLoginByMemberCode(memberCode);

		return BeanConvertUtil.convert(UserhabitDTO.class, model);
	}

	/**
	 * 
	 * @return
	 */
	public UserhabitDTO saveUserhabitRnTx(UserhabitDTO userhabitDto) {

		Userhabit userhbit = null;
		userhbit = BeanConvertUtil.convert(Userhabit.class, userhabitDto);
		Long id = (Long) mainDao.create(userhbit);
		userhabitDto.setId(id);
		return userhabitDto;
	}

	public void setBankLabelClassConvert(
			Map<String, String> bankLabelClassConvert) {
		this.bankLabelClassConvert = bankLabelClassConvert;
	}
}
