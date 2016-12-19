/*
 * Copyright (C) 2014 Fhpt All Rights Reserved.
 * 
 * MemberQuickBankAcctService.java
 */
package com.pay.acc.service.member;

import java.util.List;

import com.pay.acc.member.dto.MemberQuickBankAcctDto;

/**
 * [文件名称]<br>
 * MemberQuickBankAcctService.java<br>
 * <br>
 * [文件描述]<br>
 * 会员快捷银行卡绑定表 service interface<br>
 * <br>
 * [修改记录]<br>
 * 
 * @version 1.00
 */
public interface MemberQuickBankAcctService {

	/**
	 * 查询列表
	 * 
	 * @param memberCode
	 *            搜索条件
	 * @return 记录
	 */
	public List<MemberQuickBankAcctDto> queryMemberQuickBankAcct(
			final Long memberCode);

	/**
	 * 查询银行卡
	 * 
	 * @param memberCode
	 *            搜索条件
	 * @return 记录
	 */
	public MemberQuickBankAcctDto queryMemberBindingQuickBankAcct(
			final Long memberCode, final String bankCardNo);

	/**
	 * @param entity
	 *            搜索条件
	 * @return 分页记录
	 */
	public MemberQuickBankAcctDto findById(long id);

	/**
	 * @param entity
	 *            新增记录的详细信息
	 * @return 新增行数
	 */
	public Long create(MemberQuickBankAcctDto entity);

	/**
	 * @param entity
	 *            更新记录的详细信息
	 * @return 更新行数
	 */
	public boolean update(MemberQuickBankAcctDto entity);
}
