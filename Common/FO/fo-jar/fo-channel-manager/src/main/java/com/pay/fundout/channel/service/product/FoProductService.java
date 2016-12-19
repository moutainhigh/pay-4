/**
 *  File: FoProductService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-28      Sunsea.Li      Changes
 *
 */
package com.pay.fundout.channel.service.product;

import java.util.Map;

import com.pay.fundout.channel.dto.product.FundoutProductDTO;
import com.pay.inf.dao.Page;

/**
 * 出款产品服务接口
 * 
 * @author Sunsea.Li
 * 
 */
public interface FoProductService {

	/**
	 * 创建出款产品
	 * 
	 */
	public Map<String, Object> createFoProduct(FundoutProductDTO foProduct);

	/**
	 * 查询出款产品列表
	 * 
	 * @param ProductId
	 * @return
	 */
	public Map<String, Object> queryFoProductInfo(Page<FundoutProductDTO> resultPage, FundoutProductDTO foProduct);

	/**
	 * 更新出款产品信息
	 * 
	 * @param foProduct
	 * @return
	 */
	public int updateFoProductInfo(FundoutProductDTO foProduct);

	/**
	 * 查询出款产品信息
	 * 
	 * @param ProductCode
	 * @return
	 */
	public FundoutProductDTO queryFoProductInfoByCode(String ProductCode);

}
