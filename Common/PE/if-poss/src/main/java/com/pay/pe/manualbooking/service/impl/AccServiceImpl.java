package com.pay.pe.manualbooking.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.commons.AcctEnum;
import com.pay.acc.service.account.AccountBalanceHandlerService;
import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.acc.service.account.dto.CalFeeDetailDto;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.dto.CalFeeRequestDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.pe.helper.OrgType;
import com.pay.pe.helper.TerminalType;
import com.pay.pe.manualbooking.dto.VouchDataDetailDto;
import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchDataPostingException;
import com.pay.pe.manualbooking.service.AccService;
import com.pay.pe.service.PEService;
import com.pay.pe.service.PaymentDetailDto;
import com.pay.pe.service.PaymentReqDto;
import com.pay.pe.service.PaymentResponseDto;

public class AccServiceImpl implements AccService {
	
	private AccountBalanceHandlerService accountBalanceHandlerService ;
	
	private AcctService acctService ;
	
	private AcctAttribService acctAttribService ;
	
	private PEService peService ;
	private Log log = LogFactory.getLog(AccServiceImpl.class);
	
	public String posting(VouchDataDto vouchDataDto) throws VouchDataPostingException{
		
		List<PaymentDetailDto> calFeeDetailDtoList = new ArrayList<PaymentDetailDto>();
		List<VouchDataDetailDto> vouchDataDetails = vouchDataDto.getVouchDataDetails();
		PaymentReqDto calFeeReqDto = new PaymentReqDto();
		int i = 0 ;
		String remark=null;
		for(VouchDataDetailDto vouchDataDetailDto :vouchDataDetails){
			PaymentDetailDto calFeeDetailDto = new PaymentDetailDto();
			calFeeDetailDto.setAcctcode(vouchDataDetailDto.getAccountCode());
			calFeeDetailDto.setCrdr(vouchDataDetailDto.getCrdr());
			BigDecimal amount = new BigDecimal(vouchDataDetailDto.getAmount()).movePointRight(3);
			calFeeDetailDto.setValue(amount.longValue());
			calFeeDetailDto.setEntrycode(++i);
			calFeeDetailDto.setStatus(0);
			calFeeDetailDto.setMaBlanceBy(peService.getMaBalanceBy(calFeeDetailDto.getAcctcode(),calFeeDetailDto.getCrdr()));
			//不传报错
			calFeeDetailDto.setVouchercode(0L);
			calFeeDetailDto.setText("");
			calFeeDetailDto.setCreatedate(new java.util.Date());
			calFeeDetailDto.setPaymentServiceId(0);
            calFeeDetailDto.setRemark(vouchDataDetailDto.getRemark());
			calFeeDetailDto.setTransactiondate(vouchDataDto.getCreateDate());
			calFeeDetailDtoList.add(calFeeDetailDto);

			//设置为付款方
			if(calFeeDetailDto.getCrdr().intValue() == 1){
				calFeeReqDto.setPayerFullMemberAcctCode(calFeeDetailDto.getAcctcode());
			}else{//设置为收款方
				calFeeReqDto.setPayeeFullMemberAcctCode(calFeeDetailDto.getAcctcode());
			}
		}
		//到底用什么id		
		calFeeReqDto.setOrderId(""+vouchDataDto.getVouchDataId());
		//到底用什么金额
		calFeeReqDto.setOrderAmount(vouchDataDto.getCrTotalAmount().movePointRight(3).longValue());
		calFeeReqDto.setAmount(vouchDataDto.getCrTotalAmount().movePointRight(3).longValue());
		
		calFeeReqDto.setDealCode(0);                
		calFeeReqDto.setOrderCode(0);
		calFeeReqDto.setPayMethod(1); 
		calFeeReqDto.setOrderCode(Integer.valueOf(800));
		calFeeReqDto.setDealCode(Integer.valueOf(801));
		calFeeReqDto.setTerminalType(TerminalType.WEB.getValue());
		calFeeReqDto.setPayerOrgType(OrgType.INNER.getValue());
		calFeeReqDto.setPayeeOrgType(OrgType.INNER.getValue());
		/**
		 * 算费返回对象
		 * 1，分录
		 * 2，请求对象
		 */
		PaymentResponseDto calFeeReponse = new PaymentResponseDto();
		
		calFeeReponse.setPaymentDetails(calFeeDetailDtoList);
		calFeeReponse.setPaymentReq(calFeeReqDto);
	
		PaymentResponseDto responseDto = peService.processVoucherCode(calFeeReponse);
		CalFeeReponseDto response = null;
		try {
			response = this.getCalfeeResponseDto(responseDto);
		} catch (Exception e1) {
			throw new VouchDataPostingException("创建会计凭证失败 ");
		}
		try {
			response.setRemark(remark);
			accountBalanceHandlerService.doUpdateAcctBalanceRntx_(response, PayForEnum.INNER_POSTING.getCode());
		} catch (MaAcctBalanceException e) {
			e.printStackTrace();
			String msg = e.getMessage();
			if(e.getErrorEnum()!=null)
				msg = e.getErrorEnum().getMessage();
			throw new VouchDataPostingException("更新余额失败:  "+msg);
		}
		return responseDto.getVoucherCode()+"";
	}
	
