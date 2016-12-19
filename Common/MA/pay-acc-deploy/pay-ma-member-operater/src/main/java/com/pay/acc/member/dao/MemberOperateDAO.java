package com.pay.acc.member.dao;

import java.util.List;
import java.util.Map;

import com.pay.acc.member.model.MemberOperate;
import com.pay.acc.member.model.Operator;
import com.pay.inf.dao.BaseDAO;

public interface MemberOperateDAO extends BaseDAO<MemberOperate>{

	/**根据MAP查询会员操作表
	 * @param paramMap
	 * @return
	 */
	List<MemberOperate> queryMemberOperateByMap(Map<String,Object> paramMap);

	/**
	 * 根据map查询会员账户操作表
	 * @param paramMap
	 * @return
	 */
	List<MemberOperate> queryAccountMemberOperateByMap(
			Map<String, Object> paramMap);
	
	/**
	 * 根据map操作员Id获取操作员信息 
	 * @param paramMap
	 * @return
	 */
	public Operator queryOperatorByOperatorId(Long operatorId);
	
	
	/**
	 * 根据会员号和操作员标识查询操作员信息
	 * @param memberCode
	 * @param identity
	 * @return
	 */
	public Operator queryOperatorByMemberCode(Long memberCode,String identity);
	
	/**
	 * 根据会员号查询账户（半小时内的）登录错误次数
	 * @param memberCode
	 * @return
	 */
	public Integer queryCountByErrLogin(Long memberCode);
}
