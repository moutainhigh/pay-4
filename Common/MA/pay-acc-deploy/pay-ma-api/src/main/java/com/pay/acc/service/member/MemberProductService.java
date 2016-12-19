package com.pay.acc.service.member;

import java.util.List;

import com.pay.acc.service.member.dto.MemberProductResult;

/**
 * @author JINHAHA
 *
 */
public interface MemberProductService {

	/* (non-Javadoc)
	 * @see com.pay.acc.service.member.impl.MemberProductService#isHaveProduct(java.lang.Long, java.lang.Long)
	 */
	/**
	 *  验证会员是否绑定了某产品
	 * @param memberCode
	 * @param productId
	 * @return
	 * beanid acc-memberProductService
	 */
	public abstract boolean isHaveProduct(Long memberCode,String productCode);
	
	
	
	/**
	 * 验证企业会员包括操作员是否绑定了某产品
	 * @param memberCode
	 * @param productCode
	 * @param operatorId
	 * @return
	 */
	public MemberProductResult isHaveProduct(Long memberCode,String productCode,Long operatorId);
}