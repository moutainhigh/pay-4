package com.pay.poss.report.service.impl;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dao.SubMemberDAO;
import com.pay.poss.report.dto.SubMemberDTO;
import com.pay.poss.report.service.SubMemberService;

public class SubMemberServiceImpl implements SubMemberService {
	
	private SubMemberDAO subMemberDAO;

	public void setSubMemberDAO(SubMemberDAO subMemberDAO) {
		this.subMemberDAO = subMemberDAO;
	}
	
	@Override
	public long createSubMember(SubMemberDTO model) {
		return (Long) subMemberDAO.create(model);
	}

	@Override
	public void deleteById(Long id) {
		subMemberDAO.delete(id);
	}

	@Override
	public SubMemberDTO findById(Long id) {
		return subMemberDAO.findById(id);
	}

	@Override
	public boolean updateSubMember(SubMemberDTO model) {
		return subMemberDAO.update(model);
	}

	@Override
	public Page<SubMemberDTO> querySubMember(Page<SubMemberDTO> page, Map<String, Object> map) {
		return subMemberDAO.querySubMember(page,map);
	}

	@Override
	public SubMemberDTO findByMemberCode(Long memberCode) {
		return subMemberDAO.findByMemberCode(memberCode);
	}

	@Override
	public void deleteByParentId(Long parentId) {
		subMemberDAO.deleteByParentId(parentId);
	}
}
