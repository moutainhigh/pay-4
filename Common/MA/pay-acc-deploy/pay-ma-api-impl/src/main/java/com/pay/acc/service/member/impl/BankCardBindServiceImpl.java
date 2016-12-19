package com.pay.acc.service.member.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.common.MaConstant;
import com.pay.acc.constant.MemberInfoStatusEnum;
import com.pay.acc.exception.MaBankCardBindException;
import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.dto.LiquidateInfoDto;
import com.pay.acc.member.dto.MemberBankAcctDto;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.acc.member.service.LiquidateInfoService;
import com.pay.acc.member.service.MemberBankAcctService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.member.BankCardBindService;
import com.pay.acc.service.member.dto.BankCardBindBO;
import com.pay.util.DESUtil;

public class BankCardBindServiceImpl implements BankCardBindService {

	private MemberBankAcctService memberBankAcctService;
	private MemberService memberService;
	private LiquidateInfoService liquidateInfoService;
	private EnterpriseBaseService enterpriseBaseService;
	private final Log log = LogFactory.getLog(BankCardBindServiceImpl.class);
	

	/* (non-Javadoc)
	 * @see com.pay.acc.service.member.BankCardBindService#doQueryBankCardBindInfoForVerifyNsTx(long)
	 */
	@Override
	public List<BankCardBindBO> doQueryBankCardBindInfoForVerifyNsTx(long memberCode)
			throws MaBankCardBindException {
		log.debug("query MemberBankAcct list By MemberCode for Verify ");
		//验证
		MemberDto memberDto=this.checkQueryParameter(memberCode);
		
		List<BankCardBindBO> bankCardBindBOList=null;
		//个人用户
		if(null!=memberDto.getType() 
				&& memberDto.getType().intValue()==MaConstant.MEMBER_TYPE_PERSON){
			List<MemberBankAcctDto> memberBankAcctList=null;
			try {
				memberBankAcctList = memberBankAcctService.queryMemberBankAcctByMemberCode(memberCode);
			} catch (MemberException e) {
				log.error(e.getMessage(), e);
				throw new MaBankCardBindException(ErrorExceptionEnum.INVAILD_PARAMETER, "person invaild parameter", e);
			} catch (MemberUnknowException e) {
				log.error("person unknow error", e);
				throw new MaBankCardBindException(ErrorExceptionEnum.UNKNOW_ERROR, "person unknow error", e);
			}
			if(null!=memberBankAcctList && memberBankAcctList.size()>0){
				bankCardBindBOList=new ArrayList<BankCardBindBO>();
				log.debug("person MemberBankAcct list size : " + memberBankAcctList.size());
					for(MemberBankAcctDto memberBankAcctDto:memberBankAcctList){
						BankCardBindBO bankCardBindBO=new BankCardBindBO();
						bankCardBindBO.setId(memberBankAcctDto.getId());
						bankCardBindBO.setBankId(memberBankAcctDto.getBankId());
						bankCardBindBO.setMemberCode(memberBankAcctDto.getMemberCode());
						bankCardBindBO.setIsPrimaryBankacct(memberBankAcctDto.getIsPrimaryBankacct());
						bankCardBindBO.setBranchBankName(memberBankAcctDto.getBranchBankName());
						bankCardBindBO.setCreationDate(memberBankAcctDto.getCreationDate());
						bankCardBindBO.setName(memberBankAcctDto.getName());
						bankCardBindBO.setProvince(memberBankAcctDto.getProvince());
						bankCardBindBO.setCity(memberBankAcctDto.getCity());
						bankCardBindBO.setStatus(memberBankAcctDto.getStatus());
						bankCardBindBO.setBranchBankId(memberBankAcctDto.getBranchBankId());
						//解密处理
						try {
							bankCardBindBO.setBankAcctId(DESUtil.decrypt(memberBankAcctDto.getBankAcctId()));
						} catch (Exception e) {
							log.error("sha message decrypt error", e);
							throw new MaBankCardBindException(ErrorExceptionEnum.SHA_MESSAGE_DECRYPT_ERROR, "sha message decrypt error", e);
						}
						
						bankCardBindBOList.add(bankCardBindBO);
					}
					log.debug(" person bankCardBindBOList list size : " + bankCardBindBOList.size());
			}
			//企业用户
		}else if(null!=memberDto.getType() && memberDto.getType().intValue()==MaConstant.MEMBER_TYPE_MERCHANT){
			
			
			EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService.queryEnterpriseBaseByMemberCode(memberCode);
			List<LiquidateInfoDto> liquidateInfoList=null;
			try {
				liquidateInfoList = liquidateInfoService.queryLiquidateInfoByMemberCode(memberCode);
			} catch (MemberException e) {
				log.error(e.getMessage(), e);
				throw new MaBankCardBindException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
			} catch (MemberUnknowException e) {
				log.error("unknow error", e);
				throw new MaBankCardBindException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
			}
			if(null!=liquidateInfoList && liquidateInfoList.size()>0){
				bankCardBindBOList=new ArrayList<BankCardBindBO>();
				log.debug("merchant MemberBankAcct list size : " + liquidateInfoList.size());
					for(LiquidateInfoDto liquidateInfoDto:liquidateInfoList){
						BankCardBindBO bankCardBindBO=new BankCardBindBO();
						bankCardBindBO.setId(liquidateInfoDto.getLiquidateId());
						bankCardBindBO.setMemberCode(liquidateInfoDto.getMemberCode());
						if(null!=liquidateInfoDto.getBankAcct()){
							//解密处理
							try {
								bankCardBindBO.setBankAcctId(DESUtil.decrypt(liquidateInfoDto.getBankAcct()));
							} catch (Exception e) {
								log.error("message decrypt error", e);
								throw new MaBankCardBindException(ErrorExceptionEnum.SHA_MESSAGE_DECRYPT_ERROR, "message decrypt error", e);
							}
						}
						bankCardBindBO.setBranchBankName(liquidateInfoDto.getBankName());
						bankCardBindBO.setName(liquidateInfoDto.getAcctName());
						bankCardBindBO.setCreationDate(liquidateInfoDto.getCreateDate());
						bankCardBindBO.setIsPrimaryBankacct(liquidateInfoDto.getStatus());
						bankCardBindBO.setProvince(liquidateInfoDto.getProvince());
						bankCardBindBO.setCity(liquidateInfoDto.getCity());
						bankCardBindBO.setStatus(enterpriseBaseDto.getSettlementCycle());
						bankCardBindBO.setBankId(liquidateInfoDto.getBankId());
						bankCardBindBO.setBranchBankId(liquidateInfoDto.getBranchBankId());
						bankCardBindBO.setAuditStatus(liquidateInfoDto.getAuditStatus());
						bankCardBindBOList.add(bankCardBindBO);
					}
					log.debug(" merchant bankCardBindBOList list size : " + bankCardBindBOList.size());
			}
			
		}
		
		return bankCardBindBOList;
	}

