/**
 *  File: BanklimitDAO.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-16   Terry Ma    Create
 *
 */
package com.pay.base.service.individual.impl;

import com.pay.util.BeanConvertUtil;
import com.pay.acc.member.model.Member;
import com.pay.base.dao.individual.IndividualInfoDAO;
import com.pay.base.dto.IndividualInfoDto;
import com.pay.base.dto.MemberInfoDto;
import com.pay.base.model.IndividualInfo;
import com.pay.base.service.individual.IndividualInfoService;


public class IndividualInfoServiceImpl implements IndividualInfoService{
	
	private IndividualInfoDAO individualInfoDAO;

	public IndividualInfoDto queryIndividualInfoByMemberCode(Long memberCode){
		IndividualInfo individualInfo = null;
		individualInfo = (IndividualInfo) individualInfoDAO.findObjectByCriteria("queryIndividualInfoByMemberCode", memberCode);
		return BeanConvertUtil.convert(IndividualInfoDto.class, individualInfo);
	}
	
	
	public boolean updateIndividualInfo(IndividualInfoDto individualInfoDto){
		IndividualInfo individualInfo = BeanConvertUtil.convert(IndividualInfo.class, individualInfoDto);
		return individualInfoDAO.update(individualInfo);
	}
	
	public boolean checkEmail(String email){
	    return this.individualInfoDAO.checkEmail(email);
	}
	
	
	public boolean checkMobile(String mobile){
	    return this.individualInfoDAO.checkMobile(mobile);
	}
	
	
	public Long createIndividualInfo(IndividualInfoDto individualInfoDto){
	    IndividualInfo individualInfo = BeanConvertUtil.convert(IndividualInfo.class, individualInfoDto);
	    return this.individualInfoDAO.createIndividualInfo(individualInfo);
	}
	
	public IndividualInfoDto convertIndividualInfo(MemberInfoDto memberInfoDto,Long memberCode){
	    IndividualInfoDto individualInfo =new IndividualInfoDto();
	    individualInfo.setMemberCode(memberCode);
	    individualInfo.setTel(memberInfoDto.getTel());
	    individualInfo.setFax(memberInfoDto.getFax());
	    individualInfo.setQq(memberInfoDto.getQq());
	    individualInfo.setMsn(memberInfoDto.getMsn());
	    individualInfo.setProvince(memberInfoDto.getProvince());
	    individualInfo.setCity(memberInfoDto.getCity());
	    individualInfo.setAddr(memberInfoDto.getAddr());
	    individualInfo.setZip(memberInfoDto.getZip());
	    individualInfo.setCerType(memberInfoDto.getCertificateType());
	    individualInfo.setCerCode(memberInfoDto.getCertificateNo());
	    individualInfo.setName(memberInfoDto.getRealName());
	    individualInfo.setEmail(memberInfoDto.getEmail());
	    individualInfo.setMobile(memberInfoDto.getMobile());
	    individualInfo.setCountry(memberInfoDto.getCountry());
	    individualInfo.setSex(memberInfoDto.getSex());
	    individualInfo.setProfession(memberInfoDto.getProfession());
	    return individualInfo;
	}
	
	public void setIndividualInfoDAO(IndividualInfoDAO individualInfoDAO) {
		this.individualInfoDAO = individualInfoDAO;
	}
	
}