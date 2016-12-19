package com.pay.fundout.channel.dao.bank.impl;

import com.pay.fundout.channel.dao.bank.FundoutBankDAO;
import com.pay.fundout.channel.model.bank.FundoutBank;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 * @author Herny_zeng
 * 
 */
@SuppressWarnings("unchecked")
public class FundoutBankDAOImpl extends BaseDAOImpl<FundoutBank> implements
		FundoutBankDAO {

	@Override
	public long addFundoutBankInfo(FundoutBank info) {
		return (Long) super.create(info);
	}

	@Override
	public FundoutBank queryFundoutBankInfoById(String bankId) {
		return this.findById(bankId);
	}

	@Override
	public Page<FundoutBank> queryFundoutBankInfos(Page<FundoutBank> page,
			FundoutBank fundoutBank) {
		return this.findByQuery("findBySelective", page, fundoutBank);
	}

	@Override
	public int updateFundoutBankInfo(FundoutBank info) {
		return super.update(info) == true ? 1 : 0;
	}
}