package com.pay.pe.manualbooking.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.pe.manualbooking.dao.VouchValidator;
import com.pay.pe.manualbooking.dto.VouchDataDetailDto;
import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchValidateMessage;

/**
 * 
 * 手工记帐数据验证器
 */
public class CopyOfVouchValidatorImpl implements VouchValidator {	
	
	private static final Log LOG = LogFactory.getLog(CopyOfVouchValidatorImpl.class);
	/**
	 * 最多记录数
	 */
	private static final int MAX_RECORD_SIZE = 200;
	
	/**
	 * 空记录数
	 */
	private static final int EMPTY_RECORD_SIZE = 0;
	
	/**
	 * 备注长度
	 */
	private static final int REMARK_MAX_SIZE = 100;
	
	private static final int AMOUNT_SIZE = 20;
	
//	/**
//	 * 帐户服务
//	 */
//	private AcctSpecService acctSpecService;
//	
//	/**
//	 * 会员帐户服务
//	 */
//	private MemberAcctSpecService memberAcctSpecService;
//	
//	private MemberService memberService;
//	
//	public AcctSpecService getAcctSpecService() {
//		return acctSpecService;
//	}
//
//	public void setAcctSpecService(AcctSpecService acctSpecService) {
//		this.acctSpecService = acctSpecService;
//	}
//
//	public MemberAcctSpecService getMemberAcctSpecService() {
//		return memberAcctSpecService;
//	}
//
//	public void setMemberAcctSpecService(MemberAcctSpecService memberAcctSpecService) {
//		this.memberAcctSpecService = memberAcctSpecService;
//	}
//	
//	public MemberService getMemberService() {
//		return memberService;
//	}
//
//	public void setMemberService(MemberService memberService) {
//		this.memberService = memberService;
//	}

	public CopyOfVouchValidatorImpl() { }
	
//	public VouchValidatorImpl(AcctSpecService acctSpecService, MemberAcctSpecService memberAcctSpecService) {
//		this.acctSpecService = acctSpecService;
//		this.memberAcctSpecService = memberAcctSpecService;
//	}
	
	/**
	 * @return VouchValidateMessage
	 * 产生记录为空消息
	 */
	protected VouchValidateMessage createRecordsEmptyMessage() {
		return VouchValidateMessage.createRecordsEmptyMessage();
	}
	
	/**
	 * @return VouchValidateMessage
	 * 产生记录数溢出消息
	 */
	protected VouchValidateMessage createRecordSizeExceedMessage() {
		return VouchValidateMessage.createRecordSizeExceedMessage();
	}
	
	/**
	 * @param i
	 * @return VouchValidateMessage
	 * 产生账号为空消息
	 */
	protected VouchValidateMessage createAccountIsEmptyMessage(int i) {
		return VouchValidateMessage.createAccountIsEmptyMessage(i);
	}
	
	/**
	 * @param i
	 * @param accountCode
	 * @return VouchValidateMessage
	 * 产生账号非数字消息
	 */
	protected VouchValidateMessage createAccountIsNotNumericMessage(int i, String accountCode) {
		return VouchValidateMessage.createAccountIsNotNumericMessage(i, accountCode);
	}
	
	/**
	 * @param i
	 * @param accountCode
	 * @return VouchValidateMessage
	 * 产生账号不存在消息
	 */
	protected VouchValidateMessage createAccountNotExistMessage(int i, String accountCode) {
		return VouchValidateMessage.createAccountNotExistMessage(i, accountCode);
	}
	
	/**
	 * @param i
	 * @return VouchValidateMessage
	 * 产生借贷只能一边有金额消息
	 */
	protected VouchValidateMessage createNotOneSideAmountMessage(int i) {
		return VouchValidateMessage.createNotOneSideAmountMessage(i);
	}
	
	/**
	 * @param i
	 * @return VouchValidateMessage
	 * 产生金额为空消息
	 */
	protected VouchValidateMessage createAmountIsEmptyMessage(int i) {
		return VouchValidateMessage.createAmountIsEmptyMessage(i);
	}
	
	/**
	 * @param i
	 * @return VouchValidateMessage
	 * 产生金额不是数字消息
	 */
	protected VouchValidateMessage createAmountIsNotNumericMessage(int i) {
		return VouchValidateMessage.createAmountIsNotNumericMessage(i);
	}
	
	/**
	 * @param i
	 * @return VouchValidateMessage
	 * 产生金额不能为零消息
	 */
	protected VouchValidateMessage createAmountIsZeroMessage(int i) {
		return VouchValidateMessage.createAmountIsZeroMessage(i);
	}
	
	protected VouchValidateMessage createAmountExceedTwoScaleMessage(int i) {
		return VouchValidateMessage.createAmountExceedTwoScaleMessage(i);
	}
	
