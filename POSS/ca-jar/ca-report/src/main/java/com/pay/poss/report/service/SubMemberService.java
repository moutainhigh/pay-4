package com.pay.poss.report.service;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.SubMemberDTO;

public interface SubMemberService {
	
	/**
	 * 新增商户
	 * @param model
	 * @return
	 */
	public long createSubMember(SubMemberDTO model);
	/**
	 * 修改商户信息
	 * @param model
	 * @return
	 */
	public boolean updateSubMember(SubMemberDTO model);
	
	/**
	 * 删除商户
	 * @param id
	 */
	public void deleteById(Long id);

	/**
	 * 根据parentId删除商户
	 * @param parentId
	 */
	public void deleteByParentId(Long parentId);
	
	/**
	 * 查找商户信息
	 * @param id
	 * @return
	 */
	public SubMemberDTO findById(Long id);
	
	/**
	 * 根据memberCode查找商户信息
	 * @param id
	 * @return
	 */
	public SubMemberDTO findByMemberCode(Long memberCode);

	/**
	 * 查询商户信息带分页
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<SubMemberDTO> querySubMember(Page<SubMemberDTO> page, Map<String, Object> map);
}