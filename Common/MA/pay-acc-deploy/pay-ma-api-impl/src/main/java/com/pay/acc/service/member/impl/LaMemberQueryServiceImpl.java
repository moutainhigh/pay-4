package com.pay.acc.service.member.impl;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.MemberRelationQueryService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.member.LaMemberQueryService;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.LaResultDto;
import com.pay.acc.service.member.dto.MemberInfoDto;

public class LaMemberQueryServiceImpl implements LaMemberQueryService {
	
	private MemberQueryService  		memberQueryService;
	private AccountQueryService 		accountQueryService;
	private static final String 		SUCESSS="1111";
	private MemberRelationQueryService 	memberRelationQueryService;



	private static final Log LOG = LogFactory.getLog(LaMemberQueryServiceImpl.class);

	@Override
	public LaResultDto doQueryMemberByRelation(String memberCode,String login_Name) {
		LaResultDto resultDto=new LaResultDto();
		LOG.info("~~~~~~~~~~~~~~~~~开始调用查询余额接口~~~~~~~~~~~~~~~~~");
		try {
			Long laMemberCode=new Long(memberCode);
			MemberInfoDto infoDto=memberQueryService.doQueryMemberInfoNsTx(null, laMemberCode, null, null);
			if(null==infoDto){
				resultDto.setErrorCode(ErrorExceptionEnum.LA_MEMBER_CODE_NOT_EXITSTS.getErrorCode());
				resultDto.setErrorMsg(ErrorExceptionEnum.LA_MEMBER_CODE_NOT_EXITSTS.getMessage());
				LOG.info("~~~~~~~~~~~~~~~~~查询总店memberCode ："+memberCode+"不存在");
				return resultDto;
			}
		} catch (Exception e) {
			resultDto.setErrorCode(ErrorExceptionEnum.LA_MEMBER_CODE_NOT_EXITSTS.getErrorCode());
			resultDto.setErrorMsg(ErrorExceptionEnum.LA_MEMBER_CODE_NOT_EXITSTS.getMessage());
			LOG.error("查询总店memberCode ："+memberCode+" 异常 : "+e);
			return resultDto;
		}
		Long sunMemberCode=null;
		try {
			MemberInfoDto infoDto=memberQueryService.doQueryMemberInfoNsTx(login_Name, null, null, 10);
			if(null==infoDto){
				resultDto.setErrorCode(ErrorExceptionEnum.LA_MEMBER_CODE_NOT_EXITSTS.getErrorCode());
				resultDto.setErrorMsg(ErrorExceptionEnum.LA_MEMBER_CODE_NOT_EXITSTS.getMessage());
				LOG.info("查询分店login_Name ："+login_Name+"不存在");
				return resultDto;
			}
			resultDto.setLoginName(infoDto.getLoginName());
			sunMemberCode =infoDto.getMemberCode();
			//判断查询的名单是否已经关联
			boolean flag=this.memberRelationQueryService.isBePartOfTheBourse(memberCode, sunMemberCode.toString());
			if(!flag){
				resultDto.setErrorCode(ErrorExceptionEnum.LA_MEMBER_CODE_NOT_RELATION.getErrorCode());
				resultDto.setErrorMsg(ErrorExceptionEnum.LA_MEMBER_CODE_NOT_RELATION.getMessage());
				LOG.info("总店memberCode ："+memberCode +" 分店login_Name ："+login_Name+"没有关联");
				return resultDto;
			}
			
		} catch (Exception e) {
			resultDto.setErrorCode(ErrorExceptionEnum.LA_MEMBER_CODE_NOT_EXITSTS.getErrorCode());
			resultDto.setErrorMsg(ErrorExceptionEnum.LA_MEMBER_CODE_NOT_EXITSTS.getMessage());
			LOG.error("查询分店login_Name："+login_Name+"  异常  :"+e);
			return resultDto;
		}
		

		try {
			BalancesDto balanceDto=accountQueryService.doQueryBalancesNsTx(sunMemberCode, 10);
			if(null==balanceDto){
				resultDto.setErrorCode(ErrorExceptionEnum.LA_ACCT_BALANCE_NOT_EXITSTS.getErrorCode());
				resultDto.setErrorMsg(ErrorExceptionEnum.LA_ACCT_BALANCE_NOT_EXITSTS.getMessage());
				return resultDto;
			}
			//对结果除1000
			BigDecimal dec=new 	BigDecimal(balanceDto.getBalance());
			dec=dec.divide(new BigDecimal(1000),3,BigDecimal.ROUND_HALF_UP);			
			resultDto.setBalance(dec.toString());
		} catch (Exception e) {
			resultDto.setErrorCode(ErrorExceptionEnum.LA_ACCT_BALANCE_NOT_EXITSTS.getErrorCode());
			resultDto.setErrorMsg(ErrorExceptionEnum.LA_ACCT_BALANCE_NOT_EXITSTS.getMessage());
			LOG.error("查询分店login_Name："+login_Name+"  账户余额异常  :"+e);
			return resultDto;
		}
		resultDto.setErrorCode(SUCESSS);
		resultDto.setResultFlag(true);
		LOG.info("~~~~~~~~~~~~~~~~~调用查询余额接口正常完成~~~~~~~~~~~~~~~~~");

		return resultDto;
	}

	public void setMemberRelationQueryService(
			MemberRelationQueryService memberRelationQueryService) {
		this.memberRelationQueryService = memberRelationQueryService;
	}
	
	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}
}
