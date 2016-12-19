package com.pay.acc.member.service.impl;

import java.util.List;

import com.pay.acc.member.dao.MemberProductDAO;
import com.pay.acc.member.model.MemberProduct;
import com.pay.acc.member.service.MemberBaseProductService;

public class MemberBaseProductServiceImpl implements MemberBaseProductService {

	private MemberProductDAO memberProductDAO ;

	/* (non-Javadoc)
	 * @see com.pay.acc.member.service.impl.MemberBaseProductService#isHaveProduct(java.lang.Long, java.lang.Long)
	 */
	public boolean isHaveProduct(Long memberCode, String productCode){
		if(memberCode==null || productCode==null)
			return false;
		MemberProduct mp=memberProductDAO.queryMemberProductByMemberCode(memberCode, productCode);
			if(mp!=null)return true;
		return false;
	}
	
	public MemberProduct getMemeberProduct(Long memberCode, String productCode){
		if(memberCode==null || productCode==null)
			return null;
		MemberProduct mp=memberProductDAO.queryMemberProductByMemberCode(memberCode, productCode);
			
		return mp;
	}
	
	
	public boolean isHaveProductByOperator(Long operatorId,Long productId){
		String operatorMenuId=memberProductDAO.queryOperatorMenuByOperatorId(operatorId);
		List<String> menuIdList=memberProductDAO.queryProductMenuByProductId(productId);
		if(operatorMenuId==null || menuIdList==null && menuIdList.size()==0)
			return false;
		String []menuArray=operatorMenuId.split(",");
		if(menuArray.length>0){
			for(String s:menuIdList){
				for(String m:menuArray){
					if(m.equals(s)){
						return true;
					}
				}
			}
			
		}
		return false;
	}
	
	
	public void setMemberProductDAO(MemberProductDAO memberProductDAO) {
		this.memberProductDAO = memberProductDAO;
	}

	@Override
	public List<MemberProduct> queryMemberProductsByMemberCode(Long memberCode,
			String productCode) {
		return memberProductDAO.queryMemberProductsByMemberCode(memberCode, productCode);
	}
	
	
}
