/**
 * 
 */
package com.pay.fundout.service.ma.impl;

import org.springframework.beans.BeanUtils;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.service.ma.MemberQueryFacadeService;
import com.pay.poss.service.adapter.AbstractExternalAdapter;

/**
 * @author NEW
 *
 */
public class MemberQueryFacadeServiceImpl extends AbstractExternalAdapter implements MemberQueryFacadeService {
	

	@Override
	public MemberInfo getMemberInfo(Long memberCode) {
		MemberInfo info = null;
		try {
			MemberInfoDto dto = memberQueryService.doQueryMemberInfoNsTx(null, memberCode, null, null);
			if(dto!=null){
				info = new MemberInfo();
				BeanUtils.copyProperties(dto, info);
			}
		} catch (MaMemberQueryException e) {
			log.error("call memberQueryService.doQueryMemberInfoNsTx failed", e);
		}
		return info;
	}

	@Override
	public MemberInfo getMemberInfo(String loginName) {
		MemberInfo info = null;
		try {
			MemberInfoDto dto = memberQueryService.doQueryMemberInfoNsTx(loginName, null, null, null);
			if(dto!=null){
				info = new MemberInfo();
				BeanUtils.copyProperties(dto, info);
			}
		} catch (MaMemberQueryException e) {
			log.error("call memberQueryService.doQueryMemberInfoNsTx failed", e);
		}
		return info;
	}

	@Override
	public boolean isBePartOfTheBourse(Long memberCode, Long subMemberCode) {
		
		return memberRelationQueryService.isBePartOfTheBourse(String.valueOf(memberCode), String.valueOf(subMemberCode));
	}

	
	

}
