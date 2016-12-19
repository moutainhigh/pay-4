package com.pay.buySettle.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.buySettle.dao.BuySettleOrderDAO;
import com.pay.buySettle.service.BuySettleOrderService;
import com.pay.inf.dao.Page;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.txncore.model.BuysettleOrder;

@Service(value="buyOrderServiceImpl")
public class BuyOrderServiceImpl implements BuySettleOrderService{
	Log logger = LogFactory.getLog(BuyOrderServiceImpl.class);
	@Autowired
	private BuySettleOrderDAO buySettleOrderDaoImpl;
	@Autowired
	@Qualifier(value="accounting-1100-1101")
	private AccountingService accounting_1100_1101;
	
	@Autowired
	@Qualifier(value="accounting-1100-1102")
	private AccountingService accounting_1100_1102;
	
	@Autowired
	@Qualifier(value="accounting-1100-1103")
	private AccountingService accounting_1100_1103;
	
	@Autowired
	@Qualifier(value="accounting-1100-1104")
	private AccountingService accounting_1100_1104;
	
	@Autowired
	@Qualifier(value="accounting-1100-1105")
	private AccountingService accounting_1100_1105;
	
	@Autowired
	@Qualifier(value="accounting-1100-1106")
	private AccountingService accounting_1100_1106;
	
	@Autowired
	@Qualifier(value="accounting-1100-1107")
	private AccountingService accounting_1100_1107;
	
	private AtomicInteger lock=new AtomicInteger(0); 
	private AtomicLong partnerId=new AtomicLong(0);
	@Override
	public BuysettleOrder queryByCondition(BuysettleOrder params) {
		return buySettleOrderDaoImpl.queryByCondition(params);
	}

	@Override
	public BuysettleOrder queryByConditions(Map<String, Object> params) {
		return buySettleOrderDaoImpl.queryByConditions(params);
	}

	@Override
	public List<BuysettleOrder> queryByConditionsList(BuysettleOrder params) {
		return buySettleOrderDaoImpl.queryByConditionsList(params);
	}

	@Override
	public List<BuysettleOrder> queryByConditionsList(Map<String, Object> params) {
		return buySettleOrderDaoImpl.queryByConditionsList(params);
	}

	@Override
	public List<BuysettleOrder> queryByConditionsPage(BuysettleOrder params, Page<BuysettleOrder> page) {
		return buySettleOrderDaoImpl.queryByConditionsPage(params,page);
	}

	@Override
	public List<BuysettleOrder> queryByConditionsPage(Map<String, Object> params, Page<BuysettleOrder> page) {
		return buySettleOrderDaoImpl.queryByConditionsPage(params,page);
	}
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false,rollbackFor={Exception.class})
	@Override
	public Object create(BuysettleOrder pojo) {
		if(partnerId.get()==pojo.getPartnerId().longValue()){
			if(lock.get()!=0){
				lock.set(-1);
				partnerId.set(0);
			}
		}else{
			lock.set(0);
		}
		int op=-1;
		if(lock.get()==0){
			lock.getAndIncrement();
			partnerId.set(pojo.getPartnerId().longValue());
			String OrderId=buySettleOrderDaoImpl.create(pojo).toString();
			try {
				accounting_1100_1101(OrderId,Long.valueOf(pojo.getExchAmount().toString()),pojo.getExchCurrencyCode(),"--",pojo.getPartnerId().toString());
				accounting_1100_1102(OrderId,Long.valueOf(pojo.getTradeFee().toString()),pojo.getExchCurrencyCode(),"--",pojo.getPartnerId().toString());
				op=1;
			} catch (Exception e) {
				logger.error("购汇中间户转入记账错误",e);
				pojo.setStatus("2");
				pojo.setExchangeNo(Long.valueOf(OrderId));
				pojo.setCompletedDate(new Date());
				buySettleOrderDaoImpl.update(pojo);
				op=2;
				lock.set(0);
				logger.error("购汇记账错误",e);
				
			}
			if(op==1){
				try {
					accounting_1100_1103(OrderId,Long.valueOf(pojo.getExchAmount().toString()),pojo.getExchCurrencyCode(),"--");
					accounting_1100_1104(OrderId,Long.valueOf(pojo.getOrderAmount().toString()),pojo.getCurrencyCode(),"--",pojo.getPartnerId().toString());
					accounting_1100_1105(OrderId,Long.valueOf(pojo.getTradeFee().toString()),pojo.getExchCurrencyCode(),"--");
					op=0;
					pojo.setStatus("1");
					pojo.setExchangeNo(Long.valueOf(OrderId));
					pojo.setCompletedDate(new Date());
					buySettleOrderDaoImpl.update(pojo);
					lock.set(0);
				} catch (Exception e) {
					logger.error("购汇成功确认记账错误",e);
					try {
						accounting_1100_1106(OrderId,Long.valueOf(pojo.getExchAmount().toString()),pojo.getExchCurrencyCode(),"--",pojo.getPartnerId().toString());
						accounting_1100_1107(OrderId,Long.valueOf(pojo.getTradeFee().toString()),pojo.getExchCurrencyCode(),"--",pojo.getPartnerId().toString());
						op=3;
						pojo.setStatus("2");
						pojo.setExchangeNo(Long.valueOf(OrderId));
						pojo.setCompletedDate(new Date());
						buySettleOrderDaoImpl.update(pojo);
						lock.set(0);
					} catch (Exception e2) {
						op=4;
						logger.error("购汇失败退回记账错误",e2);
						pojo.setStatus("2");
						pojo.setExchangeNo(Long.valueOf(OrderId));
						pojo.setCompletedDate(new Date());
						buySettleOrderDaoImpl.update(pojo);
						lock.set(0);
					}
				}
			}
		}
		return op;
		
	}
	public void accounting_1100_1101(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String payer) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayer(payer);
		accountingDto.setPayerAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		accounting_1100_1101.doAccounting(accountingDto);
	}
	public void accounting_1100_1102(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String payer) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayer(payer);
		accountingDto.setPayerAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		accounting_1100_1102.doAccounting(accountingDto);
	}
	public void accounting_1100_1103( String orderId,
			Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_1100_1103.doAccounting(accountingDto);
	}
	public void accounting_1100_1104( String orderId,
			Long amount, String currencyCode,String merchantOrderId,String payer) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayee(payer);
		accountingDto.setPayeeAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		
		accounting_1100_1104.doAccounting(accountingDto);
	}
	public void accounting_1100_1105( String orderId,
			Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_1100_1105.doAccounting(accountingDto);
	}
	public void accounting_1100_1106(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String payee) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayee(payee);
		accountingDto.setPayeeAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		accounting_1100_1106.doAccounting(accountingDto);
	}
	public void accounting_1100_1107(String orderId,
			Long amount, String currencyCode,String merchantOrderId,String payee) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayee(payee);
		accountingDto.setPayeeAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		
		accounting_1100_1107.doAccounting(accountingDto);
	}
	
}
