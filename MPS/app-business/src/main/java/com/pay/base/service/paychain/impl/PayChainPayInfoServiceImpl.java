/**
*Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.base.service.paychain.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.common.helper.MessageKeyConf;
import com.pay.app.facade.bankacct.BankAcctServiceFacade;
import com.pay.base.dto.PayChainPayInfo;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.model.EnterpriseContact;
import com.pay.base.model.PayChain;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.base.service.enterprise.EnterpriseContactService;
import com.pay.base.service.paychain.PayChainPayInfoService;

/**
 * @author fjl
 * @date 2011-9-21
 */
public class PayChainPayInfoServiceImpl implements PayChainPayInfoService {
	
	private EnterpriseContactService enterpriseContactService;
	private EnterpriseBaseService  enterpriseBaseService;
	private BankAcctServiceFacade provinceServiceFacade;
	
	private final String SPLIT_CHAR = "[,]";

	@Override
	public PayChainPayInfo get(PayChain payChain) {
		try {
			PayChainPayInfo bean = new PayChainPayInfo();
			Long memberCode = payChain.getMemberCode();
			
			 EnterpriseBase enterpriseBase = enterpriseBaseService.findByMemberCode(memberCode);
			 EnterpriseContact enterpriseContact = enterpriseContactService.findByMemberCode(memberCode);
			 StringBuffer address = new StringBuffer();
	         String region = enterpriseBase.getRegion();
	         String city = enterpriseBase.getCity();
	         if(StringUtils.isNotBlank(region)){
	             region = provinceServiceFacade.getProvinceById(Integer.valueOf(region));
	             address.append(region).append("&nbsp;&nbsp;");
	         }
	         if(StringUtils.isNotBlank(city)){
	             city = provinceServiceFacade.getCityById(Integer.valueOf(city));
	             address.append(city).append("&nbsp;&nbsp;");
	         }
	         if(enterpriseContact != null && StringUtils.isNotEmpty(enterpriseContact.getAddress())){
	             address.append(enterpriseContact.getAddress());
	         }
	         if(enterpriseContact != null && StringUtils.isNotEmpty(enterpriseContact.getZip())){
	         	address.append("&nbsp;("+enterpriseContact.getZip()+")");
	         }
	         
	         bean.setPayChainId(payChain.getPayChainId().toString());
	         bean.setPayChainCode(payChain.getPayChainNumber());
	         bean.setPayeeAddr(address.toString());
	         bean.setPayeeName(enterpriseBase.getZhName());
	         if(enterpriseContact != null){
	        	 bean.setPayeeTel(enterpriseContact.getCompayLinkerTel());
	         }
	         bean.setPayChainName(payChain.getPayChainName());
	         bean.setReceiptDesc(payChain.getReceiptDesc());
	         bean.setPayeeMemberCode(memberCode.toString());
	         bean.setCreateDate(payChain.getCreateDate());
	         bean.setOverdueDate(payChain.getOverdueDate());
	         bean.setEffectiveDate(payChain.getEffectiveDate());
	         bean.setMcc(enterpriseBase.getInIndustry());
			
			return bean;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public void setEnterpriseContactService(
			EnterpriseContactService enterpriseContactService) {
		this.enterpriseContactService = enterpriseContactService;
	}

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setProvinceServiceFacade(BankAcctServiceFacade provinceServiceFacade) {
		this.provinceServiceFacade = provinceServiceFacade;
	}

	@Override
	public List<String> getMccList() {
		String mccs = MessageConvertFactory.getProperties(MessageKeyConf.PAYCHAIN_MCCS);
		if(StringUtils.isNotBlank(mccs)){
			List<String> list = new ArrayList<String>();
			String[] mcc = mccs.split(SPLIT_CHAR);
			for(String t : mcc){
				if(StringUtils.isNotBlank(t)){
					list.add(t);
				}
			}
			return list;
		}
		return null;
	}

	@Override
	public boolean isNeedLogin(String mcc) {
		List<String> list = getMccList();
		if(list == null || list.isEmpty()){
			return true;
		}
		for(String m : list){
			if(m.equals(mcc)){
				//配置中的mcc 与当前的一致就不需要登录
				return false;
			}
		}
		return true;
	}
}
