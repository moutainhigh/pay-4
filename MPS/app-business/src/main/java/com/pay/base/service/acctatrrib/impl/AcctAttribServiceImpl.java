package com.pay.base.service.acctatrrib.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.base.dao.acctattrib.AcctAttribDAO;
import com.pay.base.model.AcctAttrib;
import com.pay.base.model.PseudoAcct;
import com.pay.base.service.acctatrrib.AcctAttribService;
import com.pay.util.BeanConvertUtil;
import com.pay.util.StringUtil;

public class AcctAttribServiceImpl implements AcctAttribService{
	
	private AcctAttribDAO acctAttribDAO;
	
	public AcctAttribDto queryAcctAttribByAcctCode(String acctCode){
		AcctAttrib acctAttrib = (AcctAttrib) acctAttribDAO.findObjectByTemplate("queryAcctAttribByAcctCode", acctCode);
		return BeanConvertUtil.convert(AcctAttribDto.class, acctAttrib);
	}


	public Long createAcctAttrib(AcctAttrib acctAttrib) {
		return acctAttribDAO.createAcctAttrib(acctAttrib);
	}
	
	public boolean resetAcctAttribPwd(String acctCode, String newPayPwd){
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("acctCode", acctCode);
		map.put("newPayPwd", newPayPwd);
		boolean result = false;
		result= this.acctAttribDAO.updateAcctAttribPwd(map)==1;
		return result;
	}
	
	public boolean resetAcctAttribPwd(String acctCode,String memberCode,String newPayPwd){
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(!StringUtil.isEmpty(acctCode)){
			map.put("acctCode", acctCode);
		}
		
		if(!StringUtil.isEmpty(memberCode)){
			map.put("memberCode", memberCode);
		}
		
		map.put("newPayPwd", newPayPwd);
		boolean result = false;
		result= this.acctAttribDAO.updateAcctAttribPwd(map)>0;
		return result;
	}
	@Override
	public AcctAttrib checkPaymentPwd(Map<String, String> map) {
		return acctAttribDAO.checkPaymentPwd(map);
	}

	
	public AcctAttribDAO getAcctAttribDAO() {
		return acctAttribDAO;
	}

	public void setAcctAttribDAO(AcctAttribDAO acctAttribDAO) {
		this.acctAttribDAO = acctAttribDAO;
	}


	/* (non-Javadoc)
	 * @see com.pay.base.service.acctatrrib.AcctAttribService#queryAcctCurrencyByMemberCode(java.lang.Long)
	 */
	@Override
	public List<PseudoAcct> queryAcctCurrencyByMemberCode(Long memberCode) {
		return this.acctAttribDAO.queryAcctCurrencyByMemberCode(memberCode) ;
	}
	
}
