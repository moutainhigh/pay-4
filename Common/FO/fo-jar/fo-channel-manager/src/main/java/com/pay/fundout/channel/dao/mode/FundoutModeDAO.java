package com.pay.fundout.channel.dao.mode;

import com.pay.fundout.channel.model.mode.FundoutMode;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

/**
 * 
 * @author Herny_zeng
 * 
 */
public interface FundoutModeDAO extends BaseDAO<FundoutMode> {
	/**
	 * 新增出款银行
	 * 
	 * @param info
	 */
	long addFundoutModeInfo(FundoutMode info);

	/**
	 * 更新出款银行
	 * 
	 * @param info
	 */
	int updateFundoutModeInfo(FundoutMode info);

	/**
	 * 查询出款银行信息
	 * 
	 * @param params
	 * @return
	 */
	Page<FundoutMode> queryFundoutModeInfos(Page<FundoutMode> page,
			FundoutMode fundoutMode);

	/**
	 * 根据出款银行编号查询出款银行信息
	 * 
	 * @param bankId
	 * @return
	 */
	FundoutMode queryFundoutModeInfoById(Long modeId);

	/**
	 * 根据出款银行编号查询出款银行信息
	 * 
	 * @param bankId
	 * @return
	 */
	FundoutMode queryFundoutModeInfoByCode(String code);
}