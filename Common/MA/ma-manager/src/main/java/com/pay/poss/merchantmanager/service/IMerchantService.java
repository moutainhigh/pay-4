package com.pay.poss.merchantmanager.service;

import java.util.List;
import java.util.Map;

import com.pay.poss.base.exception.PossException;
import com.pay.poss.merchantmanager.dto.MerchantDto;
import com.pay.poss.merchantmanager.dto.MerchantSearchDto;
import com.pay.poss.merchantmanager.dto.MerchantSearchListDto;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		IMerchantService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-8-25		gungun_zhang			Create
 */
public interface IMerchantService {
	
	public String createMerchantTrans(MerchantDto memberDto)throws PossException;	
	public Boolean updateMerchantTrans(MerchantDto memberDto)throws PossException;
	public List<MerchantSearchListDto> queryMerchant(MerchantSearchDto merchantSearchDto);
	public List<MerchantSearchListDto> queryMerchantOfUnActive(MerchantSearchDto merchantSearchDto);
	public Integer queryMerchantCount(MerchantSearchDto merchantSearchDto);
	public Integer queryMerchantOfUnActiveCount(MerchantSearchDto merchantSearchDto);
	public String isAllowCreateOrUpdateMerchant(Map<String,String> paraMap);
	public String isMccExist(String mcc);
	public List<MerchantDto> getMerchantListByMemberCode(String memberCode);
	/**
	 * 审核修改状态
	 * 戴德荣 修改
	 * @param memberCode
	 * @param email
	 * @param name
	 * @param status
	 * @return 
	 * @throws PossException
	 */
	public Boolean updateMerchantStatusTrans(Long memberCode,String email,String name,Integer status)throws PossException;
	
	//public String insertAccount(String memberCode) ;
	//public Boolean insertAccountAttribute(String accountCode,Long memberCode, List<AccountAttributeTemplate> accountAttributeTemplateList);
	//public List<AccountAttributeTemplate> queryAccAttriTempByAccType(Map<String,Object> paraMap );
	public Boolean sendEmailForActivationTrans(String[] checkedArray)throws PossException;
	public void sendEmailTrans(MerchantDto merchantDto) throws PossException ;
}
