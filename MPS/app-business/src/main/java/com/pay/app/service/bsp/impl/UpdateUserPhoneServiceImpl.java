/**
 * 
 */
package com.pay.app.service.bsp.impl;

import com.pay.app.dto.UserPhoneDto;
import com.pay.app.service.bsp.UpdateUserPhoneService;
import com.pay.base.model.EnterpriseContact;
import com.pay.base.service.enterprise.EnterpriseContactService;
import com.pay.base.service.member.MemberRelationService;

/**
 * @author 戴德荣
 * date  2011-6-29 上午11:12:03
 */
public class UpdateUserPhoneServiceImpl implements UpdateUserPhoneService {
		
	private EnterpriseContactService enterpriseContactService;
	private MemberRelationService memberRelationService;
	
	
	
	/**
	 * @param memberRelationService the memberRelationService to set
	 */
	public void setMemberRelationService(MemberRelationService memberRelationService) {
		this.memberRelationService = memberRelationService;
	}
	/**
	 * @param enterpriseContactService the enterpriseContactService to set
	 */
	public void setEnterpriseContactService(
			EnterpriseContactService enterpriseContactService) {
		this.enterpriseContactService = enterpriseContactService;
	}
	/**
	 * 根据memberCode取得用户
	 * @param memberCode memberCode
	 * @return UserPhoneDto
	 */
	public UserPhoneDto getUserPhoneByMemberCode(Long memberCode){
		UserPhoneDto userPhoneDto = new UserPhoneDto();
		EnterpriseContact contact = enterpriseContactService.findByMemberCode(memberCode);
		userPhoneDto.setMobile(contact.getCompayLinkerTel());
		userPhoneDto.setMemberCode(memberCode);
		userPhoneDto.setName(contact.getCompayLinkerName());
		userPhoneDto.setLoginName(contact.getEmail());
		userPhoneDto.setTypeName("企业用户");
		return userPhoneDto;
	}
	/* (non-Javadoc)
	 * @see com.pay.app.service.bsp.UpdateUserPhoneService#updateUserPhone(com.pay.app.dto.UserPhoneDto)
	 */
	@Override
	public boolean updateUserPhone(UserPhoneDto userPhoneDto) {
		return enterpriseContactService.updateEnterpriseLinker(userPhoneDto.getMemberCode(), userPhoneDto.getMobile());
	}
	
	public boolean hasPromisson(Long sessionMebmerCode,Long paramMemberCode){
		if(sessionMebmerCode.longValue() == paramMemberCode ){
			return true;
		}
		boolean is =  memberRelationService.isFatherAndSon(paramMemberCode, sessionMebmerCode);
		return is;
	}
	
	
}


