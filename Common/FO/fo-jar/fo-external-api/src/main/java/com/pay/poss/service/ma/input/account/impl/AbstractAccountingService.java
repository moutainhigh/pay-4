/**
 *  File: AbstractAccountingService.java
 *  Description:
 *  Copyright 2010 -2010 hnapay Corporation. All rights reserved.
 *  2010-9-24      zliner      Changes
 *  
 *
 */
package com.pay.poss.service.ma.input.account.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.account.dto.CalFeeDetailDto;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.dto.CalFeeRequestDto;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.pe.service.CalFeeDetail;
import com.pay.pe.service.CalFeeReponse;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.common.accounting.AccountingResult;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.dto.withdraw.pricestrategy.CalFeeRequestDTO;
import com.pay.poss.service.withdraw.inf.output.PeFacadeService;
import com.pay.poss.service.withdraw.ma.output.AccountBalFacadeService;

/**
 * 通用记账服务
 * 
 * @author zliner
 * 
 */
@Deprecated
public abstract class AbstractAccountingService {

	protected MemberQueryService memberQueryService;

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	// 日志服务类
	private Log logger = LogFactory.getLog(AbstractAccountingService.class);
	// PE记账门面服务
	private PeFacadeService peFacadeService;
	// 更新余额记账服务
	private AccountBalFacadeService accountBalFacadeService;
	private Map<String, String> mappingToDealType;

	// set注入
	public void setMappingToDealType(final Map<String, String> param) {
		this.mappingToDealType = param;
	}

	// set注入
	public void setAccountBalFacadeService(final AccountBalFacadeService param) {
		this.accountBalFacadeService = param;
	}

	// set注入
	public void setPeFacadeService(final PeFacadeService param) {
		this.peFacadeService = param;
	}

	// 调用MA的记账服务接口
	/**
	 * 记账服务
	 * 
	 * @param param
	 *            记账对象
	 * @return integer 0为失败，1为成功
	 */
	public Integer accounting(final HandlerParam param) {
		try {
			// 调用算费接口得到算费对象，并构建传递给MA的相关数据

			CalFeeRequestDTO calFeeReq = buildCalFeeRequestDTO(param);
			if (calFeeReq.getPayee() != null) {
				if (calFeeReq.getPayeeServiceLevel() == null) {
					calFeeReq.setPayeeServiceLevel(buildCalFeeServiceLevel(
							calFeeReq, Long.valueOf(calFeeReq.getPayee())));
				}
			}
			if (calFeeReq.getPayer() != null) {
				if (calFeeReq.getPayerServiceLevel() == null) {
					calFeeReq.setPayerServiceLevel(buildCalFeeServiceLevel(
							calFeeReq, Long.valueOf(calFeeReq.getPayer())));
				}
			}

			CalFeeReponse calFeeRes = peFacadeService
					.calculateFeeDetail(calFeeReq);

			// 调用构建相关更新余额的构建器进行构建
			return accountBalFacadeService.doUpdateAcctBalanceRntx(
					buildCalFeeResDto(calFeeRes), Integer
							.valueOf(mappingToDealType.get(param
									.getWithdrawBusinessType())));
		} catch (Exception e) {
			logger.error("accting Error", e);
			return AccountingResult.ACCOUNTING_FAIL.getResult();
		}
	}

	/**
	 * 构建算费对象参数
	 * 
	 * @param accountDto
	 *            记账对象
	 * @return CalFeeRequestDTO 构建算费后的可记账对象
	 */
	protected abstract CalFeeRequestDTO buildCalFeeRequestDTO(HandlerParam param);

	// 构建传递给MA组的更新余额对象
	private CalFeeReponseDto buildCalFeeResDto(CalFeeReponse calFeeRes) {
		CalFeeReponseDto callCalFeeReponseDto = new CalFeeReponseDto();
		List<CalFeeDetail> calFeeDetailList = calFeeRes.getCalFeeDetails();
		List<CalFeeDetailDto> calFeeDetailDtoList = new ArrayList<CalFeeDetailDto>();
		for (CalFeeDetail calFeeDetail : calFeeDetailList) {
			CalFeeDetailDto dto = new CalFeeDetailDto();
			BeanUtils.copyProperties(calFeeDetail, dto);
			calFeeDetailDtoList.add(dto);
		}
		callCalFeeReponseDto.setCalFeeDetailDtos(calFeeDetailDtoList);
		CalFeeRequestDto requestDto = new CalFeeRequestDto();
		BeanUtils.copyProperties(calFeeRes.getCalFeeRequest(), requestDto);
		callCalFeeReponseDto.setCalFeeRequestDto(requestDto);
		callCalFeeReponseDto.setHasCaculatedPrice(calFeeRes
				.isHasCaculatedPrice());
		callCalFeeReponseDto.setPayeeFee(calFeeRes.getPayeeFee());
		callCalFeeReponseDto.setPayerFee(calFeeRes.getPayerFee());
		// callCalFeeReponseDto.setPriceStrategyCode(calFeeRes.getPriceStrategyCode());
		return callCalFeeReponseDto;
	}

	protected int buildCalFeeServiceLevel(CalFeeRequestDTO calFeeReqDto,
			long memberCode) {
		try {
			MemberBaseInfoBO baseInfoBO = memberQueryService
					.queryMemberBaseInfoByMemberCode(memberCode);
			return baseInfoBO.getServiceLevelCode();
		} catch (MaMemberQueryException e) {
			LogUtil.error(this.getClass(), "查询会员服务等级异常", OPSTATUS.EXCEPTION,
					String.valueOf(memberCode), String.valueOf(memberCode),
					e.getMessage(), "001", e.getMessage());
			return -1;
		}
	}

}
