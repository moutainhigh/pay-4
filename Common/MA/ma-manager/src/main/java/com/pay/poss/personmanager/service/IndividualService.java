package com.pay.poss.personmanager.service;

import java.util.Map;

import com.pay.poss.personmanager.dto.IndividualDto;

public interface IndividualService {
	
	/**根据memberCode查询会员信息
	 * @param memberCode
	 * @return
	 */
	
	IndividualDto selectIndividualDtoByMemberCode(Long memberCode);
	
	/**根据会员号得到账户基本信息
	 * @param memberCode
	 * @return
	 */
	Map<String,Object> queryIndividualByMemberCode(Long memberCode);

}
