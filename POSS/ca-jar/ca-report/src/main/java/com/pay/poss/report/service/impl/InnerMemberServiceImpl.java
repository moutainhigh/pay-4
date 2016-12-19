package com.pay.poss.report.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dao.InnerMemberDAO;
import com.pay.poss.report.dto.InnerMemberDTO;
import com.pay.poss.report.service.InnerMemberService;

public class InnerMemberServiceImpl implements InnerMemberService {
	
	private InnerMemberDAO innerMemberDAO;

	public void setInnerMemberDAO(InnerMemberDAO innerMemberDAO) {
		this.innerMemberDAO = innerMemberDAO;
	}
	
	@Override
	public long createInnerMember(InnerMemberDTO member) {
		return (Long) innerMemberDAO.create(member);
	}

	@Override
	public boolean deleteById(Long id) {
		return innerMemberDAO.delete(id);
	}

	@Override
	public InnerMemberDTO findById(Long id) {
		return innerMemberDAO.findById(id);
	}

	@Override
	public boolean updateInnerMember(InnerMemberDTO member) {
		return innerMemberDAO.update(member);
	}

	@Override
	public List<InnerMemberDTO> findAllInnerMember() {
		return innerMemberDAO.findBySelective(null);
	}
	public Page<InnerMemberDTO> queryInnerMember(Page<InnerMemberDTO> page, Map<String, Object> map){
		return innerMemberDAO.queryInnerMember(page,map);
	}

	@Override
	public InnerMemberDTO findByParentId(Long memberCode) {
		return innerMemberDAO.findByParentId(memberCode);
	}
}
