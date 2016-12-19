package com.pay.acc.member.service;

import java.util.List;

import com.pay.acc.member.model.MemberRelaction;


public interface MemberRelactionService {
	
	
	 /**判断交易商是否属于该交易中心 
	 * @param bhource 交易中心
	 * @param bargainer交易商
	 * @return
	 */
	boolean isBePartOfTheBourse(String bhource,String bargainer);
	
	/**查询父子会员信息
	 * @param member 会员号
	 * @param type type:1为父查子  :2为子查父
	 * @return MemberInfoDto会员对象集合
	 */
	List<MemberRelaction> selectMemberRelationListByMember(Long member,
			Long type);
	
	/**根据交易中心会员号，交易商名称查询交易商会员号
	 * @param bhource
	 * @param bargainerName
	 * @return
	 */
	MemberRelaction selectMemberInfoDtoByBargainerName(Long bhource,
			String bargainerName);

}
