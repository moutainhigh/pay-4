package com.pay.fundout.channel.dao.product.impl;

import com.pay.fundout.channel.dao.product.FundoutProductDAO;
import com.pay.fundout.channel.model.product.FundoutProduct;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 * @author Herny_zeng
 * 
 */
@SuppressWarnings("unchecked")
public class FundoutProductDAOImpl extends BaseDAOImpl<FundoutProduct>
		implements FundoutProductDAO {

	@Override
	public long addFoProductInfo(FundoutProduct pojo) {
		return (Long) super.create(pojo);
	}

	@Override
	public Page<FundoutProduct> queryFoProductInfos(Page<FundoutProduct> page,
			FundoutProduct fundoutProduct) {
		return super.findByQuery("queryProductList", page,
				fundoutProduct);
	}

	@Override
	public int updateFoProductInfo(FundoutProduct pojo) {
		return super.update(pojo) == true ? 1 : 0;
	}

	@Override
	public FundoutProduct queryFoProductInfoByCode(String code) {
		return super.findObjectByCriteria("findByCode", code);
	}
}