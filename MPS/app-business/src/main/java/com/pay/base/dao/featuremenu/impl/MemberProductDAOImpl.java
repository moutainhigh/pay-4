/*
 * pay.com Inc.
 * Copyright (c) 2006-2011 All Rights Reserved.
 */
package com.pay.base.dao.featuremenu.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.base.dao.featuremenu.MemberProductDAO;
import com.pay.base.dto.MenuDto;
import com.pay.base.model.Menu;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.util.BeanConvertUtil;

/**
 *
 * @author zhi.wang
 * @version $Id: MemberProductDAOImpl.java, v 0.1 2011-1-17 上午10:03:27 zhi.wang Exp $
 */
public class MemberProductDAOImpl extends BaseDAOImpl implements MemberProductDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuDto> findProductMenuByMemCode(Long memberCode) {
		List<Menu> menuList = this.getSqlMapClientTemplate().queryForList(this.namespace.concat("findMemberProductMenuByMemberCode"),memberCode);
		if(menuList != null && menuList.size() >0){
	        List<MenuDto> menuDtoList=new ArrayList<MenuDto>(menuList.size());
	        for(Menu m:menuList){
	            MenuDto mDto=BeanConvertUtil.convert(MenuDto.class, m);
	            menuDtoList.add(mDto);
	        }
	        return menuDtoList;
		}
		return null;
	}

}
