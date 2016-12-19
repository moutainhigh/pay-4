package com.pay.poss.personmanager.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.personmanager.dto.AcctDto;

public interface AcctDAO extends BaseDAO<AcctDto>{
	
	/**根据会员号得到账户信息
	 * @param memberCode
	 * @return
	 */
	AcctDto selectAcctByMemberCode(Long memberCode);
	
	/**得到会员的账户LIST
	 * @param acctDto
	 * @return
	 */
	List<AcctDto> queryAcctListByMap(AcctDto acctDto);

}