	protected VouchValidateMessage createAmountTooLargeMessage(int i) {
		return VouchValidateMessage.createAmountTooLargeMessage(i);
	}
	
	/**
	 * @return VouchValidateMessage
	 * 产生借贷总额不等消息
	 */
	protected VouchValidateMessage createCrTotalNotEqualDrTotalMessage() {
		return VouchValidateMessage.createCrTotalNotEqualDrTotalMessage();
	}
	
	/**
	 * @param i
	 * @return VouchValidateMessage
	 * 产生备注过长消息
	 */
	protected VouchValidateMessage createRemarkTooLongMessage(int i) {
		return VouchValidateMessage.createRemarkTooLongMessage(i);
	}
	
	/**
	 * @param i
	 * @param accountCode
	 * @return VouchValidateMessage
	 * 产生账号冻结消息
	 */
	protected VouchValidateMessage createAccountIsFrozenMessage(int i, String accountCode) {
		return VouchValidateMessage.createAccountIsFrozenMessage(i, accountCode);
	}
	
	/**
	 * @param i
	 * @param accountCode
	 * @return VouchValidateMessage
	 * 产生账号止入消息
	 */
	protected VouchValidateMessage createAccountFundInDisableMessage(int i, String accountCode) {
		return VouchValidateMessage.createAccountFundInDisableMessage(i, accountCode);
		
	}
	
	/**
	 * @param i
	 * @param accountCode
	 * @return VouchValidateMessage
	 * 产生账号止出消息
	 */
	protected VouchValidateMessage createAccountFundOutDisableMessage(int i, String accountCode) {
		return VouchValidateMessage.createAccountFundOutDisableMessage(i, accountCode);
	}

	
	
//	private boolean isAccountCodeEmpty(VouchDataDetailDto detail) {
//		return StringUtils.isEmpty(detail.getAccountNo());
//	}
//	
//	private boolean isAccountCodeNotNumeric(VouchDataDetailDto detail) {
//		try {
//			new BigDecimal(detail.getAccountNo());
//		} catch (NumberFormatException e) {
//			return true;
//		}
//		return false;
//	}
//	
//	private boolean isAccountCodeNotExist(VouchDataDetailDto detail) {
//		String name = "";
//		
//		String acctCode = detail.getAccountNo();
//		AcctSpecDTO acctSpecDto = acctSpecService.getAcctSpec(acctCode);
//		
//		if (null == acctSpecDto){
//			MemberAcctSpecDTO memberAcctSpecDTO = memberAcctSpecService.getByAcctCode(acctCode);
//			if (null != memberAcctSpecDTO){
//				acctSpecDto = acctSpecService.getAcctSpec(memberAcctSpecDTO.getParentCOA());
//				
//				MemberDTO memberDTO = memberService.getMemberByMemberCode("" + memberAcctSpecDTO.getMembercode());
//				name = memberDTO.getName();
//			}
//		}
//
//		if (acctSpecDto == null) {
//			return true;
//		}
//
//		detail.setAccountName(name + acctSpecDto.getAcctname());
//		return false;
//	}
	
	private boolean isNotOneSideAmount(VouchDataDetailDto detail) {
		return detail.getCrdr() == null ? true : false;
		//return detail.getCrdr() == null ? false : false;
	}
	
	private boolean isAmountEmpty(VouchDataDetailDto detail) {
		return StringUtils.isEmpty(detail.getAmount());
	}
	
	private boolean isAmountNotNumeric(VouchDataDetailDto detail) {
		try {
			new BigDecimal(detail.getAmount());
		} catch (NumberFormatException e) {
			return true;
		}
		return false;
	}
	
	private boolean isAmountExceedTwoScale(VouchDataDetailDto detail) {
		BigDecimal b = new BigDecimal(detail.getAmount());
		
		if (b.scale() >= 3) {
			return true;
		}
		return false;
	}
	
	private boolean isAmountTooLarge(VouchDataDetailDto detail) {
		String amount = detail.getAmount();
		String s = null;
		
		if (amount.indexOf(".") == -1) {
			s = amount;
		} else {
			s = amount.substring(0, amount.indexOf("."));
		}
		
		if (amount.length() > AMOUNT_SIZE) {
			return true;
		}
		return false;
	}
	