	//会员验证参数
	private MemberDto checkQueryParameter(Long memberCode) throws MaBankCardBindException {
		if(null==memberCode || memberCode.longValue()<=0){
			log.error(" invaild parameter , memberCode is null  or invaild ! ");
			throw new MaBankCardBindException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , memberCode is null or invaild! ");
		}
//		else if (memberCode < MaConstant.CHECK_MEMBER_CODE) {
//			log.error("member code error");
//			throw new MaBankCardBindException(ErrorExceptionEnum.MEMBERCODE_ERROR, "member code error");
//		}
		// 验证会员是否激活
		MemberDto memberDto=null;

		try {
			memberDto = this.memberService.queryMemberWithMemberCode(memberCode);
		} catch (MemberException e) {
			log.error(e.getMessage(), e);
			throw new MaBankCardBindException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		} catch (MemberUnknowException e) {
			log.error("unknow error", e);
			throw new MaBankCardBindException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}
		
		if (memberDto == null) {
			log.error("不存在会员号为[" + memberCode + "]的会员");
			throw new MaBankCardBindException(ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR,"不存在会员号为[" + memberCode + "]的会员");
		}
		if (null == memberDto.getStatus() || memberDto.getStatus().intValue() != MemberInfoStatusEnum.NORMAL.getCode()) {
			log.error("会员号为[" + memberCode + "]处于非" + MemberInfoStatusEnum.NORMAL.getDescription());

			throw new MaBankCardBindException(ErrorExceptionEnum.MEMBER_INVALID, "会员号为[" + memberCode + "]处于非"
					+ MemberInfoStatusEnum.NORMAL.getDescription());
		}
		return memberDto;

	}

	public MemberBankAcctService getMemberBankAcctService() {
		return memberBankAcctService;
	}

	public void setMemberBankAcctService(MemberBankAcctService memberBankAcctService) {
		this.memberBankAcctService = memberBankAcctService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public LiquidateInfoService getLiquidateInfoService() {
		return liquidateInfoService;
	}

	public void setLiquidateInfoService(LiquidateInfoService liquidateInfoService) {
		this.liquidateInfoService = liquidateInfoService;
	}

	public EnterpriseBaseService getEnterpriseBaseService() {
		return enterpriseBaseService;
	}

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}
	
}
