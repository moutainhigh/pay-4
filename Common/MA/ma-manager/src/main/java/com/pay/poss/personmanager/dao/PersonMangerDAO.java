package com.pay.poss.personmanager.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.personmanager.dto.PersonMemberSearchDto;
import com.pay.poss.personmanager.model.Member;

public interface PersonMangerDAO extends BaseDAO<Member>{

	/**查询个人会员
	 * @param memberSearchDto
	 * @return
	 */
	List<PersonMemberSearchDto> queryPersonList(PersonMemberSearchDto memberSearchDto);	
	
	/** 统计个人会员
	 * @param memberSearchDto
	 * @return
	 */
	int countPersonMember(PersonMemberSearchDto memberSearchDto);
	
	/**根据会员号得到会员信息
	 * @param memberCode
	 * @return
	 */
	PersonMemberSearchDto selectPersonMember(Long memberCode);

}
