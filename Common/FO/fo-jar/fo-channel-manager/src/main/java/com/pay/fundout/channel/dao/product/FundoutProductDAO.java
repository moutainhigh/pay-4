package com.pay.fundout.channel.dao.product;

import com.pay.fundout.channel.model.product.FundoutProduct;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

/**
 * 
 * @author Herny_zeng
 * 
 */
public interface FundoutProductDAO extends BaseDAO<FundoutProduct> {
	/**
	 * 新增出款产品
	 * 
	 * @param info
	 */
	long addFoProductInfo(FundoutProduct info);

	/**
	 * 更新出款产品
	 * 
	 * @param info
	 */
	int updateFoProductInfo(FundoutProduct info);

	/**
	 * 查询出款产品信息
	 * 
	 * @param params
	 * @return
	 */
	Page<FundoutProduct> queryFoProductInfos(Page<FundoutProduct> page,
			FundoutProduct fundoutProduct);

	/**
	 * 根据Code
	 * 
	 * @param code
	 * @return
	 */
	FundoutProduct queryFoProductInfoByCode(String code);

}