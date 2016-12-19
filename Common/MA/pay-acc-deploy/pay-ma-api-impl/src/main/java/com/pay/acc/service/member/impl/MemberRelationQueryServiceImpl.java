package com.pay.acc.service.member.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.member.model.MemberRelaction;
import com.pay.acc.member.service.MemberRelactionService;
import com.pay.acc.service.MemberRelationQueryService;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;

public class MemberRelationQueryServiceImpl implements
		MemberRelationQueryService {

	private MemberRelactionService relactionService;
	private MemberQueryService memberQueryService;

	@Override
	public List<MemberInfoDto> selectMemberRelationListByMember(Long member,
			Long type) throws MaMemberQueryException {
		if (null == member) {
			return null;
		}
		List<MemberRelaction> memberRelactionList = this.relactionService
				.selectMemberRelationListByMember(member, type);
		if (null == memberRelactionList || memberRelactionList.size() == 0) {
			return null;
		}
		List<MemberInfoDto> MemberInfoList = new ArrayList<MemberInfoDto>();
		for (MemberRelaction memberrelaction : memberRelactionList) {
			MemberInfoDto memberInfoDto = null;
			if (null != type && type == 1) {// 父查子
				memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(null,
						memberrelaction.getSonMemberCode(), null, null);
			} else {// 子查父,交易中心没有钢网的信息
				memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(null,
						memberrelaction.getFatherMemberCode(), null, null);
			}
			MemberInfoList.add(memberInfoDto);
		}
		return MemberInfoList;
	}

	@Override
	public MemberInfoDto selectMemberInfoDtoByBargainerName(Long bhource,
			String bargainerName) throws MaMemberQueryException {
		if (null == bhource || null == bargainerName) {
			return null;
		}
		MemberRelaction memberRelaction = this.relactionService
				.selectMemberInfoDtoByBargainerName(bhource, bargainerName);
		if (null == memberRelaction) {
			return null;
		}
		MemberInfoDto memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(
				null, memberRelaction.getSonMemberCode(), null, null);
		// //现在只有memberCode一个值
		// memberInfoDto.setMemberCode(memberRelaction.getSonMemberCode());
		return memberInfoDto;
	}


	@Override
	public boolean isBePartOfTheBourse(String bhource, String bargainer) {
		return this.relactionService.isBePartOfTheBourse(bhource, bargainer);
	}

	public MemberRelactionService getRelactionService() {
		return relactionService;
	}

	public void setRelactionService(MemberRelactionService relactionService) {
		this.relactionService = relactionService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

}
