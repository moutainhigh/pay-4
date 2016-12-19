package com.pay.poss.personmanager.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.personmanager.dto.BankAcctDto;

public interface BankAcctDAO extends BaseDAO<BankAcctDto> {

	
	/**查询个人银行账户信息列表
	 * @param bankAcctDto
	 * @return
	 */
	List<BankAcctDto> selectBankAcctDtoList(BankAcctDto bankAcctDto);
}