	/**
	 * 
	 * @param paymentResponseDto
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private CalFeeReponseDto getCalfeeResponseDto(PaymentResponseDto paymentResponseDto) throws IllegalAccessException, InvocationTargetException{
		CalFeeReponseDto calFeeReponseDto=new CalFeeReponseDto();
		CalFeeRequestDto calFeeRequestDto = new CalFeeRequestDto();
		BeanUtils.copyProperties(paymentResponseDto, calFeeReponseDto);
		BeanUtils.copyProperties(paymentResponseDto.getPaymentReq(), calFeeRequestDto);
		
		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);
		List<PaymentDetailDto> res = paymentResponseDto.getPaymentDetails();
		if(res != null && res.size() > 0 ){
			List<CalFeeDetailDto> calFeeDetails = new ArrayList<CalFeeDetailDto>();
			for(PaymentDetailDto tmp:res){
				CalFeeDetailDto paymentDetail = new CalFeeDetailDto();
				BeanUtils.copyProperties(tmp, paymentDetail);
				calFeeDetails.add(paymentDetail);
			}
			calFeeReponseDto.setCalFeeDetailDtos(calFeeDetails);
		}
		return calFeeReponseDto;
	}
	
	public boolean isAccountCodeNotExist(String acctCode)  {
		AcctDto acctDto = null;
		try {
			acctDto = this.acctService.queryAcctWithAcctCode(acctCode);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
//			throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_QUERY_ERROR, e.getMessage(), e);
		} 
		if (acctDto == null) {
			log.error("对于" + acctCode + "账户不存在");
			return true ;
//			throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_NON_EXIST_ERROR, "账户不存在");
		}
		if (acctDto.getStatus() != AcctEnum.VALID.getCode()) {
			log.error("对于账号" + acctCode + "无效");
			return true ;
//			throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_INVAILD_OR_FREEZE, "对于账号" + acctCode + "无效");
		}
		return false ;
	}
	
	
	
	public AcctAttribDto getAcctAttribInfo(String acctCode)  {
		// 验证属性是否存在
		AcctAttribDto acctAttribDto = null;
		try {
			acctAttribDto = this.acctAttribService.queryAcctAttribWithAcctCode(acctCode);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
//			throw new MaAcctBalanceException(e.getMessage(), ErrorExceptionEnum.ACCT_ATTRIBUTE, e);
		}
		return acctAttribDto;
//		if (acctAttribDto != null) {
//			if (acctAttribDto.getFrozen().intValue() != ConstantHelper.ACCT_FREEZE_STATUS) {
//				log.error("账户[" + acctCode + "]被冻结");
//				throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_FROZEN_ERROR, "账户[" + acctCode + "]被冻结");
//			}
//			if (acctAttribDto.getAllowIn().intValue() != ConstantHelper.ALLOW_IN) {
//				log.error("账户[" + acctCode + "]被冻结");
//				throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_FROZEN_ERROR, "账户[" + acctCode + "]被冻结");
//			}
//			if (acctAttribDto.getAllowOut().intValue() != ConstantHelper.ALLOW_OUT) {
//				log.error("账户[" + acctCode + "]被冻结");
//				throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_FROZEN_ERROR, "账户[" + acctCode + "]被冻结");
//			}
//		}
		
	}
	
	
	
	

	public AccountBalanceHandlerService getAccountBalanceHandlerService() {
		return accountBalanceHandlerService;
	}

	public void setAccountBalanceHandlerService(
			AccountBalanceHandlerService accountBalanceHandlerService) {
		this.accountBalanceHandlerService = accountBalanceHandlerService;
	}

	public AcctService getAcctService() {
		return acctService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public AcctAttribService getAcctAttribService() {
		return acctAttribService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public PEService getPeService() {
		return peService;
	}

	public void setPeService(PEService peService) {
		this.peService = peService;
	}
	


	
	
	
}
