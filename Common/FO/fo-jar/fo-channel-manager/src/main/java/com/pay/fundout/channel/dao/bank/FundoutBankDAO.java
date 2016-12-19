package com.pay.fundout.channel.dao.bank;

import com.pay.fundout.channel.model.bank.FundoutBank;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

/**
 * 
 * @author Herny_zeng
 * 
 */
public interface FundoutBankDAO extends BaseDAO<FundoutBank> {

	/**
	 * 新增出款银行
	 * 
	 * @param info
	 */
	long addFundoutBankInfo(FundoutBank info);

	/**
	 * 更新出款银行
	 * 
	 * @param info
	 */
	int updateFundoutBankInfo(FundoutBank info);

	/**
	 * 查询出款银行信息
	 * 
	 * @param params
	 * @return
	 */
	Page<FundoutBank> queryFundoutBankInfos(Page<FundoutBank> page,
			FundoutBank fundoutBank);

	/**
	 * 根据出款银行编号查询出款银行信息
	 * 
	 * @param bankId
	 * @return
	 */
	FundoutBank queryFundoutBankInfoById(String bankId);
}