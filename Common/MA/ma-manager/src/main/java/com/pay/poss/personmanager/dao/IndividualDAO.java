package com.pay.poss.personmanager.dao;

import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.personmanager.dto.IndividualDto;

public interface IndividualDAO extends BaseDAO<IndividualDto>{
	
	/**
	 * @param memberCode
	 * @return
	 */
	IndividualDto selectIndividualDtoByMemberCode(Long memberCode);
	
	/**
	 * 更新updateIndividualInfo表的真实姓名和认证号码。
	 * @param paraMap
	 */
	void updateIndividualInfo(Map paraMap);

}
