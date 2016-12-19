package com.pay.acc.member.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.member.dao.MemberRelactionDAO;
import com.pay.acc.member.model.MemberRelaction;
import com.pay.acc.member.service.MemberRelactionService;

public class MemberRelactionServiceImpl implements MemberRelactionService {

	private MemberRelactionDAO memberRelactionDAO;
	
	
	@Override
	public MemberRelaction selectMemberInfoDtoByBargainerName(Long bhource,
			String bargainerName) {		
		return this.memberRelactionDAO.selectMemberInfoDtoByBargainerName(bhource, bargainerName);
	}
	
	@Override
	public List<MemberRelaction> selectMemberRelationListByMember(Long member,
			Long type) {
		return this.memberRelactionDAO.selectMemberRelationListByMember(member, type);
	}

	@Override
	public boolean isBePartOfTheBourse(String bhource, String bargainer) {
		// TODO Auto-generated method stub;
		Map<String,Object> paramMap=new HashMap<String,Object>(2);
		paramMap.put("fatherMemberCode", bhource);
		paramMap.put("sonMember", bargainer);
		return this.memberRelactionDAO.isBePartOfTheBourse(paramMap);
	}

	public MemberRelactionDAO getMemberRelactionDAO() {
		return memberRelactionDAO;
	}


	public void setMemberRelactionDAO(MemberRelactionDAO memberRelactionDAO) {
		this.memberRelactionDAO = memberRelactionDAO;
	}






}
