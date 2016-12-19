package com.pay.fundout.channel.dao.mode.impl;

import com.pay.fundout.channel.dao.mode.FundoutModeDAO;
import com.pay.fundout.channel.model.mode.FundoutMode;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 * @author Herny_zeng
 * 
 */
public class FundoutModeDAOImpl extends BaseDAOImpl<FundoutMode> implements
		FundoutModeDAO {

	@Override
	public long addFundoutModeInfo(FundoutMode info) {
		return (Long) super.create(info);
	}

	@Override
	public FundoutMode queryFundoutModeInfoById(Long modeId) {
		return super.findById(modeId);
	}

	@Override
	public Page<FundoutMode> queryFundoutModeInfos(Page<FundoutMode> page,
			FundoutMode fundoutMode) {
		return super.findByQuery("queryModeList", page,
				fundoutMode);
	}

	@Override
	public int updateFundoutModeInfo(FundoutMode info) {
		return super.update(info) == true ? 1 : 0;
	}

	@Override
	public FundoutMode queryFundoutModeInfoByCode(String code) {
		return super.findObjectByCriteria("findByCode", code);
	}
}