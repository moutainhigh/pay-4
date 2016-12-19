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

@Service(value="settleOrderServiceImpl")
public class SettleOrderServiceImpl implements BuySettleOrderService{
	Log logger = LogFactory.getLog(SettleOrderServiceImpl.class);
	@Autowired
	private BuySettleOrderDAO buySettleOrderDaoImpl;
	@Autowired
	@Qualifier(value="accounting-1000-1001")
	private AccountingService accounting_1000_1001;
	
	@Autowired
	@Qualifier(value="accounting-1000-1002")
	private AccountingService accounting_1000_1002;
	
	@Autowired
	@Qualifier(value="accounting-1000-1003")
	private AccountingService accounting_1000_1003;
	
	@Autowired
	@Qualifier(value="accounting-1000-1004")
	private AccountingService accounting_1000_1004;
	
	@Autowired
	@Qualifier(value="accounting-1000-1005")
	private AccountingService accounting_1000_1005;
	
	@Autowired
	@Qualifier(value="accounting-1000-1006")
	private AccountingService accounting_1000_1006;
	
	@Autowired
	@Qualifier(value="accounting-1000-1007")
	private AccountingService accounting_1000_1007;
	
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
				accounting_1000_1001(OrderId,Long.valueOf(pojo.getExchAmount().toString()),pojo.getExchCurrencyCode(),"--",pojo.getPartnerId().toString());
				accounting_1000_1002(OrderId,Long.valueOf(pojo.getTradeFee().toString()),pojo.getExchCurrencyCode(),"--",pojo.getPartnerId().toString());
				op=1;
			} catch (Exception e) {
				logger.error("结汇转入中间户记账错误",e);
				op=2;
				pojo.setStatus("2");
				pojo.setExchangeNo(Long.valueOf(OrderId));
				pojo.setCompletedDate(new Date());
				buySettleOrderDaoImpl.update(pojo);
				lock.set(0);
			}
			if(op==1){
				try {
					accounting_1000_1003(OrderId,Long.valueOf(pojo.getExchAmount().toString()),pojo.getExchCurrencyCode(),"--");
					accounting_1000_1004(OrderId,Long.valueOf(pojo.getOrderAmount().toString()),pojo.getCurrencyCode(),"--",pojo.getPartnerId().toString());
					accounting_1000_1005(OrderId,Long.valueOf(pojo.getTradeFee().toString()),pojo.getExchCurrencyCode(),"--");
					pojo.setStatus("1");
					pojo.setExchangeNo(Long.valueOf(OrderId));
					pojo.setCompletedDate(new Date());
					buySettleOrderDaoImpl.update(pojo);
					lock.set(0);
					op=0;
				} catch (Exception e) {
					logger.error("结汇成功确认记账错误",e);
					try {
						accounting_1000_1006(OrderId,Long.valueOf(pojo.getExchAmount().toString()),pojo.getExchCurrencyCode(),"--",pojo.getPartnerId().toString());
						accounting_1000_1007(OrderId,Long.valueOf(pojo.getTradeFee().toString()),pojo.getExchCurrencyCode(),"--",pojo.getPartnerId().toString());
						op=3;
						pojo.setStatus("2");
						pojo.setExchangeNo(Long.valueOf(OrderId));
						pojo.setCompletedDate(new Date());
						buySettleOrderDaoImpl.update(pojo);
						lock.set(0);
					} catch (Exception e2) {
						op=4;
						logger.error("结汇失败退回记账错误",e2);
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
	public void accounting_1000_1001(String orderId,
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
		
		accounting_1000_1001.doAccounting(accountingDto);
	}
	public void accounting_1000_1002( String orderId,
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
		
		accounting_1000_1002.doAccounting(accountingDto);
	}
	public void accounting_1000_1003( String orderId,
			Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_1000_1003.doAccounting(accountingDto);
	}
	public void accounting_1000_1004( String orderId,
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
		
		accounting_1000_1004.doAccounting(accountingDto);
	}
	public void accounting_1000_1005( String orderId,
			Long amount, String currencyCode,String merchantOrderId) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		
		accounting_1000_1005.doAccounting(accountingDto);
	}
	public void accounting_1000_1006( String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayee(memberCode);
		accountingDto.setPayerAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		accounting_1000_1006.doAccounting(accountingDto);
	}
	public void accounting_1000_1007( String orderId,
			Long amount, String currencyCode,String merchantOrderId,String memberCode) {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayee(memberCode);
		accountingDto.setPayerAcctType(AcctTypeEnum.getBasicAcctTypeByCurrency(currencyCode));
		
		accounting_1000_1007.doAccounting(accountingDto);
	}
	
	
}
