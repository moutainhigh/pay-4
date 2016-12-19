/*
 * Copyright (C) 2014 Fhpt All Rights Reserved.
 * 
 * MemberQuickBankAcctServiceImpl.java
 */
package com.pay.acc.service.member.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pay.acc.member.dao.model.MemberQuickBankAcct;
import com.pay.acc.member.dto.MemberQuickBankAcctDto;
import com.pay.acc.service.member.MemberQuickBankAcctService;
import com.pay.inf.dao.BaseDAO;
import com.pay.util.BeanConvertUtil;

/**
 * [文件名称]<br>
 * MemberQuickBankAcctServiceImpl.java<br>
 * <br>
 * [文件描述]<br>
 * 会员快捷银行卡绑定表 service interface impl<br>
 * <br>
 * [修改记录]<br>
 * 2014-11-15 12:28:31 创建 陶俊代码生成器<br>
 * 
 * @author 陶俊代码生成器
 * @version 1.00
 */
@Service("memberQuickBankAcctService")
public class MemberQuickBankAcctServiceImpl implements
		MemberQuickBankAcctService {

	private BaseDAO memberQuickBankAcctDAO;

	public void setMemberQuickBankAcctDAO(BaseDAO memberQuickBankAcctDAO) {
		this.memberQuickBankAcctDAO = memberQuickBankAcctDAO;
	}

	@Override
	public List<MemberQuickBankAcctDto> queryMemberQuickBankAcct(Long memberCode) {

		List<MemberQuickBankAcct> quickBankAcct = memberQuickBankAcctDAO
				.findByCriteria(memberCode);
		return (List<MemberQuickBankAcctDto>) BeanConvertUtil.convert(
				MemberQuickBankAcctDto.class, quickBankAcct);
	}

	@Override
	public MemberQuickBankAcctDto queryMemberBindingQuickBankAcct(
			Long memberCode, String bankCardNo) {

		Map paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		paraMap.put("bankCardNo", bankCardNo);
		paraMap.put("bindingFlag", 2);
		MemberQuickBankAcct quickBankAcct = (MemberQuickBankAcct) memberQuickBankAcctDAO
				.findObjectByCriteria(paraMap);
		return BeanConvertUtil.convert(MemberQuickBankAcctDto.class,
				quickBankAcct);
	}

	@Override
	public MemberQuickBankAcctDto findById(long id) {
		return BeanConvertUtil.convert(MemberQuickBankAcctDto.class,
				memberQuickBankAcctDAO.findById(id));
	}

	@Override
	public Long create(MemberQuickBankAcctDto entity) {
		return (Long) memberQuickBankAcctDAO.create(BeanConvertUtil.convert(
				MemberQuickBankAcct.class, entity));
	}

	@Override
	public boolean update(MemberQuickBankAcctDto entity) {
		return memberQuickBankAcctDAO.update(entity);
	}

}
