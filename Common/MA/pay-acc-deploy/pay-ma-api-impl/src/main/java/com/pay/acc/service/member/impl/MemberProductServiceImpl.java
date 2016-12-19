package com.pay.acc.service.member.impl;

import com.pay.acc.member.model.MemberProduct;
import com.pay.acc.member.service.MemberBaseProductService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.member.MemberProductService;
import com.pay.acc.service.member.dto.MemberProductResult;



public class MemberProductServiceImpl implements MemberProductService {

	private MemberBaseProductService memberProductService;

	/* (non-Javadoc)
	 * @see com.pay.acc.service.member.impl.MemberProductService#isHaveProduct(java.lang.Long, java.lang.Long)
	 */
	/* (non-Javadoc)
	 * @see com.pay.acc.service.member.impl.MemberProductService#isHaveProduct(java.lang.Long, java.lang.Long)
	 */
	public boolean isHaveProduct(Long memberCode,String productCode){
		return memberProductService.isHaveProduct(memberCode, productCode);
	}
	
	
	public MemberProductResult isHaveProduct(Long memberCode,String productCode,Long operatorId){
		MemberProductResult  mpResult=new MemberProductResult();
		MemberProduct mp=memberProductService.getMemeberProduct(memberCode, productCode);
		if(mp!=null){
			if(operatorId==null || operatorId<=0L){
				mpResult.setReturnBool(true);
			}else if(memberProductService.isHaveProductByOperator(operatorId, mp.getProductId())){
				mpResult.setReturnBool(true);
			}else{
				mpResult.setError(ErrorExceptionEnum.MEMBER_OPERATOR_PRODUCT_NOT);
			}
			
		}else{
			mpResult.setError(ErrorExceptionEnum.MEMBER_PRODUCT_NOT);
		}
		return mpResult;
	}
	

	public void setMemberProductService(
			MemberBaseProductService memberProductService) {
		this.memberProductService = memberProductService;
	}


	
	
}
