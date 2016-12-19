package com.pay.acc.member.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.acc.member.dao.MemberBankAcctDAO;
import com.pay.acc.member.dto.MemberBankAcctDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.model.MemberBankAcct;
import com.pay.acc.member.service.MemberBankAcctService;
import com.pay.acc.service.member.dto.MemberBankDto;
import com.pay.util.BeanConvertUtil;

public class MemberBankAcctServiceImpl implements MemberBankAcctService {

	private MemberBankAcctDAO memberBankAcctDAO;

	public MemberBankAcctDAO getMemberBankAcctDAO() {
		return memberBankAcctDAO;
	}

	public void setMemberBankAcctDAO(MemberBankAcctDAO memberBankAcctDAO) {
		this.memberBankAcctDAO = memberBankAcctDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.acc.service.MemberBankAcctService#
	 * queryMemberBankAcctByMemberCodeForVerify(java.lang.Long)
	 */
	public List<MemberBankAcctDto> queryMemberBankAcct(Long memberCode)
			throws MemberException, MemberUnknowException {

		return queryMemberBankAcctByMemberCode(memberCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.acc.member.service.MemberBankAcctService#
	 * queryMemberBankAcctByMemberCode(java.lang.Long)
	 */
	public List<MemberBankAcctDto> queryMemberBankAcctByMemberCode(
			Long memberCode) throws MemberException, MemberUnknowException {

		if (memberCode == null || memberCode.longValue() == 0) {
			throw new MemberException("memberCode参数输入 " + memberCode + " 不正确");
		}
		List<MemberBankAcct> list = null;
		try {
			list = memberBankAcctDAO.findByTemplate("queryMemberBankAcct",
					memberCode);
		} catch (Exception e) {
			throw new MemberUnknowException(e);
		}

		List<MemberBankAcctDto> memberBankAcctDtoList = null;
		if (null != list && list.size() > 0) {
			memberBankAcctDtoList = new ArrayList<MemberBankAcctDto>();
			for (MemberBankAcct memberBankAcct : list) {
				MemberBankAcctDto memberBankAcctDto = BeanConvertUtil.convert(
						MemberBankAcctDto.class, memberBankAcct);
				memberBankAcctDtoList.add(memberBankAcctDto);
			}
		}
		return memberBankAcctDtoList;
	}

	@Override
	public void doBindingBankCard(MemberBankDto bankDto) {
		memberBankAcctDAO.create(BeanConvertUtil.convert(MemberBankAcct.class,
				bankDto));
	}

	@Override
	public void doModifyBankCard(MemberBankDto bankDto) {
		memberBankAcctDAO.update(BeanConvertUtil.convert(MemberBankAcct.class,
				bankDto));
	}

}
