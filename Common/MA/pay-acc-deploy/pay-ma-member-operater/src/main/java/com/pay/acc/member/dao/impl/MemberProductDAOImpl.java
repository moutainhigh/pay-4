package com.pay.acc.member.dao.impl;

import java.util.List;

import com.pay.acc.member.dao.MemberProductDAO;
import com.pay.acc.member.model.MemberProduct;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class MemberProductDAOImpl extends BaseDAOImpl<MemberProduct> implements MemberProductDAO{

	/* (non-Javadoc)
	 * @see com.pay.acc.member.dao.impl.MemberProductDAO#queryMemberProductByMemberCode(java.lang.Long, java.lang.Long)
	 */
	public MemberProduct queryMemberProductByMemberCode(Long memberCode,String productCode){
		MemberProduct mproduct=new MemberProduct();
		mproduct.setMemberCode(memberCode);
		mproduct.setProductCode(productCode);

		return (MemberProduct)this.getSqlMapClientTemplate().queryForObject(namespace.concat("queryProductByMemberCode"), mproduct);
	}
	
	
	public List<MemberProduct> queryMemberProductsByMemberCode(Long memberCode,String productCode){
		MemberProduct mproduct=new MemberProduct();
		mproduct.setMemberCode(memberCode);
		mproduct.setProductCode(productCode);
		
		List<MemberProduct> list = this.getSqlMapClientTemplate().queryForList(namespace.concat("queryProductsByMemberCode"), mproduct);
		return list;
	}
	
	
	public String queryOperatorMenuByOperatorId(Long operatorId){
		return (String)this.getSqlMapClientTemplate().queryForObject(namespace.concat("queryOperatorMenuByOperatorId"), operatorId);
	}
	
	
	public List<String> queryProductMenuByProductId(Long productId){
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("queryProductMenuByProductId"), productId);
	}
	
	
	
}
