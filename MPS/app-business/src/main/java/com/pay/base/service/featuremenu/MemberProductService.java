/*
 * pay.com Inc.
 * Copyright (c) 2006-2011 All Rights Reserved.
 */
package com.pay.base.service.featuremenu;

import java.util.List;

import com.pay.base.dto.MenuDto;

/**
 *
 * @author zhi.wang
 * @version $Id: MemberProductService.java, v 0.1 2011-1-17 上午10:09:57 zhi.wang Exp $
 */
public interface MemberProductService {
	/**
	 * 根据memberCode查找会员产品菜单列表
	 *
	 * @param memberCode
	 * @return
	 */
	public List<MenuDto> findProductMenuByMemCode(Long memberCode);
}
