package com.pay.acc.member.dao;

import java.util.List;
import java.util.Map;

import com.pay.acc.member.model.MemberRelaction;

public interface MemberRelactionDAO {

	/**
	 * 查询判断交易中心与交易商关联关系接口
	 * 
	 * @param paramMap
	 * @return
	 */
	public boolean isBePartOfTheBourse(Map<String, Object> paramMap);

	/**
	 * 查询父子会员信息
	 * 
	 * @param member
	 * @param type
	 *            type:1为父查子 :2为子查父
	 * @return
	 */
	public List<MemberRelaction> selectMemberRelationListByMember(Long member,
			Long type);

	MemberRelaction selectMemberInfoDtoByBargainerName(Long bhource,
			String bargainerName);

}
