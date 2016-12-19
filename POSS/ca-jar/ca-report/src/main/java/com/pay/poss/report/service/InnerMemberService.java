package com.pay.poss.report.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.report.dto.InnerMemberDTO;

public interface InnerMemberService {

	/**
	 * 保存份子公司信息
	 * 
	 * @param member
	 * @return
	 */
	public long createInnerMember(InnerMemberDTO member);

	/**
	 * 更新分子公司信息
	 * 
	 * @param member
	 * @return
	 */
	public boolean updateInnerMember(InnerMemberDTO member);

	/**
	 * 根据ID查找分子公司信息
	 * 
	 * @param id
	 * @return
	 */
	public InnerMemberDTO findById(Long id);

	/**
	 * 根据父memberCode查找分子公司信息
	 * 
	 * @param id
	 * @return
	 */
	public InnerMemberDTO findByParentId(Long memberCode);

	/**
	 * 删除份子公司
	 * 
	 * @param id
	 */
	public boolean deleteById(Long id);

	/**
	 * 查询所有分子公司
	 * 
	 * @return
	 */
	public List<InnerMemberDTO> findAllInnerMember();

	/**
	 * 查询分子公司信息带分页
	 * 
	 * @param page
	 * @param map
	 * @return
	 */
	public Page<InnerMemberDTO> queryInnerMember(Page<InnerMemberDTO> page,
			Map<String, Object> map);

}