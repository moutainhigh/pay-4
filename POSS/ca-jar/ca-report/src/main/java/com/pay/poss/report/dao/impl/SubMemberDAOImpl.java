package com.pay.poss.report.dao.impl;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.report.dao.SubMemberDAO;
import com.pay.poss.report.dto.SubMemberDTO;

@SuppressWarnings("unchecked")
public class SubMemberDAOImpl extends BaseDAOImpl<SubMemberDTO> implements
		SubMemberDAO {

	@Override
	public Page<SubMemberDTO> querySubMember(Page<SubMemberDTO> page,
			Map<String, Object> map) {
		return super.findByQuery("querySubMember",
				page, map);
	}

	@Override
	public SubMemberDTO findByMemberCode(Long memberCode) {
		return super.findObjectByCriteria(
				"findByMemberCode", memberCode);
	}

	@Override
	public void deleteByParentId(Long parentId) {
		super.getSqlMapClientTemplate().delete(
				super.namespace.concat("deleteByParentId"), parentId);
	}
}