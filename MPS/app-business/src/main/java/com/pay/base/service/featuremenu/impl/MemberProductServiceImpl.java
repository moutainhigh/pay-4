/*
 * pay.com Inc.
 * Copyright (c) 2006-2011 All Rights Reserved.
 */
package com.pay.base.service.featuremenu.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.dao.featuremenu.MemberProductDAO;
import com.pay.base.dto.MenuDto;
import com.pay.base.service.featuremenu.MemberProductService;

/**
 *
 * @author zhi.wang
 * @version $Id: MemberProductServiceImpl.java, v 0.1 2011-1-17 上午10:11:01 zhi.wang Exp $
 */
public class MemberProductServiceImpl implements MemberProductService {
	private static final Log log = LogFactory.getLog(MemberProductServiceImpl.class);
	
	private MemberProductDAO memberProductDAO;
	/** 
	 * @param memberCode
	 * @return
	 * @see com.pay.base.service.featuremenu.MemberProductService#findProductMenuByMemCode(java.lang.Long)
	 */
	@Override
	public List<MenuDto> findProductMenuByMemCode(Long memberCode) {
		if(memberCode != null && memberCode >0){
			return memberProductDAO.findProductMenuByMemCode(memberCode);
		}
		return null;
	}
	public void setMemberProductDAO(MemberProductDAO memberProductDAO) {
		this.memberProductDAO = memberProductDAO;
	}

}
