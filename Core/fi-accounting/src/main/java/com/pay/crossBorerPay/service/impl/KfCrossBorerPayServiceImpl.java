package com.pay.crossBorerPay.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.crossBorerPay.dao.KfPayTradeDetailDao;
import com.pay.crossBorerPay.service.KfCrossBorerPayService;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.txncore.model.KfPayTradeDetail;

@Service(value="kfCrossBorerPayServiceImpl")
public class KfCrossBorerPayServiceImpl implements KfCrossBorerPayService{
	@Autowired
	@Qualifier(value="kfPayTradeDetailDaoImpl")
	private KfPayTradeDetailDao kfPayTradeDetailDaoImpl;
	/**
	 * 跨境付款申请记账规则
	 */
	@Autowired
	@Qualifier(value="accounting-2000-2001")
	private AccountingService accounting_2000_2001;
	@Autowired
	@Qualifier(value="accounting-2000-2002")
	private AccountingService accounting_2000_2002;
	@Autowired
	@Qualifier(value="accounting-2000-2003")
	private AccountingService accounting_2000_2003;
	@Autowired
	@Qualifier(value="accounting-2000-2004")
	private AccountingService accounting_2000_2004;
	@Autowired
	@Qualifier(value="accounting-2000-2005")
	private AccountingService accounting_2000_2005;
	/**
	 * 跨境付款审核拒绝记账规则
	 */
	@Autowired
	@Qualifier(value="accounting-2100-2101")
	private AccountingService accounting_2100_2101;
	@Autowired
	@Qualifier(value="accounting-2100-2102")
	private AccountingService accounting_2100_2102;
	@Autowired
	@Qualifier(value="accounting-2100-2103")
	private AccountingService accounting_2100_2103;
	@Autowired
	@Qualifier(value="accounting-2100-2104")
	private AccountingService accounting_2100_2104;
	@Autowired
	@Qualifier(value="accounting-2100-2105")
	private AccountingService accounting_2100_2105;
	/**
	 * 跨境付款出款成功记账规则
	 */
	@Autowired
	@Qualifier(value="accounting-2200-2206")
	private AccountingService accounting_2200_2206;
	/**
	 * 跨境付款出款失败记账规则
	 */
	@Autowired
	@Qualifier(value="accounting-2300-2305")
	private AccountingService accounting_2300_2305;
	@Autowired
	@Qualifier(value="accounting-2300-2306")
	private AccountingService accounting_2300_2306;
	@Autowired
	@Qualifier(value="accounting-2300-2307")
	private AccountingService accounting_2300_2307;
	@Autowired
	@Qualifier(value="accounting-2300-2308")
	private AccountingService accounting_2300_2308;
	@Autowired
	@Qualifier(value="accounting-2300-2309")
	private AccountingService accounting_2300_2309;
	@Override
	public void crossBorerApply(KfPayTradeDetail kfPayTradeDetail) {
		List<KfPayTradeDetail> detailList=kfPayTradeDetailDaoImpl.findByCriteria("queryConditions", kfPayTradeDetail);
		for (KfPayTradeDetail item : detailList) {//申请
			this.accounting_2000_2001(item.getDetailNo(),item.getOrderAmount(),"CNY",item.getOrderId(),String.valueOf(item.getPartnerId()));
			this.accounting_2000_2002(item.getDetailNo(),item.getFee(),"CNY",item.getOrderId(),String.valueOf(item.getPartnerId()));
			this.accounting_2000_2003(item.getDetailNo(),item.getSmallServiceFee(),"CNY",item.getOrderId(),String.valueOf(item.getPartnerId()));
			this.accounting_2000_2004(item.getDetailNo(),item.getPayAmount(),"CNY",item.getOrderId(),String.valueOf(item.getPartnerId()));
			this.accounting_2000_2005(item.getDetailNo(),item.getRemitAmount(),item.getRemitCurrencyCode(),item.getOrderId(),String.valueOf(item.getPartnerId()));
		}
	}


