package com.pay.acc.service;

import java.util.List;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.dto.MemberInfoDto;

public interface MemberRelationQueryService {

	 /**判断交易商是否属于该交易中心 
	 * @param bhource  :String 交易中心
	 * @param bargainer:String 交易商
	 * @return boolean true:false
	 */
	boolean isBePartOfTheBourse(String bhource,String bargainer);
	
	
	/**查询交易中心，交易商的父子会员信息
	 * @param member :Long 会员号
	 * @param type  :Long  type:1为父查子  :2为子查父
	 * @return MemberInfoDto会员对象集合
	 */
	List<MemberInfoDto> selectMemberRelationListByMember(Long member,
			Long type)throws MaMemberQueryException;;
	
	
	/**根据交易中心会员号，交易商名称查询交易商会员号
	 * @param bhource       :Long     交易中心会员号
	 * @param bargainerName :String   交易商名称
	 * @return
	 * @throws MaMemberQueryException
	 */
	MemberInfoDto  selectMemberInfoDtoByBargainerName(Long bhource,String bargainerName)throws MaMemberQueryException;;
}
