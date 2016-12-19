package com.pay.acc.member.dao;

import java.util.List;

import com.pay.acc.member.model.MemberProduct;

public interface MemberProductDAO {

	public abstract MemberProduct queryMemberProductByMemberCode(Long memberCode,String productCode);
	
	public String queryOperatorMenuByOperatorId(Long operatorId);
	
	public List<String> queryProductMenuByProductId(Long productId);
	
	List<MemberProduct> queryMemberProductsByMemberCode(Long memberCode,String productCode);

}