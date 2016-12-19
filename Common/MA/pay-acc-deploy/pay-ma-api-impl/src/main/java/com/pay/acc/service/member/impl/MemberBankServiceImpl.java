package com.pay.acc.service.member.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.constant.MemberInfoStatusEnum;
import com.pay.acc.exception.MaMemberBankException;
import com.pay.acc.member.dto.MemberBankAcctDto;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.service.MemberBankAcctService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.member.MemberBankService;
import com.pay.acc.service.member.dto.MemberBankDto;

public class MemberBankServiceImpl implements MemberBankService {

	private MemberBankAcctService memberBankAcctService;
	private MemberService memberService;

	private final Log log = LogFactory.getLog(MemberBankServiceImpl.class);

	public void setMemberBankAcctService(
			MemberBankAcctService memberBankAcctService) {
		this.memberBankAcctService = memberBankAcctService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.service.member.MemberBankService#doQueryMemberBankNsTx(long)
	 */
	public List<MemberBankDto> doQueryMemberBankNsTx(Long memberCode)
			throws MaMemberBankException {
		log.debug("query MemberBankAcct list By MemberCode for Verify ");
		// 验证
		this.checkQueryParameter(memberCode);
		List<MemberBankAcctDto> memberBankAcctList = null;
		try {
			memberBankAcctList = memberBankAcctService
					.queryMemberBankAcctByMemberCode(memberCode);
		} catch (MemberException e) {
			log.error(e.getMessage(), e);
			throw new MaMemberBankException(
					ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter",
					e);
		} catch (MemberUnknowException e) {
			log.error("unknow error", e);
			throw new MaMemberBankException(ErrorExceptionEnum.UNKNOW_ERROR,
					"unknow error", e);
		}

		List<MemberBankDto> memberBankDtoList = null;
		if (null != memberBankAcctList && memberBankAcctList.size() > 0) {
			memberBankDtoList = new ArrayList<MemberBankDto>();
			log.debug("MemberBankAcct list size : " + memberBankAcctList.size());
			for (MemberBankAcctDto memberBankAcctDto : memberBankAcctList) {
				MemberBankDto memberBankDto = new MemberBankDto();
				memberBankDto.setId(memberBankAcctDto.getId());
				memberBankDto.setBankId(memberBankAcctDto.getBankId());
				memberBankDto.setMemberCode(memberBankAcctDto.getMemberCode());
				memberBankDto.setIsPrimaryBankacct(memberBankAcctDto
						.getIsPrimaryBankacct());
				memberBankDto.setBranchBankName(memberBankAcctDto
						.getBranchBankName());
				memberBankDto.setCreationDate(memberBankAcctDto
						.getCreationDate());
				memberBankDto.setName(memberBankAcctDto.getName());
				memberBankDto.setProvince(memberBankAcctDto.getProvince());
				memberBankDto.setCity(memberBankAcctDto.getCity());
				memberBankDto.setStatus(memberBankAcctDto.getStatus());
				memberBankDto.setBankAcctId(memberBankAcctDto.getBankAcctId());
				memberBankDto.setBranchBankId(memberBankAcctDto.getBranchBankId());
				memberBankDtoList.add(memberBankDto);
			}
			log.debug("bankCardBindBOList list size : "
					+ memberBankDtoList.size());
		}
		return memberBankDtoList;
	}

	// 会员验证参数
	private MemberDto checkQueryParameter(Long memberCode)
			throws MaMemberBankException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null or invaild! ");
			throw new MaMemberBankException(
					ErrorExceptionEnum.INVAILD_PARAMETER,
					"invaild parameter , memberCode is null or invaild! ");
		}
		// else if (memberCode < MaConstant.CHECK_MEMBER_CODE) {
		// log.error("member code error");
		// throw new MaMemberBankException(ErrorExceptionEnum.MEMBERCODE_ERROR,
		// "member code error");
		// }
		// 验证会员是否激活
		MemberDto memberDto = null;

		try {
			memberDto = this.memberService
					.queryMemberWithMemberCode(memberCode);
		} catch (MemberException e) {
			log.error(e.getMessage(), e);
			throw new MaMemberBankException(
					ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter",
					e);
		} catch (MemberUnknowException e) {
			log.error("unknow error", e);
			throw new MaMemberBankException(ErrorExceptionEnum.UNKNOW_ERROR,
					"unknow error", e);
		}

		if (memberDto == null) {
			log.error("不存在会员号为[" + memberCode + "]的会员");
			throw new MaMemberBankException(
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR,
					"member non exist error");
		}
		if (null == memberDto.getStatus()
				|| memberDto.getStatus().intValue() != MemberInfoStatusEnum.NORMAL
						.getCode()) {
			log.error("会员号为[" + memberCode + "]处于非"
					+ MemberInfoStatusEnum.NORMAL.getDescription());

			throw new MaMemberBankException(ErrorExceptionEnum.MEMBER_INVALID,
					"会员号为[" + memberCode + "]处于非"
							+ MemberInfoStatusEnum.NORMAL.getDescription());
		}
		return memberDto;

	}

	@Override
	public void doBindingBankCard(MemberBankDto bankDto) {
		memberBankAcctService.doBindingBankCard(bankDto);
	}

	@Override
	public void doModifyBankCard(MemberBankDto bankDto) {
		memberBankAcctService.doModifyBankCard(bankDto);
	}

	@Override
	public MemberBankDto doQueryMemberBankNsTx(Long memberCode, String bankAcct) {

		List<MemberBankDto> bankList = doQueryMemberBankNsTx(memberCode);
		if (null != bankList && !bankList.isEmpty()) {
			for (MemberBankDto bank : bankList) {
				if (bank.getBankAcctId().equals(bankAcct)) {
					return bank;
				}
			}
		}
		return null;
	}

}
