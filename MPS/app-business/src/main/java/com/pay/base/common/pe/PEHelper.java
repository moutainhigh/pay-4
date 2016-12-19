package com.pay.base.common.pe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.dto.CalFeeDetailDto;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.dto.CalFeeRequestDto;
import com.pay.pe.helper.OrgType;
import com.pay.pe.service.CalFeeDetail;
import com.pay.pe.service.CalFeeReponse;
import com.pay.pe.service.CalFeeRequest;
import com.pay.util.BeanConvertUtil;

public class PEHelper {
	public static CalFeeRequest generateCaculateRequest(AcctAttribDto acctAttribDto,PEObject peObj,String amount) {
		String acctCode=acctAttribDto.getAcctCode();
		String acctountId=peObj.getMemberCode()+peObj.getAccountType();
		CalFeeRequest calFeeRequest=new CalFeeRequest();
		calFeeRequest.setOrderId(peObj.getOrderId());
		calFeeRequest.setAmount(Long.parseLong(amount)*10);
		calFeeRequest.setOrderCode(peObj.getOrderCode());	
		calFeeRequest.setDealCode(peObj.getDealCode());
		calFeeRequest.setPaymentServicePkgCode(peObj.getDealCode());
		calFeeRequest.setOrderAmount(Long.parseLong(amount)*10);
		calFeeRequest.setPayeeOrgCode(peObj.getPayerOrgCode());
		calFeeRequest.setPayeeOrgType(OrgType.BANK.getValue());	
		calFeeRequest.setPayer(peObj.getMemberCode());                  //科目无需设置payee
		calFeeRequest.setPayerAcctType(peObj.getAccountType());
		calFeeRequest.setPayerOrgType(peObj.getPayerOrgType());
		calFeeRequest.setPayerMemberAcctCode(acctountId);    //科目无需设置账户账号全称	
		calFeeRequest.setPayMethod(peObj.getPayMethod());
		calFeeRequest.setRequestDate(new Date());
		calFeeRequest.setPayerFullMemberAcctCode(acctCode);		
		calFeeRequest.setSubmitAcctCode(acctountId);
		return calFeeRequest;
	}
	
	public static CalFeeRequest unfreezeCaculateRequest(AcctAttribDto acctAttribDto,PEObject peObj,String amount) {
		String acctCode=acctAttribDto.getAcctCode();
		String acctountId=peObj.getMemberCode()+peObj.getAccountType();
		CalFeeRequest calFeeRequest=new CalFeeRequest();
		calFeeRequest.setOrderId(peObj.getOrderId());
		calFeeRequest.setAmount(Long.parseLong(amount));
		calFeeRequest.setOrderCode(peObj.getOrderCode());	
		calFeeRequest.setDealCode(peObj.getDealCode());
		calFeeRequest.setPaymentServicePkgCode(peObj.getDealCode());
		calFeeRequest.setOrderAmount(Long.parseLong(amount));
		calFeeRequest.setPayerOrgCode(peObj.getPayerOrgCode());
		calFeeRequest.setPayerOrgType(OrgType.BANK.getValue());	
		calFeeRequest.setPayee(peObj.getMemberCode());                  //科目无需设置payee
		calFeeRequest.setPayeeAcctType(peObj.getAccountType());
		calFeeRequest.setPayeeOrgType(peObj.getPayerOrgType());
		calFeeRequest.setPayeeMemberAcctCode(acctountId);    //科目无需设置账户账号全称	
		calFeeRequest.setPayMethod(peObj.getPayMethod());
		calFeeRequest.setRequestDate(new Date());
		calFeeRequest.setPayeeFullMemberAcctCode(acctCode);		
		calFeeRequest.setSubmitAcctCode(acctountId);
		return calFeeRequest;
	}
	public static CalFeeReponseDto generateCaculateResponse(CalFeeReponse calFeeRespone) {
		CalFeeReponseDto calFeeReponseDto =   BeanConvertUtil.convert(CalFeeReponseDto.class, calFeeRespone);
		CalFeeRequestDto calFeeRequestDto = BeanConvertUtil.convert(CalFeeRequestDto.class, calFeeRespone.getCalFeeRequest());			
		List<CalFeeDetailDto> calDetailList=new ArrayList<CalFeeDetailDto>();
		for(CalFeeDetail calFeeDetail:calFeeRespone.getCalFeeDetails()){
			CalFeeDetailDto calFeeDetailDtos= new CalFeeDetailDto();
			calFeeDetailDtos.setAcctcode(calFeeDetail.getAcctcode());
			calFeeDetailDtos.setCrdr(calFeeDetail.getCrdr());
			calFeeDetailDtos.setCreatedate(calFeeDetail.getCreatedate());
			calFeeDetailDtos.setCurrencyCode(calFeeDetail.getCurrencyCode());
			calFeeDetailDtos.setDealId(calFeeDetail.getDealId());
			calFeeDetailDtos.setEntrycode(calFeeDetail.getEntrycode());
			calFeeDetailDtos.setExchangeRate(calFeeDetail.getExchangeRate());
			calFeeDetailDtos.setMaBlanceBy(calFeeDetail.getMaBlanceBy());
			calFeeDetailDtos.setPaymentServiceId(calFeeDetail.getPaymentServiceId());
			calFeeDetailDtos.setStatus(calFeeDetail.getStatus());
			calFeeDetailDtos.setText(calFeeDetail.getText());
			calFeeDetailDtos.setTransactiondate(calFeeDetail.getTransactiondate());
			calFeeDetailDtos.setValue(calFeeDetail.getValue());
			calFeeDetailDtos.setVouchercode(calFeeDetail.getVouchercode());
			calDetailList.add(calFeeDetailDtos);
		}
		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);
		calFeeReponseDto.setCalFeeDetailDtos(calDetailList);			
		return calFeeReponseDto;
	}
}
