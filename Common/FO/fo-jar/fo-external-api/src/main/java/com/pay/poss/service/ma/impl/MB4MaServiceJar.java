 /** @Description 
 * @project 	poss-external-api
 * @file 		MB4MaServiceJar.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-1		Henry.Zeng			Create 
*/
package com.pay.poss.service.ma.impl;

import com.pay.poss.service.adapter.AbstractExternalAdapter;
import com.pay.poss.service.ma.MB4MaServiceApi;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-9-1
 * @see 
 */
public class MB4MaServiceJar extends AbstractExternalAdapter implements MB4MaServiceApi {
	//该功能提供给MA进行   2010-10-06 zliner修改 
//	@Override
//	public List<PersonBO> queryPersonMember(PersonSearchBO personSearchBO,long pageNo,long pageSize) throws PossUntxException{
//		
//		log.debug(printLog(this, 1) +"personSearchBO :"+printObjToString(personSearchBO) 
//				+ "pageSize : "+pageSize + "pageNo:"+pageNo );
//		List<PersonBO> memberList = null;
//		try {
//			memberList = personMemberServiceMa.doQueryPersonsNsTx(personSearchBO, pageNo, pageSize);
//			log.debug(printLog(this, 2) +"queryPersonMember :"+printObjToString(memberList) );
//		} catch (MaMemberException e) {
//			log.error("MB4MaServiceJar.queryPersonMember error !");
//			log.error("error information:"+e.getMessage());
//			e.printStackTrace();
//		}
//		return memberList;
//	}
//	
//	@Override
//	public Long queryPersonMemberCount(PersonSearchBO personSearchBO) throws PossUntxException{
//		Long memberListCount = null;
//		log.debug(printLog(this, 1) +"personSearchBO :"+printObjToString(personSearchBO));
//		try {
//			memberListCount = personMemberServiceMa.doQueryPersonCountNsTx(personSearchBO);
//			log.debug(printLog(this, 2) +"memberListCount :"+printObjToString(memberListCount));
//		} catch (MaMemberException e) {
//			log.error("MB4MaServiceJar.queryPersonMemberCount error !");
//			log.error("error information:"+e.getMessage());
//			e.printStackTrace();
//		}
//		return memberListCount;
//	}
//	
//	@Override
//	public List<EnterpriseBO> queryEnterpriseMember(EnterpriseSearchBO enterpriseSearchBO,long pageNo,long pageSize) throws PossUntxException{
//		List<EnterpriseBO> memberList = null;
//		log.debug(printLog(this, 1) +"enterpriseSearchBO :"+printObjToString(enterpriseSearchBO) 
//				+ "pageSize : "+pageSize + "pageNo:"+pageNo );
//		try {
//			memberList = enterpriseMemberServiceMa.doQueryEnterprisesNsTx(enterpriseSearchBO, pageNo, pageSize);
//			log.debug(printLog(this, 2) +"memberList :"+printObjToString(memberList));
//		} catch (MaMemberException e) {
//			log.error("MB4MaServiceJar.queryEnterpriseMember error !");
//			log.error("error information:"+e.getMessage());
//			e.printStackTrace();
//		}
//		return memberList;
//	}
//	@Override
//	public Long queryEnterpriseMemberCount(EnterpriseSearchBO enterpriseSearchBO) throws PossUntxException{
//		Long memberListCount = null;
//		log.debug(printLog(this, 1) +"enterpriseSearchBO :"+printObjToString(enterpriseSearchBO) );
//		try {
//			memberListCount = enterpriseMemberServiceMa.doQueryEnterpriseCountNsTx(enterpriseSearchBO);
//			log.debug(printLog(this, 2) +"queryEnterpriseMemberCount="+memberListCount);
//		} catch (MaMemberException e) {
//			log.error("MB4MaServiceJar.queryEnterpriseMemberCount error !");
//			log.error("error information:"+e.getMessage());
//			e.printStackTrace();
//		}
//		return memberListCount;
//	}
//
//	@Override
//	public Long insertMerchant(MerchantMemberFillInfoBO merchantMemberFillInfoBO) throws PossUntxException {
//		Long memberCode = null;
//		try {
//			memberCode = merchantServiceMa.doMerchantMemberFillRnTx(merchantMemberFillInfoBO);
//		} catch (MaRegisterException e) {
//			log.error("MB4MaServiceJar.insertMerchant error !");
//			log.error("error information:"+e.getMessage());
//			e.printStackTrace();
//		}
//		return memberCode;
//	}
//
//	
	
}
