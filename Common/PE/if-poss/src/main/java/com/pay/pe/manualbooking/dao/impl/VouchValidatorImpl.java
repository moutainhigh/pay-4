package com.pay.pe.manualbooking.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.commons.ConstantHelper;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.pe.account.service.SubjectService;
import com.pay.pe.helper.CRDRType;
import com.pay.pe.manualbooking.dao.VouchValidator;
import com.pay.pe.manualbooking.dto.VouchDataDetailDto;
import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchValidateMessage;
import com.pay.pe.manualbooking.service.AccService;
import com.pay.util.StringUtil;

/**
 * 
 * 手工记帐数据验证器
 */
public class VouchValidatorImpl implements VouchValidator {	
	
	private static final Log LOG = LogFactory.getLog(VouchValidatorImpl.class);
	/**
	 * 最多记录数
	 */
	private static final int MAX_RECORD_SIZE = 500;
	
	/**
	 * 空记录数
	 */
	private static final int EMPTY_RECORD_SIZE = 0;
	
	/**
	 * 备注长度
	 */
	private static final int REMARK_MAX_SIZE = 100;
	
	private static final int AMOUNT_SIZE = 20;
	
	private AccService accService ;
	private SubjectService subjectService;
	private MemberQueryService memberQueryService;


	public VouchValidatorImpl() { }
	
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
	 * 帐户名称不可以为空
	 * @param i
	 * @return
	 */
	protected VouchValidateMessage createAcctNameNullMessage(int i) {
		return VouchValidateMessage.createAcctNameNullMessage(i);
		
	}
	/**
	 * 帐户名称不能超过60个字符
	 * @param i
	 * @return
	 */
	protected VouchValidateMessage createAcctNameTooLagerMessage(int i) {
		return VouchValidateMessage.createAcctNameToolagerMessage(i);
		
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

	/**
	* @Title: createCrRecordlNotEqualDrRecordMessage
	* @Description: 验证借贷记录条数是否相等
	* @return VouchValidateMessage    返回类型
	* @throws
	*/ 
	protected VouchValidateMessage createCrRecordlNotEqualDrRecordMessage(){
		return VouchValidateMessage.createCrRecordlNotEqualDrRecordMessage();
	}
	
	private boolean isAccountCodeEmpty(VouchDataDetailDto detail) {
		return StringUtils.isEmpty(detail.getAccountCode());
	}
	
	private boolean isAccountCodeNotNumeric(VouchDataDetailDto detail) {
		try {
			new BigDecimal(detail.getAccountCode());
		} catch (NumberFormatException e) {
			return true;
		}
		return false;
	}
//	
	
	private boolean isAccountCodeNotExist(VouchDataDetailDto detail) {
		return accService.isAccountCodeNotExist(detail.getAccountCode());
	}
	
	
	
	
	
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
	
	private boolean isAmountExceedTwoScale(BigDecimal b) {
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
	
	private boolean isAmountZero(BigDecimal amount) {
		return BigDecimal.ZERO.compareTo(amount) == 0 ? true : false;
	}
	private boolean isAmountLessZero(BigDecimal amount){
		return BigDecimal.ZERO.compareTo(amount) == 1 ? true : false;
	}
	
	private List<VouchValidateMessage> checkIfAccountFrozenOrDisInOrDisOut(int i, VouchDataDetailDto detail) {
		LOG.info("Start");
		List<VouchValidateMessage> messages = new ArrayList<VouchValidateMessage>();
		
		String acctCode = detail.getAccountCode();
		
		
		AcctAttribDto acctAttribDto  = accService.getAcctAttribInfo(acctCode);
		
		if (acctAttribDto != null) {
			Boolean isFrozen = acctAttribDto.getFrozen().intValue() == ConstantHelper.ACCT_FREEZE_STATUS ? false : true;
			if (isFrozen) {
				LOG.debug("Account is Frozen! account code : " + acctCode);
				messages.add(createAccountIsFrozenMessage(i, acctCode));
			}
			//止入止出在MA判断，PE这边不用处理
//			//1止入，
//			Boolean isNotAllowIn = acctAttribDto.getAllowIn().intValue() == ConstantHelper.ALLOW_IN ? false : true;
//			if (isNotAllowIn) {
//				LOG.debug("Account fund in is disable! account code : " + acctCode);
//				messages.add(createAccountFundInDisableMessage(i, acctCode));
//			}
//			//1止出
//			Boolean isNotAllowOut = acctAttribDto.getAllowOut().intValue() ==ConstantHelper.ALLOW_OUT ? false : true;
//			if (isNotAllowOut) {
//				LOG.debug("Account fund out is disable! account code : " + acctCode);
//				messages.add(createAccountFundOutDisableMessage(i, acctCode));
//			}
			
		}
		
		LOG.info("End");
		return messages;
	}
	
	private Long getMemberByAcctCode(String acctCode){
		String code=null;
		if(null!=acctCode && acctCode.length()==26){
			code=acctCode.substring(13, 24);
		}		
		return Long.parseLong(code);
	}
	private  VouchValidateMessage checkAcctCodeMatchAcctName(int i, VouchDataDetailDto detail) {
		LOG.info("Start");
		String acctCode=detail.getAccountCode();
		String acctName=detail.getAccountName();
		
		LOG.info("acctCode  : "+acctCode  +"acctName  : "+acctName  + "acctCode length :" +acctCode.length());
		
		if(acctCode.length()>20){
			try {
				MemberInfoDto dto=memberQueryService.doQueryMemberInfoNsTx(null, getMemberByAcctCode(acctCode), null, null);
				if(null==dto || ! dto.getMemberName().equals(acctName)){
					LOG.debug("AcctCode  is not Match AcctName!");
					return VouchValidateMessage.createAcctCodeMatchAcctNameMessage(i);
				}
			} catch (MaMemberQueryException e) {
				LOG.error(" checkAcctCodeMatchAcctName error:"+e);
				return VouchValidateMessage.createAcctCodeMatchAcctNameMessage(i);
			}
		}else //科目名称与科目代码不匹配	
			if(!subjectService.countSubjectByAcctCodeAndAcctName(acctCode, acctName)){
				LOG.debug("AcctCode  is not Match AcctName!");
				return VouchValidateMessage.createAcctCodeMatchAcctNameMessage(i);
		}	
		LOG.info("End");

		return null;
	}
	public List<VouchValidateMessage> validate(VouchDataDto vouchDataDto) {
		LOG.info("Start");
		List<VouchValidateMessage> messages = new ArrayList<VouchValidateMessage>();
		//判断条数不能为空
		if (vouchDataDto.getVouchDataDetails().size() == EMPTY_RECORD_SIZE) {
			LOG.debug("Records is empty!");
			messages.add(createRecordsEmptyMessage());
			return messages;
		} 
		//判断是否超过最大条数限制
		if (vouchDataDto.getVouchDataDetails().size() > MAX_RECORD_SIZE) {
			LOG.debug("Record num exceed max size! size : " + vouchDataDto.getVouchDataDetails().size());
			messages.add(createRecordSizeExceedMessage());
		}
		//验证条数是否偶数
		if (vouchDataDto.getVouchDataDetails().size() % 2  !=  0) {
			LOG.debug("Record dr,cr not equal : " + vouchDataDto.getVouchDataDetails().size());
			messages.add(createCrRecordlNotEqualDrRecordMessage());
		}
		
		int detailIndex = 1;
		BigDecimal crAmount=BigDecimal.ZERO;
		BigDecimal drAmount=BigDecimal.ZERO;
		int crdr=0;
		for (VouchDataDetailDto detail : vouchDataDto.getVouchDataDetails()) {
			String remark = detail.getRemark();
			//备注长度
			if (StringUtils.isNotEmpty(remark) && remark.length() > REMARK_MAX_SIZE) {
				LOG.debug("Remark is to long! remark : " + remark);
				messages.add(createRemarkTooLongMessage(detailIndex));
			}
			
			//判断账户号
			if (isAccountCodeEmpty(detail)) {
				LOG.debug("Account code is empty!");
				messages.add(createAccountIsEmptyMessage(detailIndex));
			} else if (isAccountCodeNotNumeric(detail)) {
				LOG.debug("Account code is not numeric! account code : " + detail.getAccountCode());
				messages.add(createAccountIsNotNumericMessage(detailIndex, detail.getAccountCode()));
			} else if (isAccountCodeNotExist(detail)) {
				LOG.debug("Account code is not exist! account code : " + detail.getAccountCode());
				messages.add(createAccountNotExistMessage(detailIndex, detail.getAccountCode()));
			} else {
				List<VouchValidateMessage> msgs = checkIfAccountFrozenOrDisInOrDisOut(detailIndex, detail);
				if (msgs != null && msgs.size() > 0) {
					messages.addAll(msgs);
				}
			}
			
			
			BigDecimal amount=BigDecimal.ZERO;
			if (isNotOneSideAmount(detail)) {
				LOG.debug("Not one side amount!");
				messages.add(createNotOneSideAmountMessage(detailIndex));
			}	else if (isAmountEmpty(detail)) {
				LOG.debug("Amount is empty!");
				messages.add(createAmountIsEmptyMessage(detailIndex));
			}	
			
			else if (isAmountNotNumeric(detail)) {
				LOG.debug("Amount is not numeric!");
				messages.add(createAmountIsNotNumericMessage(detailIndex));
			} else {
				 amount=new BigDecimal(detail.getAmount());	
			}
			
			if (isAmountZero(amount)) {
				LOG.debug("Amount is zero!");
				messages.add(createAmountIsZeroMessage(detailIndex));
			} else if(isAmountLessZero(amount)){
				LOG.debug("Amount not less zero");
				messages.add(VouchValidateMessage.createAmountLessZeroMessage(detailIndex));
			}else if (isAmountExceedTwoScale(amount)) {
				LOG.debug("Amount exceed two scale!");
				messages.add(createAmountExceedTwoScaleMessage(detailIndex));
			} else if (isAmountTooLarge(detail)) {
				LOG.debug("Amount too large!");
				messages.add(createAmountTooLargeMessage(detailIndex));
			}
			

			//借方
			if(detail.getCrdr()==CRDRType.DEBIT.getValue()){
				drAmount=amount;
			}else{
				crAmount=amount;
			}
			//对一借一贷的条件进行验证
			//判断是否一借一贷或者一贷一借
//			if(detailIndex%2 ==0 && crdr ==detail.getCrdr()){
//				LOG.info(" dr  is  not equal cr !");
//            	messages.add(VouchValidateMessage.createCrNotEqualDrMessage(detailIndex));
//			}
			
			if(detailIndex%2 ==0 ){
				if(crdr ==detail.getCrdr()){
					LOG.info(" dr  is  not equal cr !");
	            	messages.add(VouchValidateMessage.createCrNotEqualDrMessage(detailIndex));
				}
				crdr=0;
			}else{
				crdr=detail.getCrdr();
			}
			
			//TODO 验证一借一贷中的金额是否相等
			if( BigDecimal.ZERO.compareTo(crAmount) != 0  &&  BigDecimal.ZERO.compareTo(drAmount) !=0 ){
				//借贷金额不相等
				if(crAmount.compareTo(drAmount)  != 0){
					LOG.debug(" drAmount is  not equal crAmount !");
					messages.add(VouchValidateMessage.createCrAmountNotEqualDrAmountMessage(detailIndex));
				}else{
					//初始化为0
					 crAmount=BigDecimal.ZERO;
					 drAmount=BigDecimal.ZERO;
				}
			}
			
			if(StringUtil.isEmpty(detail.getAccountName())){
				messages.add(createAcctNameNullMessage(detailIndex));
//			}else if(detail.getAccountName().length() > 50){
//				messages.add(createAcctNameTooLagerMessage(detailIndex));
			}	
			//TODO 判断账户号与科目名称是否相同
			//VouchValidateMessage msg=checkAcctCodeMatchAcctName(detailIndex,detail);
			//if(null!=msg ){
				//messages.add(msg);
			//}			
			detailIndex++;
		}
		
		if (messages.size() != 0) {
			return messages;
		}
		//验证总的CR,DR总金额是否相等
		if (!vouchDataDto.getCrTotalAmount().equals(vouchDataDto.getDrTotalAmount())) {
			LOG.debug("Cr/Dr total is not equal!");
			messages.add(createCrTotalNotEqualDrTotalMessage());
		}
		LOG.info("End");
		return messages;
	}

	
	public AccService getAccService() {
		return accService;
	}

	public void setAccService(AccService accService) {
		this.accService = accService;
	}
	
	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}
	
}
