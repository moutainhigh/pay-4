package com.pay.acc.member.service;

import java.util.List;

import com.pay.acc.member.model.MemberProduct;

public interface MemberBaseProductService {

	public abstract  boolean isHaveProduct(Long memberCode, String productCode);
	
	public MemberProduct getMemeberProduct(Long memberCode, String productCode);
	
	public boolean isHaveProductByOperator(Long operatorId,Long productId);
	
	List<MemberProduct> queryMemberProductsByMemberCode(Long memberCode,String productCode);

}