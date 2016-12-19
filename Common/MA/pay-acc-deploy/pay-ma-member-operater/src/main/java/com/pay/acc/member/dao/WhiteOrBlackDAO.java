package com.pay.acc.member.dao;

import java.util.List;

import com.pay.acc.member.model.BlackWhiteList;
import com.pay.inf.dao.BaseDAO;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-12-27 下午05:34:39
 */
public interface WhiteOrBlackDAO extends BaseDAO<BlackWhiteList>{
	
	public List<BlackWhiteList> queryMemberWhiteOrBlackList(Long memberCode,int businessId);
}