	private boolean isAmountZero(VouchDataDetailDto detail) {
		BigDecimal amount = new BigDecimal(detail.getAmount());
		return BigDecimal.ZERO.compareTo(amount) == 0 ? true : false;
	}
	
//	private List<VouchValidateMessage> checkIfAccountFrozenOrDisInOrDisOut(int i, VouchDataDetailDto detail) {
//		LOG.info("Start");
//		List<VouchValidateMessage> messages = new ArrayList<VouchValidateMessage>();
//		
//		String acctCode = detail.getAccountNo();
//		MemberAcctSpecDTO memberAcctSpecDTO = memberAcctSpecService.getByAcctCode(acctCode);
//		
//		if (memberAcctSpecDTO != null) {
//			Boolean isFrozen = memberAcctSpecDTO.getFrozen().intValue() == 1 ? true : false;
//			if (isFrozen) {
//				LOG.debug("Account is Frozen! account code : " + acctCode);
//				messages.add(createAccountIsFrozenMessage(i, acctCode));
//			}
//			//1止入，
//			Boolean isNotAllowIn = memberAcctSpecDTO.getAllowIn().intValue() == 1 ? true : false;
//			if (isNotAllowIn) {
//				LOG.debug("Account fund in is disable! account code : " + acctCode);
//				messages.add(createAccountFundInDisableMessage(i, acctCode));
//			}
//			//1止出
//			Boolean isNotAllowOut = memberAcctSpecDTO.getAllowOut().intValue() == 1 ? true : false;
//			if (isNotAllowOut) {
//				LOG.debug("Account fund out is disable! account code : " + acctCode);
//				messages.add(createAccountFundOutDisableMessage(i, acctCode));
//			}
//			
//		}
//		
//		LOG.info("End");
//		return messages;
//	}
	
	public List<VouchValidateMessage> validate(VouchDataDto vouchDataDto) {
		LOG.info("Start");
		List<VouchValidateMessage> messages = new ArrayList<VouchValidateMessage>();
		
		if (vouchDataDto.getVouchDataDetails().size() == EMPTY_RECORD_SIZE) {
			LOG.debug("Records is empty!");
			messages.add(createRecordsEmptyMessage());
			return messages;
		} 
		if (vouchDataDto.getVouchDataDetails().size() > MAX_RECORD_SIZE) {
			LOG.debug("Record num exceed max size! size : " + vouchDataDto.getVouchDataDetails().size());
			messages.add(createRecordSizeExceedMessage());
		}
		
		int detailIndex = 1;
		for (VouchDataDetailDto detail : vouchDataDto.getVouchDataDetails()) {
			String remark = detail.getRemark();
			if (StringUtils.isNotEmpty(remark) && remark.length() > REMARK_MAX_SIZE) {
				LOG.debug("Remark is to long! remark : " + remark);
				messages.add(createRemarkTooLongMessage(detailIndex));
			}
			
//			if (isAccountCodeEmpty(detail)) {
//				LOG.debug("Account code is empty!");
//				messages.add(createAccountIsEmptyMessage(detailIndex));
//			} else if (isAccountCodeNotNumeric(detail)) {
//				LOG.debug("Account code is not numeric! account code : " + detail.getAccountNo());
//				messages.add(createAccountIsNotNumericMessage(detailIndex, detail.getAccountNo()));
//			} else if (isAccountCodeNotExist(detail)) {
//				LOG.debug("Account code is not exist! account code : " + detail.getAccountNo());
//				messages.add(createAccountNotExistMessage(detailIndex, detail.getAccountNo()));
//			} else {
//				List<VouchValidateMessage> msgs = checkIfAccountFrozenOrDisInOrDisOut(detailIndex, detail);
//				if (msgs != null && msgs.size() > 0) {
//					messages.addAll(msgs);
//				}
//			}
			
			if (isNotOneSideAmount(detail)) {
				LOG.debug("Not one side amount!");
				messages.add(createNotOneSideAmountMessage(detailIndex));
			} else if (isAmountEmpty(detail)) {
				LOG.debug("Amount is empty!");
				messages.add(createAmountIsEmptyMessage(detailIndex));
			} else if (isAmountNotNumeric(detail)) {
				LOG.debug("Amount is not numeric!");
				messages.add(createAmountIsNotNumericMessage(detailIndex));
			} else if (isAmountZero(detail)) {
				LOG.debug("Amount is zero!");
				messages.add(createAmountIsZeroMessage(detailIndex));
			} else if (isAmountExceedTwoScale(detail)) {
				LOG.debug("Amount exceed two scale!");
				messages.add(createAmountExceedTwoScaleMessage(detailIndex));
			} else if (isAmountTooLarge(detail)) {
				LOG.debug("Amount too large!");
				messages.add(createAmountTooLargeMessage(detailIndex));
			}
				
			detailIndex++;
		}
		
		if (messages.size() != 0) {
			return messages;
		}
		
		if (!vouchDataDto.getCrTotalAmount()
				.equals(vouchDataDto.getDrTotalAmount())) {
			LOG.debug("Cr/Dr total is not equal!");
			messages.add(createCrTotalNotEqualDrTotalMessage());
		}
		LOG.info("End");
		return messages;
	}
}
