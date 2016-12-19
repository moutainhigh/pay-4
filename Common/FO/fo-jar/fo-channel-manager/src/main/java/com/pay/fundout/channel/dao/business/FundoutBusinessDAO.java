package com.pay.fundout.channel.dao.business;

import com.pay.fundout.channel.model.business.FundoutBusiness;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

/**
 * 
 * @author Herny_zeng
 * 
 */
public interface FundoutBusinessDAO extends BaseDAO<FundoutBusiness> {
	public FundoutBusiness getFundoutBusinessById(Long businessId);

	/**
	 * 新增出款业务
	 * 
	 * @param info
	 */
	long addFoBusinessInfo(FundoutBusiness info);

	/**
	 * 更新出款业务
	 * 
	 * @param info
	 */
	int updateFoBusinessInfo(FundoutBusiness info);

	/**
	 * 查询出款业务信息
	 * 
	 * @param params
	 * @return
	 */
	Page<FundoutBusiness> queryFoBusinessInfos(Page<FundoutBusiness> page,
			FundoutBusiness fundoutBusiness);

	/**
	 * 根据出款业务编号查询出款业务信息
	 * 
	 * @param bankId
	 * @return
	 */
	FundoutBusiness queryFoBusinessInfoById(String bankId);

	/**
	 * 根据Code
	 * 
	 * @param code
	 * @return
	 */
	FundoutBusiness queryFoBusinessInfoByCode(String code);

}