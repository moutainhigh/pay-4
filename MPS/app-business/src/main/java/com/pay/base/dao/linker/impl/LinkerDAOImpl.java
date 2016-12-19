/**
 *  File: LinkerDAOImpl.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.dao.linker.impl;

import java.util.List;

import com.pay.base.dao.linker.LinkerDAO;
import com.pay.base.dto.LinkerDTO;
import com.pay.base.model.Linker;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 */
public class LinkerDAOImpl extends BaseDAOImpl implements LinkerDAO {

	@SuppressWarnings("unchecked")
	public List<Linker> queryLinkByPage(LinkerDTO linkerDto) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("pageQueryLinker"), linkerDto);
	}

	public Integer queryLinkByCount(LinkerDTO linkerDto) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("queryCount"), linkerDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Linker> queryLinkerByMemberCode(Long memberCode) {

		return getSqlMapClientTemplate().queryForList(
				namespace.concat("querybymemberCode"), memberCode);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Linker> verifylinkerrepeat(LinkerDTO LinkerDTO) {

		return getSqlMapClientTemplate().queryForList(
				namespace.concat("verifylinkerrepeat"), LinkerDTO);
	}

}
