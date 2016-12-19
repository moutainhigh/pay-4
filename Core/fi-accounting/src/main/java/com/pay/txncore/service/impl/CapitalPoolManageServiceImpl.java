package com.pay.txncore.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.txncore.dao.CapitalPoolManageDAO;
import com.pay.txncore.model.CapitalPoolManage;
import com.pay.txncore.model.PositionAllocaAudit;
import com.pay.txncore.service.CapitalPoolManageService;

public class CapitalPoolManageServiceImpl  implements CapitalPoolManageService{

	private CapitalPoolManageDAO capitalPoolManageDAO;

	private AccountingService accounting_1200_1201;

	private AccountingService accounting_1200_1202;
	
	private AccountingService accounting_1200_1203;

	private AccountingService accounting_1200_1204;
	
	private AccountingService accounting_1200_1205;
	
	@Override
	public List<CapitalPoolManage> queryCapitalPool(Map<String, Object> paraMap) {
		return capitalPoolManageDAO.queryCapitalPool(paraMap);
	}
	@Override
	public void update(Map<String, Object> paraMap) {
		capitalPoolManageDAO.update(paraMap);
	}
	@Override
	public Integer count() {
		return capitalPoolManageDAO.count();
	}
	@Override
	public void createAudit(Map<String, Object> paraMap) {
		Long allotSequence = capitalPoolManageDAO.createAudit(paraMap);
		String callOutCurrencyCode = String.valueOf(paraMap.get("callOutCurrencyCode"));
	    BigDecimal callOutAmount = new BigDecimal(String.valueOf(paraMap.get("callOutAmount"))).multiply(new BigDecimal(1000));
	    this.accounting_1200_1201(String.valueOf(allotSequence), callOutAmount.longValue(), callOutCurrencyCode, "--");		
	}
	@Override
	public List<PositionAllocaAudit> queryPositionAllocaAudit(
			PositionAllocaAudit positionAllocaAudit,Page page) {
		return capitalPoolManageDAO.queryPositionAllocaAudit(positionAllocaAudit,page);
	}
	
	
	public void setCapitalPoolManageDAO(CapitalPoolManageDAO capitalPoolManageDAO) {
		this.capitalPoolManageDAO = capitalPoolManageDAO;
	}
	public void setAccounting_1200_1201(AccountingService accounting_1200_1201) {
		this.accounting_1200_1201 = accounting_1200_1201;
	}
	//审核
	@Override
	public void updateStatus(PositionAllocaAudit positionAllocaAudit) {
		PositionAllocaAudit position=capitalPoolManageDAO.findPositionById(positionAllocaAudit);
		boolean checkStatus = capitalPoolManageDAO.updateStatus(positionAllocaAudit);
		String allotSequence = String.valueOf(position.getAllotSequence());
		Long calloutAmount =position.getCalloutAmount();
		String calloutCurrencyCode = position.getCalloutCurrencyCode();
	
		Long callinAmount =position.getCallinAmount();
		String callinCurrencyCode = position.getCallinCurrencyCode();
		
		if(checkStatus){ //update 成功后
			String status = positionAllocaAudit.getStatus();
			if(status.equals("1")){  ///通过
			this.accounting_1200_1203(allotSequence, calloutAmount, calloutCurrencyCode, "--");							//调用记账
			this.accounting_1200_1204(allotSequence, callinAmount, callinCurrencyCode, "--");							//调用记账
			this.accounting_1200_1205(allotSequence, callinAmount, callinCurrencyCode, "--");							//调用记账
			}else if(status.equals("2")){ //拒绝
					this.accounting_1200_1202(allotSequence,calloutAmount,calloutCurrencyCode, "--");			   //调用记账
			}
		}
	}
	
	public void accounting_1200_1201( String orderId,
			Long amount, String currencyCode,String merchantOrderId) {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);

		accounting_1200_1201.doAccounting(accountingDto);
	}
	
	public void accounting_1200_1202( String orderId,
			Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_1200_1202.doAccounting(accountingDto);
	}
	
	public void accounting_1200_1203( String orderId,
			Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_1200_1203.doAccounting(accountingDto);
	}
	
	public void accounting_1200_1204( String orderId,
			Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_1200_1204.doAccounting(accountingDto);
	}
	
	public void accounting_1200_1205( String orderId,
			Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_1200_1205.doAccounting(accountingDto);
	}
	
	
	public void setAccounting_1200_1202(AccountingService accounting_1200_1202) {
		this.accounting_1200_1202 = accounting_1200_1202;
	}
	public void setAccounting_1200_1203(AccountingService accounting_1200_1203) {
		this.accounting_1200_1203 = accounting_1200_1203;
	}
	public void setAccounting_1200_1204(AccountingService accounting_1200_1204) {
		this.accounting_1200_1204 = accounting_1200_1204;
	}
	public void setAccounting_1200_1205(AccountingService accounting_1200_1205) {
		this.accounting_1200_1205 = accounting_1200_1205;
	}
	@Override
	public List<CapitalPoolManage> queryByStatus(String status) {
		return capitalPoolManageDAO.queryByStatus(status);
	}
}
