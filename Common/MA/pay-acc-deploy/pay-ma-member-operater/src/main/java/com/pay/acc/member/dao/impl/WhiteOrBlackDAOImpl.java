package com.pay.acc.member.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.member.dao.WhiteOrBlackDAO;
import com.pay.acc.member.model.BlackWhiteList;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-12-27 下午05:38:56
 */
@SuppressWarnings("unchecked")
public class WhiteOrBlackDAOImpl extends BaseDAOImpl<BlackWhiteList> implements WhiteOrBlackDAO {

	@Override
	public List<BlackWhiteList> queryMemberWhiteOrBlackList(Long memberCode,int businessId){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", memberCode);
		paramMap.put("businessTypeId", businessId);
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("queryMemberWhiteOrBlackList"), paramMap);
	}
}