	@Override
	public void RemitFailTypingReviewed(String status,String detailNos) {
		if(status.equals("3")){
			List<KfPayTradeDetail> result=crossBorerDate("3",detailNos);
			for (KfPayTradeDetail item : result) {//出款成功
				this.accounting_2200_2206(item.getDetailNo(),item.getRemitAmount(),item.getRemitCurrencyCode(),item.getOrderId(),String.valueOf(item.getPartnerId()));
			}
		}else if(status.equals("4")){
			List<KfPayTradeDetail> result=crossBorerDate("4",detailNos);
			for (KfPayTradeDetail item : result) {//出款失败
				this.accounting_2300_2305(item.getDetailNo(),item.getRemitAmount(),item.getRemitCurrencyCode(),item.getOrderId(),String.valueOf(item.getPartnerId()));
				this.accounting_2300_2306(item.getDetailNo(),item.getPayAmount(),"CNY",item.getOrderId(),String.valueOf(item.getPartnerId()));
				this.accounting_2300_2307(item.getDetailNo(),item.getRemitExpense(),"CNY",item.getOrderId(),String.valueOf(item.getPartnerId()));
				this.accounting_2300_2308(item.getDetailNo(),item.getSmallServiceFee(),"CNY",item.getOrderId(),String.valueOf(item.getPartnerId()));
				this.accounting_2300_2309(item.getDetailNo(),item.getOrderAmount(),"CNY",item.getOrderId(),String.valueOf(item.getPartnerId()));
			}
		}else if(status.equals("2")){
			List<KfPayTradeDetail> result=crossBorerDate("2",detailNos);
			for (KfPayTradeDetail item : result) {//审核拒绝
				this.accounting_2100_2101(item.getDetailNo(),item.getRemitAmount(),item.getRemitCurrencyCode(),item.getOrderId(),String.valueOf(item.getPartnerId()));
				this.accounting_2100_2102(item.getDetailNo(),item.getPayAmount(),"CNY",item.getOrderId(),String.valueOf(item.getPartnerId()));
				this.accounting_2100_2103(item.getDetailNo(),item.getSmallServiceFee(),"CNY",item.getOrderId(),String.valueOf(item.getPartnerId()));
				this.accounting_2100_2104(item.getDetailNo(),item.getRemitExpense(),"CNY",item.getOrderId(),String.valueOf(item.getPartnerId()));
				this.accounting_2100_2105(item.getDetailNo(),item.getOrderAmount(),"CNY",item.getOrderId(),String.valueOf(item.getPartnerId()));
			}
		}
	}

	private List<KfPayTradeDetail> crossBorerDate(String status,String detailNos) {
		KfPayTradeDetail kfPayTradeDetail=new KfPayTradeDetail();
		kfPayTradeDetail.setDetailNos(detailNos);
		List<KfPayTradeDetail> detailList=kfPayTradeDetailDaoImpl.findByCriteria("queryConditions", kfPayTradeDetail);
		return detailList;
	}
	
	
	
	private void accounting_2000_2001(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		accounting_2000_2001.doAccounting(accountingDto);
	}
	private void accounting_2000_2002(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		accounting_2000_2002.doAccounting(accountingDto);
	}
	private void accounting_2000_2003(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayer(memberCode);
		accountingDto.setPayerAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		accounting_2000_2003.doAccounting(accountingDto);
	}
	private void accounting_2000_2004(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_2000_2004.doAccounting(accountingDto);
	}
	private void accounting_2000_2005(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_2000_2005.doAccounting(accountingDto);
	}
	private void accounting_2100_2101(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_2100_2101.doAccounting(accountingDto);
	}
	private void accounting_2100_2102(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_2100_2102.doAccounting(accountingDto);
	}
	private void accounting_2100_2103(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayee(memberCode);
		accountingDto.setPayeeAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		accounting_2100_2103.doAccounting(accountingDto);
	}
	private void accounting_2100_2104(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayee(memberCode);
		accountingDto.setPayeeAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		accounting_2100_2104.doAccounting(accountingDto);
	}
	private void accounting_2100_2105(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayee(memberCode);
		accountingDto.setPayeeAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		accounting_2100_2105.doAccounting(accountingDto);
	}
	private void accounting_2200_2206(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_2200_2206.doAccounting(accountingDto);
	}
	private void accounting_2300_2305(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_2300_2305.doAccounting(accountingDto);
	}
	private void accounting_2300_2306(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_2300_2306.doAccounting(accountingDto);
	}
	private void accounting_2300_2307(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayee(memberCode);
		accountingDto.setPayeeAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		accounting_2300_2307.doAccounting(accountingDto);
	}
	private void accounting_2300_2308(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayee(memberCode);
		accountingDto.setPayeeAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		accounting_2300_2308.doAccounting(accountingDto);
	}
	private void accounting_2300_2309(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayee(memberCode);
		accountingDto.setPayeeAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		accounting_2300_2309.doAccounting(accountingDto);
	}

}
