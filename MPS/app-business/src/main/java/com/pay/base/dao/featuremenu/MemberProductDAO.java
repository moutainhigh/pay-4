/*
 * pay.com Inc.
 * Copyright (c) 2006-2011 All Rights Reserved.
 */
package com.pay.base.dao.featuremenu;

import java.util.List;

import com.pay.base.dto.MenuDto;

/**
 * 会员产品DAO
 * @author zhi.wang
 * @version $Id: MemberProductDAO.java, v 0.1 2011-1-17 上午10:03:53 zhi.wang Exp $
 */
public interface MemberProductDAO {
	/**
	 * 根据memberCode查找会员产品菜单列表
	 *
	 * @param memberCode
	 * @return
	 */
	 public List<MenuDto> findProductMenuByMemCode(Long memberCode);
}
